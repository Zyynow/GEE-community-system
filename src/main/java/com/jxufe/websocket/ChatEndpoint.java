package com.jxufe.websocket;

import com.alibaba.fastjson.JSON;
import com.jxufe.configuration.GetHttpSessionConfig;
import com.jxufe.entity.User;
import com.jxufe.utils.MessageUtils;
import com.jxufe.websocket.pojo.Message;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @version v1.0
 * @ClassName: ChatEndpoint
 * @Description: 将目前的类定义成一个websocket服务器端，注解的值(value)将被用于监听用户连接的终端访问URL地址，客户端可以通过这个URL来连接到WebSocket服务器端
 * 而configurator属性则是引入相关的配置类
 * @Author: Kafka
 */
@ServerEndpoint(value = "/chat", configurator = GetHttpSessionConfig.class)
@Component
public class ChatEndpoint {

    /*
        用于存储session对象的一个Map集合，由于每一次连接都会创建endPoint实例，使用static是为了保证所有的endPoint实例使用同一个Map集合
        防止重新给onlineUser赋值

        ConcurrentHashMap:
            1、ConcurrentHashMap是线程安全的HashMap，专门用于多线程环境
            2、ConcurrentHashMap并非锁住整个方法，而是通过原子操作和局部加锁的方法保证了多线程的线程安全，且尽可能减少了性能损耗。
            3、ConcurrentHashMap使用volatile修饰节点数组，保证其可见性，禁止指令重排。
            4、put方法具体细节：
                做插入操作时，首先进入乐观锁，
                然后，在乐观锁中判断容器是否初始化，
                如果没初始化则初始化容器，
                如果已经初始化，则判断该hash位置的节点是否为空，如果为空，则通过CAS操作进行插入。
                如果该节点不为空，再判断容器是否在扩容中，如果在扩容，则帮助其扩容。
                如果没有扩容，则进行最后一步，先加锁，然后找到hash值相同的那个节点(hash冲突)，
                循环判断这个节点上的链表，决定做覆盖操作还是插入操作。
                循环结束，插入完毕。
            5、ConcurrentHashMap的get()方法是不加锁的，为什么可以不加锁？因为table有volatile关键字修饰，保证每次获取值都是最新的。
     */
    private static final Map<String, Session> onlineUsers = new ConcurrentHashMap<>();

    // httpSession成员变量
    private HttpSession httpSession;

    /**
     * 跳转到聊天界面打开会话，建立websocket连接后，即会调用该被@OnOpen标记的方法
     *
     * @param session javax.websocket包下的Session对象，即websocketSession
     * @param config  该对象也就是ServerEndpointConfig对象，因为ServerEndpointConfig接口继承了EndpointConfig接口
     */
    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        // 通过config对象拿到HttpSession对象
        this.httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        // 通过httpSession获取用户名称属性(this意味着当前的ChatEndpoint)
        String username = ((User) this.httpSession.getAttribute("user")).getUsername();
        System.out.println("谁来了：" + username);
        // 将session进行保存
        onlineUsers.put(username, session); // 用户名来标识每个session
        // 广播消息，需要将登陆的所有的用户推送给所有的用户，注意广播消息是系统发出的
        String message = MessageUtils.getMessage(true, null, getFriends());
        broadcastAllUsers(message);
    }

    public Set getFriends() {
        // 拿到key的一个集合，可以用于遍历，也可以用于查询
        Set<String> set = onlineUsers.keySet();
        return set;
    }

    // 广播消息给所有用户
    private void broadcastAllUsers(String message) {
        try {
            // 遍历map集合
            /*
                entrySet方法就是返回一个集合，集合里存放的是对象，创建对象的类有两个属性，分别是 键和值 也即键值对。
                其中Entry是属于Map的静态内部类，在创建Map对象的时候就会同时创建一个Entry对象，用来记录键与值的映射关系。
             */
            Set<Map.Entry<String, Session>> entries = onlineUsers.entrySet();
            for (Map.Entry<String, Session> entry : entries) {
                // 获取到所有用户对应的session对象
                Session session = entry.getValue();
                // 利用RemoteEndpoint的sendText方法发送消息
                session.getBasicRemote().sendText(message);
            }
        } catch (Exception e) {
            // 记录日志
        }
    }

    /**
     * 客户端发送消息到服务端，即会调用该被@OnMessage标记的方法
     * <p>
     * 张三 -> 李四 (即确定发送方和接收方)
     *
     * @param message
     */
    @OnMessage
    public void onMessage(String message) {
        try {
            System.out.println("消息：" + message);
            // 将json字符串转换为Message对象
            Message msg = JSON.parseObject(message, Message.class);
            // 获取消息接收方的用户名和内容
            String toName = msg.getToName();
            String mess = msg.getMessage();
            // 获取消息接收方用户对象的session对象(session对象被存在onlineUser这个ConcurrentHashMap(username-websocketSession)集合中)
            Session session = onlineUsers.get(toName);
            String username = ((User) this.httpSession.getAttribute("user")).getUsername();
            String msg1 = MessageUtils.getMessage(false, username, mess);
            // 利用RemoteEndpoint的sendText方法发送消息
            session.getBasicRemote().sendText(msg1);
        } catch (Exception e) {
            // 记录日志
        }
    }

    /**
     * 用户退出登录，断开websocket连接时，即会调用该被@OnClose标记的方法
     *
     * @param session javax.websocket包下的Session对象，即websocketSession
     */
    @OnClose
    public void onClose(Session session) {
        // 从onlineUsers中剔除当前用户的session对象
        String username = ((User) this.httpSession.getAttribute("user")).getUsername();
        System.out.println("谁走了：" + username);
        onlineUsers.remove(username);
        // 广播消息，通知其他所有的用户当前用户下线了
        String message = MessageUtils.getMessage(true, null, getFriends());
        broadcastAllUsers(message);
    }
}
