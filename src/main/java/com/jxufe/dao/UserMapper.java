package com.jxufe.dao;

import com.jxufe.entity.Feedback;
import com.jxufe.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 告诉Mybatis这是我们的dao接口，创建此接口的代理对象(然后放入容器中)
 * 有了@Mapper就能找到Mapper.xml文件
 * MyBatis数据库查询是返回一个对象还是List或者数组，会根据返回类型来判断，类型是数组则返回数组、List则返回List
 */
@Mapper
public interface UserMapper {

    User findByUsernameAndPassword(String username, String password);

    User findByUsername(String username);

    Integer saveUser(User user);

    Integer updateUserInfo(User user);

    Integer deleteUser(String username);

    Integer addFeedback(Feedback feedback);

    Integer checkAndUpdatePassword(String username, String newPassword, String oldPassword);

    User findUserById(Long id);

    User checkPassword(String username, String oldPwd);

    Integer updateBlogNum(Long id);

    List<User> searchUser(@Param("name") String keyword);
}
