package com.jxufe.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_picture")
public class Picture implements Serializable {

    @Id
    @GeneratedValue //生成策略
    private Long id;

    private String pictureName;

    @Temporal(TemporalType.TIMESTAMP)
    private Date pictureTime;

    private String pictureAddress;

    private String pictureDescription;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    @ManyToOne
    private User user;
}
