package com.jxufe.web;

import com.jxufe.entity.Blog;
import com.jxufe.entity.Favourites;
import com.jxufe.entity.Resource;
import com.jxufe.entity.User;
import com.jxufe.service.BlogService;
import com.jxufe.service.ResourceService;
import com.jxufe.vo.CollectionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/collection")
public class CollectionController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private ResourceService resourceService;

    @PostMapping("/user_blog")
    public void collectionBlog(@RequestBody Favourites favourites, HttpSession session, HttpServletResponse response) throws IOException {
        User user = (User) session.getAttribute("user");
        // 是帖子，并且收藏者是自己
        if (!favourites.isType() && user.getId() == favourites.getUserId()) {
            response.getWriter().print("<script language='javascript'>alert('自己不能收藏自己的帖子');" +
                    "window.location.href='/dev/user_blog/" + favourites.getBlogId() + "';</script>");
        }
        int res = blogService.collectionBlog(favourites);
        if (res == 0) {
            response.getWriter().print("<script language='javascript'>alert('收藏失败，看看是什么原因吧');" +
                    "window.location.href='/dev/user_blog/" + favourites.getBlogId() + "';</script>");
        }
    }

    @PostMapping("/user_resource")
    public void collectionResource(@RequestBody Favourites favourites, HttpServletResponse response) throws IOException {
        int res = resourceService.collectionResource(favourites);
        if (res == 0) {
            response.getWriter().print("<script language='javascript'>alert('收藏失败，看看是什么原因吧');" +
                    "window.location.href='/dev/user_resource/" + favourites.getResourceId() + "';</script>");
        }
    }

    @GetMapping("/remove/resource/{resourceId}")
    @Transactional
    public void removeCollectionResource(@PathVariable Long resourceId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        int res = resourceService.removeCollectionResource(user.getId(), resourceId);
    }

    @GetMapping("/remove/blog/{blogId}")
    @Transactional
    public void removeCollectionBlog(@PathVariable Long blogId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        int res = blogService.removeCollectionBlog(user.getId(), blogId);
    }

    @GetMapping("/delete/resource/{id}")
    @Transactional
    public String removeResourceById(@PathVariable Long id) {
        int res = resourceService.removeResourceById(id);
        return "collection_resource";
    }

    @GetMapping("/delete/blog/{id}")
    @Transactional
    public String removeBlogById(@PathVariable Long id) {
        int res = blogService.removeBlogById(id);
        return "collection_blog";
    }

    @GetMapping("/blog/list")
    public String getCollectionBlogList(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        List<CollectionVO> favourites = blogService.collectionBlogList(user.getId());
        List<List<CollectionVO>> groupedCollections = sortByTime(favourites);
        List<Blog> hotBlogs = blogService.getHotBlog();
        List<Blog> collectionMaxB = blogService.getCollectionMax();

        // 现在你可以将groupedCollections传递给模型
        model.addAttribute("groupedCollectionList", groupedCollections);
        model.addAttribute("totalCollectionsCount", favourites.size());
        model.addAttribute("hotBlogs", hotBlogs);
        model.addAttribute("collectionMaxB", collectionMaxB);

        return "collection_blog";
    }

    @GetMapping("/resource/list")
    public String getCollectionResourceList(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        List<CollectionVO> favourites = resourceService.collectionResourceList(user.getId());
        List<List<CollectionVO>> groupedCollections = sortByTime(favourites);
        List<Resource> hotResources = resourceService.getHotResource();
        List<Resource> collectionMaxR = resourceService.getCollectionMax();

        // 现在你可以将groupedCollections传递给模型
        model.addAttribute("groupedCollectionList", groupedCollections);
        model.addAttribute("totalCollectionsCount", favourites.size());
        model.addAttribute("hotResources", hotResources);
        model.addAttribute("collectionMaxR", collectionMaxR);

        return "collection_resource";
    }

    public List<List<CollectionVO>> sortByTime(List<CollectionVO> list) {
        // 将收藏按月份分类并存储月份和对应收藏列表的映射
        Map<YearMonth, List<CollectionVO>> collectionsByMonth = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");

        for (CollectionVO collection : list) {
            LocalDate date = collection.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            YearMonth monthKey = YearMonth.from(date);

            if (!collectionsByMonth.containsKey(monthKey)) {
                collectionsByMonth.put(monthKey, new ArrayList<>());
            }
            collectionsByMonth.get(monthKey).add(collection);
        }

        // 将Map按照月份逆序排序后转为List<List<CollectionVO>>
        List<List<CollectionVO>> groupedCollections = new ArrayList<>(collectionsByMonth.entrySet().stream()
                .sorted(Map.Entry.<YearMonth, List<CollectionVO>>comparingByKey(Comparator.reverseOrder()))
                .map(entry -> entry.getValue())
                .collect(Collectors.toList()));

        return groupedCollections;
    }

}
