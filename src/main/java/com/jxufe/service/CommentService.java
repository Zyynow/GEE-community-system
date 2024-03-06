package com.jxufe.service;

import com.jxufe.entity.Comment;

import java.util.List;

public interface CommentService {

    Comment saveComment(Comment comment);

    List<Comment> findListByBlogId(Long id);
}
