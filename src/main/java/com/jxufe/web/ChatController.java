package com.jxufe.web;

import com.jxufe.entity.Chat;
import com.jxufe.entity.User;
import com.jxufe.service.ChatService;
import com.jxufe.service.FriendService;
import com.jxufe.service.UserService;
import com.jxufe.vo.ChatVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
 * @RestController 注解相当于 @ResponseBody + @Controller 合在一起的作用
 * 若使用@Controller注解，在对应的方法上，视图解析器可以解析return的jsp,html页面，并且跳转到相应页面
        若返回json等内容到页面，则需要加@ResponseBody注解
        在Restful的服务中，我们大部分情况是使用JSON为返回数据的，所以可以直接使用@Controller注解
 *
 * @RequestMapping注解是用来映射请求的，即指明处理器可以处理哪些URL请求，该注解既可以用在类上，也可以用在方法上
 * 当使用@RequestMapping标记控制器类时，方法的请求地址是相对类的请求地址而言的；当没有使用@RequestMapping标记类时，方法的请求地址是绝对路径
 * @ResponseBody注解用于将Controller的方法返回的对象，通过适当的HttpMessageConverter转换为指定格式后，写入到Response对象的body数据区
 */
@Controller
@RequestMapping("chat")
public class ChatController {

    @Autowired
    ChatService chatService;

    @Autowired
    UserService userService;

    @Autowired
    FriendService friendService;

    @GetMapping("/")
    public String doRecord() {
        return "friends_record";
    }

    @GetMapping("/{userId}")
    public String doRecordToUser(@PathVariable Long userId, Model model,
                                 HttpServletResponse response, HttpSession session) throws IOException {
        User user1 = userService.findUserById(userId);
        User user2 = (User) session.getAttribute("user");
        if (!friendService.isFriend(userId, user2.getId())) {
            response.getWriter().print("<script language='javascript'>alert('你和 " + user1.getNickname() + " 还是好友哦，请先添加好友吧');"
                    + "window.location.href='/dev/friend/';</script>");
        }
        model.addAttribute("toFriend", user1);
        return "friends_record";
    }

    @GetMapping("/record")
    @ResponseBody
    public List<ChatVO> doRecord(HttpSession session) {
        // 读取当前用户的最近记录(只显示对方名字/头像)
        User user = (User) session.getAttribute("user");
        List<ChatVO> chatList = chatService.record(user.getUsername());
        // System.out.println(chatList);
        return chatList;
    }

    /**
     * 返回chatList
     *
     * @param username 对方用户名
     * @return
     */
    @GetMapping("/record/{username}")
    @ResponseBody
    public List<Object> recordByUsername(@PathVariable String username, HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<Object> chatList = chatService.record(username, user.getUsername());
        // System.out.println(chatList);
        return chatList;
    }

    @GetMapping("/friendId/{username}")
    @ResponseBody
    public Long FriendIdByUsername(@PathVariable String username) {
        User user = userService.findUser(username);
        return user.getId();
    }
}
