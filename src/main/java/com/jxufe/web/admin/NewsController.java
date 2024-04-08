package com.jxufe.web.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin")
public class NewsController {

    @GetMapping("/news")
    public String doNews() {
        return "admin/news";
    }
}
