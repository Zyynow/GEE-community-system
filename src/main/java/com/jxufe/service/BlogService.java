package com.jxufe.service;

import com.jxufe.entity.ArchiveBlog;
import com.jxufe.entity.Blog;
import com.jxufe.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BlogService {

    Blog getBlog(Long id);

    List<Blog> findBlogs(Integer id);

    //通过类型来搜索
    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

    Page<Blog> listBlog(Pageable pageable);

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id, Blog blog);

    void deleteBlog(Long id);

    List<Blog> listRecommendBlogTop(Integer size);

    List<Blog> listBlogTopById(Integer size, Long id);

    //全局搜索
    Page<Blog> listBlog(Pageable pageable, String query);

    Blog getAndConvert(Long id);

    Map<String, List<Blog>> archivesBlog();

    Long countBlog();

    Integer updateViews(Long id);

    List<Blog> getArchiveBlogs(Long userId);
}
