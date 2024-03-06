package com.jxufe.web.admin;


import com.jxufe.service.ResourceService;
import com.jxufe.service.TagService;
import com.jxufe.service.TypeService;
import com.jxufe.vo.ResourceQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/admin")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @Resource
    private TypeService typeService;

    @Resource
    private TagService tagService;

    /**
     * 执行分页查询，以时间倒叙排序
     * @param model 模型
     * @param resource 指南
     * @param pageable 页面
     * @return
     */
    @GetMapping("/resources")
    public String doResources(@PageableDefault(size = 3, sort = {"updateTime"}, direction = Sort.Direction.DESC)
                                      Pageable pageable, Model model, ResourceQuery resource) {
        model.addAttribute("types", typeService.listType());
        model.addAttribute("pages", resourceService.listResource(pageable, resource));
        return "/admin/resources";
    }

    @GetMapping("/resources/search")
    public String doSearch(@PageableDefault(size = 3, sort = {"updateTime"}, direction = Sort.Direction.DESC)
                                       Pageable pageable, Model model, ResourceQuery resource) {
        model.addAttribute("pages", resourceService.listResource(pageable, resource));
        return "/admin/resources";
    }

    @Transactional
    @GetMapping(value = "/resources/input")
    public String doInput(Model model) {
        //添加模型数据
        setTypeAndTag(model);
        model.addAttribute("resource", new com.jxufe.entity.Resource());
        return "/admin/resource-input";
    }

    private void setTypeAndTag(Model model) {
        model.addAttribute("types", typeService.listType());
    }

    @PostMapping("/resources/post")
    public String doPost(com.jxufe.entity.Resource resource, RedirectAttributes attributes, HttpSession session) {
        com.jxufe.entity.Resource resource1;
        if (resource.getId() == null) {
            resource1 = resourceService.saveResource(resource);
        } else {
            resource1 = resourceService.updateResource(resource.getId(), resource); //如果id不为空，则更新id
        }
        if (resource1 == null) {
            attributes.addFlashAttribute("message", "操作失败");
        } else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        return "redirect:/admin/resources";
    }

    @Transactional
    @GetMapping("/resources/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        setTypeAndTag(model);
        com.jxufe.entity.Resource resource = resourceService.getResource(id);
        model.addAttribute("resource", resource);
        return "/admin/resource-input";
    }

    @Transactional
    @GetMapping("/resources/{id}/delete")
    public String delete(@PathVariable Long id) {
        resourceService.deleteResource(id);
        return "redirect:/admin/resources";
    }
}
