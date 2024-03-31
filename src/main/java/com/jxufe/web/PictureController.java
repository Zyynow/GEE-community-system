package com.jxufe.web;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jxufe.entity.Picture;
import com.jxufe.entity.User;
import com.jxufe.service.PictureService;
import com.jxufe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/picture")
public class PictureController {

    @Autowired
    private PictureService pictureService;

    @Autowired
    private UserService userService;

    @GetMapping("/me/list")
    public String pictures(@RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum,
                           Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        PageHelper.startPage(pageNum, 4);
        PageInfo<Picture> page = pictureService.listPictures(user.getId()).toPageInfo();
//        System.out.println(page.getTotal() + " " + page.getPages() + " " + page.getList());
        model.addAttribute("user", user);
        model.addAttribute("pagePicture", page);
        return "picture";
    }

    @GetMapping("/list/{id}")
    public String listPictureByUser(@RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum,
                                    @PathVariable Long id, Model model) {
        PageHelper.startPage(pageNum, 4);
        PageInfo<Picture> page = pictureService.listPictures(id).toPageInfo();
//        System.out.println(page.getTotal() + " " + page.getPages() + " " + page.getList());
        model.addAttribute("user", userService.findUserById(id));
        model.addAttribute("pagePicture", page);
        return "picture";
    }

    @PostMapping("/me/save")
    public String savePost(@Valid Picture picture, RedirectAttributes attributes, HttpSession session) {
        int p = pictureService.savePicture(picture, session);
        if (p == 0) {
            attributes.addFlashAttribute("messageFailed", "添加失败了哦，找找是什么原因吧");
        } else {
            attributes.addFlashAttribute("messageSuccessful", "添加成功啦！");
        }
        return "redirect:/picture/me/list";
    }

    @GetMapping("/me/{id}")
    @ResponseBody
    public Picture doEdit(@PathVariable Long id, Model model) {
        return pictureService.getPictureById(id);
    }

    @PostMapping("/me/edit")
    public String editPost(@Valid Picture picture, RedirectAttributes attributes) {
        int p = pictureService.updatePicture(picture);
        if (p == 0) {
            attributes.addFlashAttribute("messageFailed", "修改失败了哦，找找是什么原因吧");
        } else {
            attributes.addFlashAttribute("messageSuccessful", "修改成功啦！");
        }
        return "redirect:/picture/me/list";
    }

    @GetMapping("/me/delete/{id}")
    @Transactional
    public String deletePicture(@PathVariable Long id, RedirectAttributes attributes) {
        pictureService.deletePicture(id);
        attributes.addFlashAttribute("messageSuccessful", "删除成功啦！");
        return "redirect:/picture/me/list";
    }
}
