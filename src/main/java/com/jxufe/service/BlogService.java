package com.jxufe.service;

import com.jxufe.entity.Blog;
import com.jxufe.entity.Favourites;
import com.jxufe.vo.BlogQuery;
import com.jxufe.vo.CollectionVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BlogService {

    Blog getBlog(Long id);

    List<Blog> findBlogs(Integer id);

    List<Blog> findBlogsByTag(Integer forumId, Long tagId);

    //通过类型来搜索
    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

    Page<Blog> listBlog(Pageable pageable);

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id, Blog blog);

    void deleteBlog(Long id);

    void deleteBlogsByUser(Long id);

    List<Blog> listRecommendBlogTop(Integer size);

    List<Blog> listBlogTopById(Integer size, Long id);

    //全局搜索
    Page<Blog> listBlog(Pageable pageable, String query);

    Blog getAndConvert(Long id);

    Map<String, List<Blog>> archivesBlog();

    Long countBlog();

    Integer updateViews(Long id);

    List<Blog> getArchiveBlogs(Long userId);

    int collectionBlog(Favourites favourites);

    int removeCollectionBlog(Long userId, Long blogId);

    int removeBlogById(Long id);

    List<CollectionVO> collectionBlogList(Long id);

    boolean isCollectionBlog(Long userId, Long BlogId);

    List<Blog> getCollectionMax();

    List<Blog> getHotBlog();

    List<Blog> listBlog(Long id);

    void removeCollection(Long blogId);
}
