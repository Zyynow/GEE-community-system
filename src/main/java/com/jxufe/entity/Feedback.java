package com.jxufe.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_feedback")
public class Feedback {

    @Id
    @GeneratedValue
    private Integer id;
    @Basic(fetch = FetchType.LAZY)
    @Lob
    private String content;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    private String title;
    /*
    暂时设定反馈不区分用户
     */

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", title='" + title + '\'' +
                '}';
    }
}
