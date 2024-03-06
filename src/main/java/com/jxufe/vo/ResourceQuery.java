package com.jxufe.vo;

/**
 * vo类表示将联表查询的多个对象封装成一个对象
 * 前端页面传过来的如表单等数据的字段，比如当前端填写了一个表单，当前端传过来的数据较多时，
 * 我们可以创建一个vo实体类，将前端传来的数据字段名作为成员变量名，
 * 这样我们就可以使用@RequestBody注解快速获取参数内容，而不需要使用Request对象来一个个获取，方便开发。
 */
public class ResourceQuery {

    private String title;
    private Long typeId;
    private boolean recommend;

    public ResourceQuery() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }
}
