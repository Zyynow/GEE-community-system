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
@Table(name = "t_apply")
public class Apply {

    @Id
    @GeneratedValue
    private Long id;

    private String senderId;

    private String recipientId;

    private Date applyTime;
}
