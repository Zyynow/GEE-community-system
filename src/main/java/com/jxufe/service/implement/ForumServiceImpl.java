package com.jxufe.service.implement;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.jxufe.dao.ForumMapper;
import com.jxufe.entity.Forum;
import com.jxufe.service.ForumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ForumServiceImpl implements ForumService {

    @Autowired
    ForumMapper forumMapper;

    @Override
    public Integer joinForum(Integer userId, Integer forumId) {
        Integer res = forumMapper.joinForum(userId, forumId);
        return res;
    }

    @Override
    public Integer exitForum(Integer userId, Integer forumId) {
        Integer res = forumMapper.exitForum(userId, forumId);
        return res;
    }

    @Override
    public List<Forum> listForums() {
        List<Forum> listForums = forumMapper.findListForums();
        return listForums;
    }

    @Override
    public Forum getForumById(Integer id) {
        return forumMapper.findForumById(id);
    }

    @Override
    public List<Forum> findNotJoinForums(Long id) {
        return forumMapper.findNotJoinForumsByUserId(id);
    }

    @Override
    public List<Forum> listForumsById(Long id) {
        return forumMapper.listForumsTopById(id);
    }

    @Override
    public Integer updateHotById(Long id) {
        return forumMapper.updateHotById(id);
    }

    @Override
    public void deleteJoinByUser(Long id) {
        forumMapper.deleteJoinByUser(id);
    }

    @Override
    public void updateBlogNum(Integer id) {
        forumMapper.updateBlogNum(id);
    }

    @Override
    public void reduceBlogNum(Integer id) {
        forumMapper.reduceBlogNum(id);
    }

    @Override
    public void updatePeopleNum(Integer forumId) {
        forumMapper.updatePeopleNum(forumId);
    }

    @Override
    public Page<Forum> pageForums() {
        return forumMapper.pageForums();
    }

    @Override
    public void deleteForum(Long id) {
        forumMapper.deleteForum(id);
    }

    @Override
    public int saveForum(Forum forum) {
        forum.setBlogNumber(2600);
        forum.setPeopleNumber(1200);
        forum.setPopular(4800);
        return forumMapper.saveForum(forum);
    }

    @Override
    public int updateForum(Forum forum) {
        return forumMapper.updateForum(forum);
    }

    @Override
    public Page<Forum> pageSearch(String query) {
        return forumMapper.pageSearch(query);
    }

    @Override
    public List<Forum> findJoinForums(Long id) {
        return forumMapper.findJoinForumsByUserId(id);
    }



}
