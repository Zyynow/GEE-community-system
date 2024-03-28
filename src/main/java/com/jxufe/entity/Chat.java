package com.jxufe.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_chat")
public class Chat {

    @Id
    @GeneratedValue //生成策略
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date sendTime;

    private String senderUsername;

    private String recipientUsername;

    private String content;

    private boolean type;

    private boolean isRead;
}
