package com.jxufe.web;

import com.jxufe.entity.Blog;
import com.jxufe.entity.Comment;
import com.jxufe.entity.User;
import com.jxufe.service.BlogService;
import com.jxufe.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URI;
import java.util.List;

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
    public void doPost(@RequestBody Comment comment, HttpSession session, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        System.out.println(comment);

        User user = (User) session.getAttribute("user"); //获取当前登录用户
        System.out.println(user);
        if (user == null) {
            response.getWriter().print("<script language='javascript'>alert('请登录后再评论');" +
                    "window.location.href='/dev/login';</script>");
            return;
        }
        Long blogId = comment.getId();
        comment.setBlogId(blogId);
        Blog blog = blogService.getBlog(blogId);
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

    /**
     * 删除评论
     * @param blogId
     * @param id
     * @param comment
     * @param response
     * @return
     */
    @GetMapping("/comment/{blogId}/{id}/delete")
    public void delete(@PathVariable Long blogId, @PathVariable Long id, Comment comment, HttpServletResponse response) throws IOException {
        commentService.deleteComment(comment, id);
        response.sendRedirect("/dev/user_blog/" + blogId);
    }
}
