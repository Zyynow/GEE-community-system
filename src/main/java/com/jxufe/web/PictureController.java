package com.jxufe.web;

import com.jxufe.entity.User;
import com.jxufe.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/picture")
public class PictureController {

    @Autowired
    private PictureService pictureService;

    @GetMapping("/me/list")
    public String pictures(Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        model.addAttribute("pictures",pictureService.listPictures(user.getId()));
        return "picture";
    }
}
