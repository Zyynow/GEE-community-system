package com.jxufe.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_forum")
public class Forum {

    @Id
    @GeneratedValue
    private Integer id;
    private String forumName;
    private Integer peopleNumber;
    private Integer blogNumber;
    private Integer popular;
    private String promotionalImage;
    private String description;

    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "forum")
    private List<Blog> blogs;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

    public String getForumName() {
        return forumName;
    }

    public void setForumName(String forumName) {
        this.forumName = forumName;
    }

    public Integer getPeopleNumber() {
        return peopleNumber;
    }

    public void setPeopleNumber(Integer peopleNumber) {
        this.peopleNumber = peopleNumber;
    }

    public Integer getBlogNumber() {
        return blogNumber;
    }

    public void setBlogNumber(Integer blogNumber) {
        this.blogNumber = blogNumber;
    }

    public Integer getPopular() {
        return popular;
    }

    public void setPopular(Integer popular) {
        this.popular = popular;
    }

    public String getPromotionalImage() {
        return promotionalImage;
    }

    public void setPromotionalImage(String promotionalImage) {
        this.promotionalImage = promotionalImage;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Forum{" +
                "id=" + id +
                ", forumName='" + forumName + '\'' +
                ", peopleNumber=" + peopleNumber +
                ", blogNumber=" + blogNumber +
                ", popular=" + popular +
                ", promotionalImage='" + promotionalImage + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
