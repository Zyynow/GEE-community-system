package com.jxufe.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_type")
public class Type {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message = "分类名称不能为空")
    private String name;

    /**
     * Type和Blog之间的关系，一对多的关系
     * mappedBy = "type" : 表示type被维护他们两者的关系，对应的blog主动维护二者关系
     */
    @OneToMany(mappedBy = "type")
    private List<Resource> resources = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Resource> getBlogs() {
        return resources;
    }

    public void setBlogs(List<Resource> resources) {
        this.resources = resources;
    }

    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
