package com.jxufe.dao;

import com.jxufe.entity.Forum;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ForumDao {

    Integer joinForum(Integer userId, Integer forumId);

    Integer exitForum(Integer userId, Integer forumId);

    List<Forum> findListForums();

    Forum findForumById(Integer id);

    List<Forum> findNotJoinForumsByUserId(Long id);

    List<Forum> findJoinForumsByUserId(Long id);

    List<Forum> listForumsTopById(Long id);
}
