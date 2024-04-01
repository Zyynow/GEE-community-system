package com.jxufe.web;

import com.jxufe.entity.User;
import com.jxufe.service.BlogService;
import com.jxufe.service.ForumService;
import com.jxufe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
public class AboutController {

    @Resource
    UserService userService;

    @Autowired
    BlogService blogService;

    @Autowired
    ForumService forumService;

    @GetMapping("/about")
    public String doAbout(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", userService.findUserById(user.getId()));
        return "about";
    }

    @GetMapping("/otherAbout/{id}")
    public String doOtherAbout(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.findUserById(id));
        model.addAttribute("forums", forumService.findJoinForums(id));
        model.addAttribute("blogs", blogService.listBlogTopById(3, id));
        return "other_about";
    }

    @GetMapping("/userOtherAbout/{id}")
    public String doUserOtherAbout(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.findUserById(id));
        model.addAttribute("forums", forumService.findJoinForums(id));
        model.addAttribute("blogs", blogService.listBlogTopById(3, id));
        return "user_other_about";
    }

    @GetMapping("/about/detail")
    public String doAboutDetail() {
        return "about_detail_info";
    }

    @GetMapping("/about/edit")
    public String doAboutEdit() {
        return "about_edit";
    }

    @PostMapping("/about/edit/success")
    public String doAboutEdit(User user) {
        userService.editUserInfo(user);
        return "redirect:/about";
    }

    @GetMapping("/about/space")
    public String doAboutSpace() {
        return "personal_space";
    }

}
