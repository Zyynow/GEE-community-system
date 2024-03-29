package com.jxufe.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendVO {

    private Long userId;

    private String username;

    private String avatar;

    private String description;

    private String nickname;
}
