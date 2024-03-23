package com.jxufe.dao;

import com.jxufe.entity.Admin;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminMapper {

    Admin findByUsernameAndPassword(String username, String password);

    Admin findByUsername(String username);

    Integer saveUser(Admin user);

    Integer deleteUser(String username);

    Integer checkAndUpdatePassword(String username, String newPassword, String oldPassword);

    Admin findUserById(Long id);

    Admin checkPassword(String username, String oldPwd);
}
