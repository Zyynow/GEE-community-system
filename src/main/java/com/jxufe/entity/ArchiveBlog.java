package com.jxufe.entity;

import java.util.Date;

public class ArchiveBlog {

    // 博客id
    private Long id;
    // 博客标题
    private String title;
    // 博客创建时间
    private Date createTime;

    @Override
    public String toString() {
        return "ArchiveBlog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", createTime=" + createTime +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public ArchiveBlog() {
    }
}
