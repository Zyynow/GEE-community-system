package com.jxufe.web;

import com.jxufe.entity.Type;
import com.jxufe.service.ResourceService;
import com.jxufe.service.TypeService;

import com.jxufe.vo.ResourceQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class TypeShowController {

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private TypeService typeService;

    @GetMapping("/types/{id}")
    public String doTypes(@PageableDefault(size = 4, sort = {"updateTime"}, direction = Sort.Direction.DESC)
                                Pageable pageable, @PathVariable Long id, Model model, HttpServletRequest request) {
        request.getSession().setAttribute("lastPath", request.getServletPath());

        List<Type> types = typeService.listTypeTop(10000); //拿到分类的列表
        if (id == -1) { //表示开始没有选定是哪一个分类
            id = types.get(0).getId(); //给他一个默认的分类值
        }
        ResourceQuery resourceQuery = new ResourceQuery();
        resourceQuery.setTypeId(id); //根据id来分页查询
        //将数据返回给视图 -> 前台
        model.addAttribute("types", types);
        model.addAttribute("page", resourceService.listResource(pageable, resourceQuery));
        model.addAttribute("activeTypeId", id);
        return "types";
    }

    @GetMapping("/user_types/{id}")
    public String doUserTypes(@PageableDefault(size = 4, sort = {"updateTime"}, direction = Sort.Direction.DESC)
                                  Pageable pageable, @PathVariable Long id, Model model, HttpServletRequest request) {
        request.getSession().setAttribute("lastPath", request.getServletPath());

        List<Type> types = typeService.listTypeTop(10000); //拿到分类的列表
        if (id == -1) { //表示开始没有选定是哪一个分类
            id = types.get(0).getId(); //给他一个默认的分类值
        }
        ResourceQuery resourceQuery = new ResourceQuery();
        resourceQuery.setTypeId(id); //根据id来分页查询
        //将数据返回给视图 -> 前台
        model.addAttribute("types", types);
        model.addAttribute("page", resourceService.listResource(pageable, resourceQuery));
        model.addAttribute("activeTypeId", id);
        return "user_types";
    }
}
