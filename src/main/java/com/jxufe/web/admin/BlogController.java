package com.jxufe.web.admin;

import com.jxufe.entity.Blog;
import com.jxufe.entity.User;
import com.jxufe.service.BlogService;
import com.jxufe.service.TagService;
import com.jxufe.service.TypeService;
import com.jxufe.vo.BlogQuery;
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

/**
 * 不再使用
 */
@Controller
@RequestMapping(value = "/admin")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Resource
    private TypeService typeService;

    @Resource
    private TagService tagService;

    /**
     * 执行分页查询，以时间倒叙排序
     * @param model 模型
     * @param blog 博客
     * @param pageable 页面
     * @return
     */
    @GetMapping("/blogs")
    public String doBlogs(@PageableDefault(size = 3, sort = {"updateTime"}, direction = Sort.Direction.DESC)
                                      Pageable pageable, Model model, BlogQuery blog) {
        model.addAttribute("types", typeService.listType());
        model.addAttribute("pages", blogService.listBlog(pageable, blog));
        return "resources";
    }

    @GetMapping("/blogs/search")
    public String doSearch(@PageableDefault(size = 3, sort = {"updateTime"}, direction = Sort.Direction.DESC)
                                       Pageable pageable, Model model, BlogQuery blog) {
        model.addAttribute("pages", blogService.listBlog(pageable, blog));
        return "resources";
    }

    @Transactional
    @GetMapping(value = "/blogs/input")
    public String doInput(Model model) {
        //添加模型数据
        setTypeAndTag(model);
        model.addAttribute("blog", new Blog());
        return "resource-input";
    }

    private void setTypeAndTag(Model model) {
        model.addAttribute("types", typeService.listType());
//        model.addAttribute("tags", tagService.listTag());
    }

    @PostMapping("/blogs/post")
    public String doPost(Blog blog, RedirectAttributes attributes, HttpSession session) {
        blog.setUser((User) session.getAttribute("user"));
//        blog.setTags(tagService.listTag(blog.getTagIds()));
        Blog blog1;
        if (blog.getId() == null) {
            blog1 = blogService.saveBlog(blog);
        } else {
            blog1 = blogService.updateBlog(blog.getId(), blog); //如果id不为空，则更新id
        }
        if (blog1 == null) {
            attributes.addFlashAttribute("message", "操作失败");
        } else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        return "redirect:/admin/blogs";
    }

    @Transactional
    @GetMapping("/blogs/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        setTypeAndTag(model);
        Blog blog = blogService.getBlog(id);
//        blog.init();
        model.addAttribute("blog", blog);
        return "resource-input";
    }

    @Transactional
    @GetMapping("blogs/{id}/delete")
    public String delete(@PathVariable Long id) {
        blogService.deleteBlog(id);
        return "redirect:/admin/blogs";
    }
}
