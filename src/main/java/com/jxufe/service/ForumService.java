package com.jxufe.service;

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
}
