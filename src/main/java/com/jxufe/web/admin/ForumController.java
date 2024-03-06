package com.jxufe.web.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class ForumController {

    @GetMapping("/forums")
    public String doForums(HttpSession session) {
        return "admin/forums";
    }
}
