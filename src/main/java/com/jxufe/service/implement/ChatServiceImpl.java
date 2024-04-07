package com.jxufe.service.implement;

import com.jxufe.dao.ChatMapper;
import com.jxufe.dao.UserMapper;
import com.jxufe.entity.Chat;
import com.jxufe.entity.User;
import com.jxufe.service.ChatService;
import com.jxufe.vo.ChatVO;
import com.jxufe.websocket.pojo.Message;
import com.jxufe.websocket.pojo.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {

    @Resource
    ChatMapper chatMapper;

    @Resource
    UserMapper userMapper;

    @Override
    public List<ChatVO> record(String username) {
        List<Chat> chatList = chatMapper.record(username);
        List<ChatVO> chatVOList = new ArrayList<>();
        for (Chat chat : chatList) {
            if (chat.getSenderUsername().equals(username)) {
                chatVOList.add(createChatVO(chat.getRecipientUsername(), chat));
            } else {
                chatVOList.add(createChatVO(chat.getSenderUsername(), chat));
            }
        }
        return chatVOList;
    }

    /**
     * 返回聊天记录对象
     * @param senderUsername 对方用户名
     * @param recipientUsername 我的用户名
     * @return
     */
    @Override
    public List<Object> record(String senderUsername, String recipientUsername) {
        List<Chat> chatList = chatMapper.findRecordByUsername(senderUsername, recipientUsername);
        List<Object> messageList = new ArrayList<>();
        for (Chat chat : chatList) {
            // 发送者是我
            if (chat.getSenderUsername().equals(recipientUsername)) {
                Message message = new Message(recipientUsername, chat.getContent(), chat.getSendTime());
                messageList.add(message);
            }
            // 发送者不是我
            if (chat.getRecipientUsername().equals(recipientUsername)) {
                ResultMessage message = new ResultMessage(false, senderUsername, chat.getContent(), chat.getSendTime());
                messageList.add(message);
            }
        }
        return messageList;
    }

    @Override
    public void saveChat(Message msg, String sender) {
        Chat chat = new Chat();
        chat.setContent(msg.getMessage());
        chat.setRead(true); // 已读(这个功能暂时不需要)
        chat.setType(false); // 普通类型
        chat.setSenderUsername(sender);
        chat.setRecipientUsername(msg.getToName());
        chat.setSendTime(msg.getSendTime());
        chatMapper.saveChat(chat);
    }

    @Override
    public int clearRecordByUser(String username1, String username2) {
        return chatMapper.clearRecordByUser(username1, username2);
    }

    @Override
    public void deleteChatByUser(String username) {
        chatMapper.deleteChatByUser(username);
    }

    public ChatVO createChatVO(String username, Chat chat) {
        User user = userMapper.findByUsername(username);
        ChatVO chatVO = new ChatVO();
        chatVO.setChat(chat);
        chatVO.setAvatarTa(user.getAvatar());
        chatVO.setNicknameTa(user.getNickname());
        chatVO.setUsernameTa(user.getUsername());
        return chatVO;
    }
}
