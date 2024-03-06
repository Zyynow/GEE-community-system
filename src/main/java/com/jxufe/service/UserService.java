package com.jxufe.service;

import com.jxufe.entity.Feedback;
import com.jxufe.entity.User;

public interface UserService {

    User checkUserLogin(String username, String password);

    User checkUserExist(String username);

    Integer addNewUser(User user);

    Integer editUserInfo(User user);

    Integer removeUser(String username);

    Integer sendFeedback(Feedback feedback);

    Integer editPassword(String username, String newPwd, String oldPwd);

    Integer checkPassword(String username, String oldPwd);

    User findUserById(Long id);

    Integer updateBlogNum(Long id);
}
