package com.jxufe.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_collection")
public class Favourites {

    @Id
    @GeneratedValue
    private Long id;

    private Long userId;

    private Long blogId;

    private Date time;

    private String blogTitle;

    private Long resourceId;

    private String resourceTitle;

    private boolean type; // true博客 false帖子

    private Long blogUserId;
}
