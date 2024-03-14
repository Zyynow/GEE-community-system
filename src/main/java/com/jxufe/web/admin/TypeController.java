package com.jxufe.web.admin;

import com.jxufe.entity.Type;
import com.jxufe.service.TypeService;
import net.bytebuddy.TypeCache;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/admin")
public class TypeController {

    @Autowired
    private TypeService service;

    @GetMapping("/types")
    public String doTypes(@PageableDefault(size = 6, sort = {"id"}, direction = Sort.Direction.DESC)
                          Pageable pageable, Model model) {
        model.addAttribute("page", service.listType(pageable));
        return "admin/types";
    }

    @Transactional
    @GetMapping("/types/input")
    public String doInput(Model model) {
        model.addAttribute("type", new Type());
        return "admin/types-input";
    }

    @Transactional
    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("type", service.getType(id));
        return "admin/types-input";
    }

    /*
        @Valid: 校验信息 结果在result中
        注：被@Valid注解的Type和BindingResult必须连着，校验才有效
     */
    @PostMapping("/types")
    public String doPost(@Valid Type type, BindingResult result, RedirectAttributes attributes) {
        Type type2 = service.getTypeByName(type.getName());
        if (type2 != null) {
            result.rejectValue("name", "nameError", "该分类名称不能重复添加");
        }

        if (result.hasErrors())
            return "admin/types-input";

        Type type1 = service.saveType(type);
        if (type1 == null) {
            attributes.addFlashAttribute("message", "操作失败");
        } else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        return "redirect:/admin/types";
    }

    @Transactional
    @PostMapping("/types/{id}")
    public String editPost(@Valid Type type, BindingResult result, @PathVariable Long id, RedirectAttributes attributes) {
        Type type2 = service.getTypeByName(type.getName());
        if (type2 != null) {
            result.rejectValue("name", "nameError", "该分类名称不能重复添加");
        }

        if (result.hasErrors())
            return "admin/types-input";

        Type type1 = service.updateType(id, type);
        if (type1 == null) {
            attributes.addFlashAttribute("message", "操作失败");
        } else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        return "redirect:/admin/types";
    }

    @Transactional
    @GetMapping("types/{id}/delete")
    public String delete(@PathVariable Long id) {
        service.deleteType(id);
        return "redirect:/admin/types";
    }
}
