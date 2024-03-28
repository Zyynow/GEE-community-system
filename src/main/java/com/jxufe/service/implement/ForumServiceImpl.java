package com.jxufe.service.implement;

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
    public List<Forum> findJoinForums(Long id) {
        return forumMapper.findJoinForumsByUserId(id);
    }
}
