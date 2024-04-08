package com.jxufe.web.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jxufe.entity.Admin;
import com.jxufe.entity.Forum;
import com.jxufe.entity.Picture;
import com.jxufe.entity.User;
import com.jxufe.service.ForumService;
import com.jxufe.vo.ResourceQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/admin/forums")
public class ForumController {

    @Autowired
    private ForumService forumService;

    @GetMapping({"", "/"})
    public String doForums(@RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum, Model model) {
        PageHelper.startPage(pageNum, 6);
        PageInfo<Forum> page = forumService.pageForums().toPageInfo();
        model.addAttribute("pageForums", page);
        return "admin/forums";
    }

    @GetMapping("/search")
    public String doSearch(@RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum,
                           @RequestParam(value = "forumName") String forumName, Model model) {
        PageHelper.startPage(pageNum, 6);
        PageInfo<Forum> page = forumService.pageSearch(forumName).toPageInfo();
        model.addAttribute("pageForums", page);
        return "admin/forums";
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Forum getMyForum(@PathVariable Integer id, Model model) {
        return forumService.getForumById(id);
    }

    @PostMapping("/delete/{id}")
    @Transactional
    public String deletePicture(@PathVariable Long id, RedirectAttributes attributes, HttpSession session) {
        Admin admin = (Admin) session.getAttribute("admin");
        if (!admin.getUsername().equals("root")) {
            attributes.addFlashAttribute("messageFailed", "非顶级管理员没有权限删除论坛社区");
            return "redirect:/admin/forums/";
        }
        forumService.deleteForum(id);
        attributes.addFlashAttribute("messageSuccessful", "删除成功啦！");
        return "redirect:/admin/forums/";
    }

    @PostMapping("/save")
    public String savePost(@Valid Forum forum, RedirectAttributes attributes, HttpSession session) {
        int p = forumService.saveForum(forum);
        if (p == 0) {
            attributes.addFlashAttribute("messageFailed", "添加失败了哦，找找是什么原因吧");
        } else {
            attributes.addFlashAttribute("messageSuccessful", "添加成功啦！");
        }
        return "redirect:/admin/forums/";
    }

    @PostMapping("/edit")
    @Transactional
    public String editPost(@Valid Forum forum, RedirectAttributes attributes) {
        int p = forumService.updateForum(forum);
        if (p == 0) {
            attributes.addFlashAttribute("messageFailed", "修改失败了哦，找找是什么原因吧");
        } else {
            attributes.addFlashAttribute("messageSuccessful", "修改成功啦！");
        }
        return "redirect:/admin/forums/";
    }
}
