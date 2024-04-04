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
@Table(name = "t_view")
public class View {

    @Id
    @GeneratedValue
    private Long id;

    private Long viewerId;

    private Long viewedId;

    private Integer count;
}
