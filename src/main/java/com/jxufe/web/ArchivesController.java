package com.jxufe.web;

import com.jxufe.entity.User;
import com.jxufe.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class ArchivesController {

    @Autowired
    private BlogService blogService;

    /**
     * 按照月份展示购买记录的(下次使用)
     *
     * @param model
     * @return
     */
    /*@GetMapping("/archives")
    public String doArchives(Model model) {
        model.addAttribute("archiveMap", blogService.archivesBlog());
        model.addAttribute("blogCount", blogService.countBlog());
        return "consumption_record";
    }*/

    /**
     * 发帖记录(按照时间线递减)
     * @param model
     * @param session
     * @return
     */
    @GetMapping("/archives")
    public String archives(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("myBlogs", blogService.getArchiveBlogs(user.getId()));
        return "archives";
    }
}
