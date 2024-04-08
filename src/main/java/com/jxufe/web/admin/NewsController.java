package com.jxufe.web.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jxufe.entity.Admin;
import com.jxufe.entity.Feedback;
import com.jxufe.entity.Forum;
import com.jxufe.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("admin")
public class NewsController {

    @Autowired
    private FeedbackService feedbackService;

    @GetMapping("/news")
    public String doNews(@RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum, Model model) {
        PageHelper.startPage(pageNum, 6);
        PageInfo<Feedback> page = feedbackService.pageFeedbacks().toPageInfo();
        System.out.println(page);
        model.addAttribute("pageNews", page);
        return "admin/news";
    }

    @PostMapping("/news/delete/{id}")
    @Transactional
    public String deleteNew(@PathVariable Long id, RedirectAttributes attributes, HttpSession session) {
        feedbackService.deleteFeedback(id);
        attributes.addFlashAttribute("message", "删除操作成功。");
        return "redirect:/admin/forums/";
    }
}
