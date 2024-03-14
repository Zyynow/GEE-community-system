package com.jxufe.web.admin;

import com.jxufe.entity.Tag;
import com.jxufe.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/admin")
public class TagController {

    @Autowired
    private TagService service;

    @GetMapping("/tags")
    public String doTags(@PageableDefault(size = 6, sort = {"id"}, direction = Sort.Direction.DESC)
                          Pageable pageable, Model model) {
        model.addAttribute("page", service.listTag(pageable));
        return "admin/tags";
    }

    @Transactional
    @GetMapping("/tags/input")
    public String doInput(Model model) {
        model.addAttribute("tag", new Tag());
        return "admin/tags-input";
    }

    @Transactional
    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("tag", service.getTag(id));
        return "admin/tags-input";
    }

    /*
        @Valid: 校验信息 结果在result中
        注：被@Valid注解的Type和BindingResult必须连着，校验才有效
     */
    @PostMapping("/tags")
    public String doPost(@Valid Tag tag, BindingResult result, RedirectAttributes attributes) {
        Tag tag1 = service.getTagByName(tag.getName());
        if (tag1 != null) {
            result.rejectValue("name", "nameError", "该分类名称不能重复添加");
        }

        if (result.hasErrors())
            return "admin/tags-input";

        Tag tag2 = service.saveTag(tag);
        if (tag2 == null) {
            attributes.addFlashAttribute("message", "操作失败");
        } else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        return "redirect:/admin/tags";
    }

    @Transactional
    @PostMapping("/tags/{id}")
    public String editPost(@Valid Tag tag, BindingResult result, @PathVariable Long id, RedirectAttributes attributes) {
        Tag tag1 = service.getTagByName(tag.getName());
        if (tag1 != null) {
            result.rejectValue("name", "nameError", "该分类名称不能重复添加");
        }

        if (result.hasErrors())
            return "admin/tags-input";

        Tag tag2 = service.updateTag(id, tag);
        if (tag2 == null) {
            attributes.addFlashAttribute("message", "操作失败");
        } else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        return "redirect:/admin/tags";
    }

    @Transactional
    @GetMapping("tags/{id}/delete")
    public String delete(@PathVariable Long id) {
        service.deleteTag(id);
        return "redirect:/admin/tags";
    }
}
