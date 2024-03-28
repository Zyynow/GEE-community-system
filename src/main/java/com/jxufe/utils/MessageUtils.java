package com.jxufe.utils;

import com.alibaba.fastjson.JSON;
import com.jxufe.websocket.pojo.Message;
import com.jxufe.websocket.pojo.ResultMessage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @version v1.0
 * @ClassName: MessageUtils
 * @Description: 封装json格式消息的工具类
 * @Author: Kafka
 */
public class MessageUtils {

    /**
     * 将用户端发过来的请求从参数转为Json格式字符串
     * @param isSystemMessage 即是否是系统消息，若是则fromName为null
     * @param fromName 发送方的名字
     * @param message 发送的消息，json数据
     * @return Json格式字符串
     */
    public static String getMessage(boolean isSystemMessage, String fromName, Message message) {
        System.out.println("发送方：" + fromName);
        ResultMessage result = new ResultMessage();
        result.setSystem(isSystemMessage);
        result.setMessage(message.getMessage());
        // 格式化时间
//        LocalDateTime now = LocalDateTime.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd HH:mm");
//        String formattedTime = now.format(formatter);
        result.setSendTime(message.getSendTime());
        System.out.println(result);
        if(fromName != null) {
            result.setFromName(fromName);
        }
        return JSON.toJSONString(result);
    }
}
