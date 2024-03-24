package com.jxufe.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jxufe.entity.Picture;
import com.jxufe.entity.User;
import com.jxufe.service.PictureService;
import com.jxufe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
        List<Picture> pictures = pictureService.listPictures(user.getId());
        PageHelper.startPage(pageNum, 4);
        PageInfo<Picture> page = new PageInfo<>(pictures);
        System.out.println(page.getTotal() + "" + page.getList());
        model.addAttribute("user", user);
        model.addAttribute("pagePicture", page);
        return "picture";
    }

    @GetMapping("/list/{id}")
    public String listPictureByUser(@RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum,
                                    @PathVariable Long id, Model model, HttpSession session) {
        List<Picture> pictures = pictureService.listPictures(id);
        PageHelper.startPage(pageNum, 4);
        PageInfo<Picture> page = new PageInfo<>(pictures);
        model.addAttribute("user", userService.findUserById(id));
        model.addAttribute("pagePicture", page);
        return "picture";
    }

    @GetMapping("/me/input")
    public String input(Model model) {
        model.addAttribute("picture", new Picture());
        return "输入返回路径";
    }

    @PostMapping("/me/save")
    public String post(@Valid Picture picture, RedirectAttributes attributes) {
        int p = pictureService.savePicture(picture);
        if (p == 0) {
            attributes.addFlashAttribute("message", "新增失败");
            return "redirect:输入转发路径";
        } else {
            attributes.addFlashAttribute("message", "新增成功");
        }
        return "redirect:输入转发路径";
    }

    @GetMapping("/me/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("picture", pictureService.getPictureById(id));
        return "输入返回路径";
    }

    @PostMapping("/me/{id}")
    public String editPost(@Valid Picture picture, RedirectAttributes attributes) {
        int p = pictureService.updatePicture(picture);
        if (p == 0) {
            attributes.addFlashAttribute("message", "修改失败");
        } else {
            attributes.addFlashAttribute("message", "修改成功");
        }
        return "redirect:输入转发路径";
    }

    @GetMapping("/me/{id}/delete")
    public String deletePicture(@PathVariable Long id, RedirectAttributes attributes) {
        pictureService.deletePicture(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:输入转发路径";
    }
}
