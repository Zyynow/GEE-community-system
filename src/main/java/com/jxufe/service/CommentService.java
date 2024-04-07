package com.jxufe.service;

import com.jxufe.entity.Comment;

import java.util.List;

public interface CommentService {

    Comment saveComment(Comment comment);

    List<Comment> findListByBlogId(Long id);

    List<Comment> findSubListByParentId(Long id);

    List<Comment> findGrandListBySonId(Long id);

    void deleteComment(Comment comment, Long id);

    void deleteCommentByBlog(Long blogId);

    void deleteCommentByUser(Long userId);

    void updateComments(Long userId, String nickname, String avatar);
}
