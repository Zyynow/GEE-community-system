package com.jxufe.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_friend")
public class Friend {

    @Id
    @GeneratedValue
    private Long id;

    private Long user1Id;

    private Long user2Id;

    private String user1Nickname;

    private String user2Nickname;

    private String user1Description;

    private String user2Description;

    private String user1Avatar;

    private String user2Avatar;

    private String user1Username;

    private String user2Username;
}
