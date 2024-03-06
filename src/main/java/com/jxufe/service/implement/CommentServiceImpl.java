package com.jxufe.service.implement;

import com.jxufe.dao.CommentRepository;
import com.jxufe.entity.Comment;
import com.jxufe.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 1、我们想到的是非空校验
 * 2、回复别人的时候会出现@xxx
 * 3、提交回复内容后，会刷新之前的页面，把提交的内容遍历出来。
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Comment saveComment(Comment comment) {
        comment.setCreateTime(new Date());
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> findListByBlogId(Long id) {
        return commentRepository.findAllById(Collections.singleton(id));
    }
}