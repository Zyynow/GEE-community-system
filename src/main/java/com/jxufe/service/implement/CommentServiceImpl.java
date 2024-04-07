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
        return commentRepository.findCommentsById(id);
    }

    @Override
    public List<Comment> findSubListByParentId(Long id) {
        return commentRepository.findSubCommentsById(id);
    }

    @Override
    public List<Comment> findGrandListBySonId(Long id) {
        return commentRepository.findGrandCommentsById(id);
    }

    @Override
    public void deleteComment(Comment comment, Long id) {
        commentRepository.deleteById(id);
        // 删除所有子评论
        commentRepository.deleteSub(id);
    }

    @Override
    public void deleteCommentByBlog(Long blogId) {
        commentRepository.deleteByBlog(blogId);
    }

    @Override
    public void deleteCommentByUser(Long userId) {
        commentRepository.deleteCommentByUser(userId);
    }

    @Override
    public void updateComments(Long userId, String nickname, String avatar) {
        commentRepository.updateCommentByUser(userId, nickname, avatar);
        commentRepository.updateSubCommentByUser(userId, nickname);
    }

}
