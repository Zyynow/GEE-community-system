package com.jxufe.websocket.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @version v1.0
 * @ClassName: Message
 * @Description: 用于封装浏览器发送给服务端的消息数据
 * @Author: Kafka
 */
@Data // lombok，可以隐藏getter和setter
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    private String toName;

    private String message;

    @DateTimeFormat(pattern = "MM/dd HH:mm")
    @JsonFormat(pattern = "MM/dd HH:mm", timezone = "GMT+8")
    private Date sendTime;
}
