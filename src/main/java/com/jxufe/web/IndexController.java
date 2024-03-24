package com.jxufe.web;

import com.jxufe.entity.Blog;
import com.jxufe.entity.Comment;
import com.jxufe.entity.Feedback;
import com.jxufe.entity.Resource;
import com.jxufe.service.*;
import com.jxufe.service.implement.BlogServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@org.springframework.stereotype.Controller
public class IndexController {

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;

    @GetMapping({"/", "/index"})
    public String doIndex(@PageableDefault(size = 6, sort = {"updateTime"}, direction = Sort.Direction.DESC)
                                      Pageable pageable, Model model, HttpServletRequest request) {
        request.getSession().setAttribute("lastPath", request.getServletPath());

        model.addAttribute("page", resourceService.listResource(pageable)); // 拿到分页查询的数据
        model.addAttribute("types", typeService.listTypeTop(6));
        model.addAttribute("recommendResources", resourceService.listRecommendResourceTop(2));
        if (request.getSession().getAttribute("user") != null) {
            return "user_index";
        }
        return "index";
    }

    @GetMapping("/user_index")
    public String doUserIndex(@PageableDefault(size = 6, sort = {"updateTime"}, direction = Sort.Direction.DESC)
                                      Pageable pageable, Model model) {
        model.addAttribute("page", resourceService.listResource(pageable)); // 拿到分页查询的数据
        model.addAttribute("types", typeService.listTypeTop(6));
        model.addAttribute("recommendResources", resourceService.listRecommendResourceTop(2));
        return "user_index";
    }

    @GetMapping("/blog/{id}")
    public String doBlogPage(@PathVariable Long id, Model model, HttpServletRequest request){
        request.getSession().setAttribute("lastPath", request.getServletPath());
        blogService.updateViews(id);
        model.addAttribute("blog", blogService.getAndConvert(id));
        if (request.getSession().getAttribute("user") != null) {
            return "user_blog";
        }
        return "blog";
    }

    @GetMapping("/user_blog/{id}")
    public String doUserBlogPage(@PathVariable Long id, Model model, HttpServletRequest request){
        blogService.updateViews(id);
        model.addAttribute("blog", blogService.getAndConvert(id));
        return "user_blog";
    }

    /**
     *
     * @param pageable 设置分页查询的结果集的规定
     * @param query 拿到查询提交表单的query数据
     * @param model 数据模型，设置前端传输的数据对象
     * @return 返回search.html页面
     */
    @GetMapping("/search")
    public String doSearch(@PageableDefault(size = 6, sort = {"updateTime"}, direction = Sort.Direction.DESC)
                                   Pageable pageable, @RequestParam String query, Model model, HttpServletRequest request) {
        request.getSession().setAttribute("lastPath", request.getServletPath());

        model.addAttribute("page", resourceService.listResource(pageable, "%" + query + "%"));
        model.addAttribute("query", query);
        if (request.getSession().getAttribute("user") != null) {
            return "user_search";
        }
        return "search";
    }

    @GetMapping("/user_search")
    public String doUserSearch(@PageableDefault(size = 6, sort = {"updateTime"}, direction = Sort.Direction.DESC)
                                   Pageable pageable, @RequestParam String query, Model model, HttpServletRequest request) {

        model.addAttribute("page", resourceService.listResource(pageable, "%" + query + "%"));
        model.addAttribute("query", query);
        return "user_search";
    }

    @GetMapping("/resource/{id}")
    public String doResource(@PathVariable Long id, Model model, HttpServletRequest request) {
        request.getSession().setAttribute("lastPath", request.getServletPath());

        resourceService.updateViews(id);
        Resource resource = resourceService.getAndConvert(id);
        model.addAttribute("resource", resource);
        if (request.getSession().getAttribute("user") != null) {
            return "user_resource";
        }
        return "resource";
    }

    @GetMapping("/user_resource/{id}")
    public String doUserResource(@PathVariable Long id, Model model) {
        resourceService.updateViews(id);
        Resource resource = resourceService.getAndConvert(id);
        model.addAttribute("resource", resource);
        return "user_resource";
    }
}
