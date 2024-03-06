package com.jxufe.service.implement;

import com.jxufe.dao.UserDao;
import com.jxufe.entity.Feedback;
import com.jxufe.entity.User;
import com.jxufe.service.UserService;
import com.jxufe.util.MD5Utils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    @Resource //Autowired也可以的
    private UserDao dao;

    @Transactional
    @Override
    public User checkUserLogin(String username, String password) {
        User user = dao.findByUsernameAndPassword(username, MD5Utils.code(password));
        return user;
    }

    @Override
    public User checkUserExist(String username) {
        User user = dao.findByUsername(username);
        return user;
    }

    @Override
    public Integer addNewUser(User user) {
        Integer res = 0;
        if (user != null) {
            user.setCreateTime(new Date());
            user.setPassword(MD5Utils.code(user.getPassword()));
            user.setNickname("默认");
            user.setSex("默认");
            user.setAvatar("https://img0.baidu.com/it/u=1855725179,2561304587&fm=253&fmt=auto&app=138&f=JPEG?w=499&h=500");
            user.setBlogNum(0);
            res = dao.saveUser(user);
        }
        return res;
    }

    @Override
    public Integer editUserInfo(User user) {
        user.setUpdateTime(new Date());
        Integer res = dao.updateUserInfo(user);
        return res;
    }

    @Override
    public Integer removeUser(String username) {
        Integer res = dao.deleteUser(username);
        return res;
    }

    @Override
    public Integer sendFeedback(Feedback feedback) {
        feedback.setCreateTime(new Date());
        return dao.addFeedback(feedback);
    }

    @Override
    public Integer editPassword(String username, String newPwd, String oldPwd) {
        Integer res = dao.checkAndUpdatePassword(username, MD5Utils.code(newPwd), MD5Utils.code(oldPwd));
        return res;
    }

    @Override
    public Integer checkPassword(String username, String oldPwd) {
        User res = dao.checkPassword(username, MD5Utils.code(oldPwd));
        if (res == null) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public User findUserById(Long id) {
        return dao.findUserById(id);
    }

    @Override
    public Integer updateBlogNum(Long id) {
        return dao.updateBlogNum(id);
    }
}
