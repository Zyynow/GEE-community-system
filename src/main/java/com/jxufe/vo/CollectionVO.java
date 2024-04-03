package com.jxufe.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollectionVO {

    private Long id; // 博客id或者帖子id

    private String title;

    private Date time; // 收藏时间

    private String tag; // 标签化
}
