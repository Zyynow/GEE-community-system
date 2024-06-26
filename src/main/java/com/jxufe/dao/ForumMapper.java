package com.jxufe.dao;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.jxufe.entity.Forum;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ForumMapper {

    Integer joinForum(Integer userId, Integer forumId);

    Integer exitForum(Integer userId, Integer forumId);

    List<Forum> findListForums();

    Forum findForumById(Integer id);

    List<Forum> findNotJoinForumsByUserId(Long id);

    List<Forum> findJoinForumsByUserId(Long id);

    List<Forum> listForumsTopById(Long id);

    Integer updateHotById(Long id);

    void deleteJoinByUser(Long userId);

    void updateBlogNum(Integer id);

    void reduceBlogNum(Integer id);

    void updatePeopleNum(Integer forumId);

    Page<Forum> pageForums();

    void deleteForum(Long id);

    int saveForum(Forum forum);

    int updateForum(Forum forum);

    Page<Forum> pageSearch(String query);
}
