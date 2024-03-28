package com.jxufe.websocket.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @version v1.0
 * @ClassName: ResultMessage
 * @Description: 用来封装服务端给浏览器发送的消息数据
 * @Author: Kafka
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultMessage {

    private boolean isSystem;

    private String fromName;

    private Object message; // 如果是系统消息是数组

    @DateTimeFormat(pattern = "MM/dd HH:mm")
    @JsonFormat(pattern = "MM/dd HH:mm", timezone = "GMT+8")
    private Date sendTime;
}
