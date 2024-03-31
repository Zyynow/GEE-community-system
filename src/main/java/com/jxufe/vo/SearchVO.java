package com.jxufe.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchVO {

    private Long userId;

    private String username;

    private String avatar;

    private String nickname;

    private String signature;

    private boolean isFriend; // 前端接收会被映射为friend

    private boolean isMe; // 前端接收会被映射为me
}
