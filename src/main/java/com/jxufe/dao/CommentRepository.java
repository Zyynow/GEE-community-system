package com.jxufe.dao;

import com.jxufe.entity.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    //List<Comment> findByBlogIdAndParentCommentNull(Long id, Sort sort);

    // 根据blogId查询所有一级评论
    @Query("select c from Comment c where c.blogId = ?1 and c.parentCommentId = null")
    List<Comment> findCommentsById(Long blogId);

    // 根据一级评论id查找所有二级评论
    @Query("select c from Comment c where c.parentCommentId = ?1")
    List<Comment> findSubCommentsById(Long parentCommentId);
}
