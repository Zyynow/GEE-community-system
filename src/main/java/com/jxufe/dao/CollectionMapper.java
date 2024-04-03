package com.jxufe.dao;

import com.jxufe.entity.Favourites;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CollectionMapper {

    int addCollectionBlog(Favourites favourites);

    int addCollectionResource(Favourites favourites);

    int deleteCollectionBlog(Long userId, Long blogId);

    int deleteCollectionResource(Long userId, Long resourceId);

    List<Favourites> collectionBlogList(Long userId);

    List<Favourites> collectionRecourseList(Long userId);

    int deleteResourceId(Long id);

    int deleteBlogById(Long id);

    Favourites isCollectionBlog(Long userId, Long blogId);

    Favourites isCollectionResource(Long userId, Long resourceId);
}
