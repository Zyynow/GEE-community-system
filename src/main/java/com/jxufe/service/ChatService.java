package com.jxufe.service;

import com.jxufe.entity.Chat;
import com.jxufe.vo.ChatVO;
import com.jxufe.websocket.pojo.Message;

import java.util.List;

public interface ChatService {
    List<ChatVO> record(String username);

    List<Object> record(String senderUsername, String recipientUsername);

    void saveChat(Message msg, String sender);

    int clearRecordByUser(String username1, String username2);
}
