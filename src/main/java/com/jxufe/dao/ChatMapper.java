package com.jxufe.dao;

import com.jxufe.entity.Chat;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatMapper {

    List<Chat> record(String username);

    List<Chat> findRecordByUsername(String senderUsername, String recipientUsername);

    int saveChat(Chat chat);

    Integer clearRecordByUser(String username1, String username2);

    void deleteChatByUser(String username);
}
