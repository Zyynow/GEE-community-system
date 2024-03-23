package com.jxufe.web;

import com.jxufe.entity.Blog;
import com.jxufe.entity.Forum;
import com.jxufe.entity.User;
import com.jxufe.service.BlogService;
import com.jxufe.service.ForumService;
import com.jxufe.service.TagService;
import com.jxufe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
public class ForumShowController {

    @Resource
    ForumService forumService;

    @Autowired
    TagService tagService;

    @Autowired
    UserService userService;

    @Autowired
    BlogService blogService;

    @GetMapping("/forum")
    public String doForum(HttpServletRequest request, Model model) {
        request.getSession().setAttribute("lastPath", request.getServletPath());

        model.addAttribute("forums", forumService.listForums());
        model.addAttribute("tags", tagService.listTag());
        return "forum";
    }

    @GetMapping("/user_forum")
    public String doUserForum(HttpServletRequest request, Model model, HttpSession session) {
        session.setAttribute("lastPath", request.getServletPath());

        User user = (User) session.getAttribute("user");
        model.addAttribute("forums", forumService.listForums());
        model.addAttribute("tags", tagService.listTag());
        model.addAttribute("joinForums", forumService.findJoinForums(user.getId()));
        model.addAttribute("notJoinForums", forumService.findNotJoinForums(user.getId()));
        return "user_forum";
    }

    @GetMapping("/forum/fblog/{id}")
    public String doFblog(@PageableDefault(size = 999, sort = {"updateTime"}, direction = Sort.Direction.DESC)
                                  Pageable pageable, @PathVariable Integer id, HttpServletRequest request, Model model) {
        request.getSession().setAttribute("lastPath", request.getServletPath());

        model.addAttribute("forum", forumService.getForumById(id));
        model.addAttribute("tags", tagService.listTag());
        model.addAttribute("blogs", blogService.findBlogs(id));
        return "fblog";
    }

    @GetMapping("/user_forum/fblog/{id}")
    public String doUserFblog(@PageableDefault(size = 999, sort = {"updateTime"}, direction = Sort.Direction.DESC)
                                      Pageable pageable, @PathVariable Integer id, HttpServletRequest request, Model model) {
        request.getSession().setAttribute("lastPath", request.getServletPath());

        model.addAttribute("forum", forumService.getForumById(id));
        model.addAttribute("tags", tagService.listTag());
        model.addAttribute("blogs", blogService.findBlogs(id));
        return "user_fblog";
    }

    @GetMapping({"/user_forum/join", "/forum/join"})
    public void doForumJoin(Integer userId, Integer forumId, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        Integer res = forumService.joinForum(userId, forumId);
        if (res == 1) {
            response.getWriter().print("<script language='javascript'>alert('添加成功');" +
                    "window.location.href='/dev/user_forum';</script>");
        } else {
            response.getWriter().print("<script language='javascript'>alert('添加失败');" +
                    "window.location.href='/dev/user_forum';</script>");
        }
    }

    @GetMapping({"/user_forum/exit"})
    public void doForumExit(Integer userId, Integer forumId, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        Integer res = forumService.exitForum(userId, forumId);
        if (res == 1) {
            response.getWriter().print("<script language='javascript'>alert('退出成功');" +
                    "window.location.href='/dev/user_forum';</script>");
        } else {
            response.getWriter().print("<script language='javascript'>alert('退出失败');" +
                    "window.location.href='/dev/user_forum';</script>");
        }
    }

    @GetMapping("/fblog/input")
    public String doWriteBlog(Model model) {
        model.addAttribute("forums", forumService.listForums());
        model.addAttribute("tags", tagService.listTag());
        return "fblog-input";
    }

    @PostMapping("/fblog/input/success")
    public void doWriteBlog(Blog blog, HttpSession session, HttpServletResponse response) throws IOException {
        if (blog.getContent().equals("") || blog.getDescription().equals("") ||
                blog.getTitle().equals("") || blog.getFirstPicture().equals("") ||
                blog.getForum() == null || blog.getTag() == null) {
            response.getWriter().print("<script language='javascript'>alert('内容请填写完整');" +
                    "window.location.href='/dev/fblog/input';</script>");
        } else {
            User user = (User) session.getAttribute("user");
            blog.setUser(user);
            blogService.saveBlog(blog);
            userService.updateBlogNum(user.getId());
            response.sendRedirect("/dev/user_index");
        }
    }
}
