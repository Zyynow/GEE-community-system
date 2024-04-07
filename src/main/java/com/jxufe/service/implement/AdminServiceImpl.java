package com.jxufe.service.implement;

import com.jxufe.dao.AdminMapper;
import com.jxufe.entity.Admin;
import com.jxufe.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin checkUserLogin(String username, String password) {
        return adminMapper.findByUsernameAndPassword(username, password);
    }

    @Override
    public Admin checkUserExist(String username) {
        return adminMapper.findByUsername(username);
    }

    @Override
    public Integer addNewUser(Admin user) {
        Integer res = 0;
        if (user != null) {
            user.setDescription("我是管理员哦");
            user.setAvatar("https://sky-take-out-kafka.oss-cn-beijing.aliyuncs.com/defaultAvatar.png");
            res = adminMapper.saveUser(user);
        }
        return res;
    }

    @Override
    public Integer removeUser(String username) {
        return null;
    }

    @Override
    public Integer editPassword(String username, String newPwd, String oldPwd) {
        return null;
    }

    @Override
    public Integer checkPassword(String username, String oldPwd) {
        return null;
    }
}
