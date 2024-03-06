package com.jxufe.service.implement;

import com.jxufe.dao.ForumDao;
import com.jxufe.entity.Forum;
import com.jxufe.service.ForumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ForumServiceImpl implements ForumService {

    @Autowired
    ForumDao forumDao;

    @Override
    public Integer joinForum(Integer userId, Integer forumId) {
        Integer res = forumDao.joinForum(userId, forumId);
        return res;
    }

    @Override
    public Integer exitForum(Integer userId, Integer forumId) {
        Integer res = forumDao.exitForum(userId, forumId);
        return res;
    }

    @Override
    public List<Forum> listForums() {
        List<Forum> listForums = forumDao.findListForums();
        return listForums;
    }

    @Override
    public Forum getForumById(Integer id) {
        return forumDao.findForumById(id);
    }

    @Override
    public List<Forum> findNotJoinForums(Long id) {
        return forumDao.findNotJoinForumsByUserId(id);
    }

    @Override
    public List<Forum> listForumsById(Long id) {
        return forumDao.listForumsTopById(id);
    }

    @Override
    public List<Forum> findJoinForums(Long id) {
        return forumDao.findJoinForumsByUserId(id);
    }
}
