package com.jxufe.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_comment")
public class Comment {

    @Id
    @GeneratedValue
    private Long id;
    private String nickname;
    @Basic(fetch = FetchType.LAZY)
    @Lob
    private String content;
    private String avatar;
    private String parentNickname;
    private String hostId;

    private boolean adminComment; //管理员标记

    @Temporal(TemporalType.TIMESTAMP) //时间+日期
    private Date createTime;

    // 父评论
    private Long parentCommentId;

    // 子评论
    @Transient // 不会尝试为这个字段创建数据库列映射，而是需要在应用层通过业务逻辑或者其他方式维护
    private List<Comment> childComment;

    private Long blogId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    public boolean isAdminComment() {
        return adminComment;
    }

    public void setAdminComment(boolean b) {
        adminComment = b;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", content='" + content + '\'' +
                ", avatar='" + avatar + '\'' +
                ", parentNickname='" + parentNickname + '\'' +
                ", hostId='" + hostId + '\'' +
                ", adminComment=" + adminComment +
                ", createTime=" + createTime +
                ", parentCommentId=" + parentCommentId +
                ", childComment=" + childComment +
                ", blogId=" + blogId +
                '}';
    }

    public Long getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(Long parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    public List<Comment> getChildComment() {
        return childComment;
    }

    public void setChildComment(List<Comment> childComment) {
        this.childComment = childComment;
    }

    public String getParentNickname() {
        return parentNickname;
    }

    public void setParentNickname(String parentNickname) {
        this.parentNickname = parentNickname;
    }

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }
}
