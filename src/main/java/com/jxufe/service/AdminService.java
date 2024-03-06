package com.jxufe.service;

import com.jxufe.entity.Admin;
import com.jxufe.entity.User;

public interface AdminService {

    Admin checkUserLogin(String username, String password);

    Admin checkUserExist(String username);

    Integer addNewUser(Admin user);

    Integer removeUser(String username);

    Integer editPassword(String username, String newPwd, String oldPwd);

    Integer checkPassword(String username, String oldPwd);
}
