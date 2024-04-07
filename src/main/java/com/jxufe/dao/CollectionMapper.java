package com.jxufe.dao;

import com.jxufe.entity.Blog;
import com.jxufe.entity.Favourites;
import com.jxufe.entity.Resource;
import com.jxufe.vo.CollectionVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CollectionMapper {

    int addCollectionBlog(Favourites favourites);

    int addCollectionResource(Favourites favourites);

    int deleteCollectionBlog(Long userId, Long blogId);

    int deleteCollectionResource(Long userId, Long resourceId);

    List<CollectionVO> collectionBlogList(Long userId);

    List<CollectionVO> collectionRecourseList(Long userId);

    int deleteResourceId(Long id);

    int deleteBlogById(Long id);

    void deleteCollectionByUser(Long userId);

    Favourites isCollectionBlog(Long userId, Long blogId);

    Favourites isCollectionResource(Long userId, Long resourceId);

    List<Resource> getCollectionResourceMax();

    List<Blog> getCollectionBlogMax();

    void updateCollectionByBlog(Long blogId, String title);

    void updateCollectionByResource(Long resourceId, String title);

    void deleteCollectionByBlog(Long blogId);

    void deleteCollectionByResource(Long resourceId);
}
