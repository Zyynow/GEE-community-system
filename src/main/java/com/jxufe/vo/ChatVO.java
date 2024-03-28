package com.jxufe.vo;

import com.jxufe.entity.Chat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

/**
 * vo类表示按照前端意愿返回对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatVO {

    private String usernameTa;

    private String avatarTa;

    private String nicknameTa;

    private Chat chat;
}
