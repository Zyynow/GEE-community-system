package com.jxufe.web;

import com.jxufe.entity.Blog;
import com.jxufe.entity.Comment;
import com.jxufe.entity.User;
import com.jxufe.service.BlogService;
import com.jxufe.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * ${} -> 注入的是外部配置文件对应的property
     * #{} -> SpEL表达式对应的内容 -> 需要实体类
     */
    @Value("${comment.avatar}")
    private String avatar;

    @Autowired
    private BlogService blogService;

    @PostMapping("/comments/save")
    public void doPost(Comment comment, HttpSession session, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=UTF-8");

        System.out.println(comment);
        Long blogId = comment.getId();
        comment.setBlogId(blogId);
        Blog blog = blogService.getBlog(blogId);
        User user = (User) session.getAttribute("user"); //获取当前登录用户
        if (user == null) {
            response.getWriter().print("<script language='javascript'>alert('请登录后再评论');" +
                    "window.location.href='/dev/login';</script>");
            return;
        } else {
            comment.setNickname(user.getNickname());
            if (user.getId() == blog.getUser().getId()) { //如果是管理员就获取管理员的头像
                comment.setAvatar(blog.getUser().getAvatar());
                comment.setAdminComment(true);
            } else {
                comment.setAvatar(user.getAvatar());
            }
            commentService.saveComment(comment);
            response.sendRedirect("/dev/user_blog/" + blogId);
        }
    }
}
