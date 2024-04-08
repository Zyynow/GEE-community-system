package com.jxufe.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.jxufe.entity.Forum;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ForumService {

    public Integer joinForum(Integer userId, Integer forumId);

    public Integer exitForum(Integer userId, Integer forumId);

    public List<Forum> listForums();

    public Forum getForumById(Integer id);

    public List<Forum> findJoinForums(Long id);

    public List<Forum> findNotJoinForums(Long id);

    public List<Forum> listForumsById(Long id);

    public Integer updateHotById(Long id);

    void deleteJoinByUser(Long id);

    void updateBlogNum(Integer id);

    void reduceBlogNum(Integer id);

    void updatePeopleNum(Integer forumId);

    Page<Forum> pageForums();

    void deleteForum(Long id);

    int saveForum(Forum forum);

    int updateForum(Forum forum);

    Page<Forum> pageSearch(String query);
}
