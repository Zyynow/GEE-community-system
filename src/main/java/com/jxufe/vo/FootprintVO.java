package com.jxufe.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FootprintVO {

    private Long userId;

    private String username;

    private String nickname;

    private String avatar;

    private Long count;
}
