package com.jxufe.service.implement;

import com.jxufe.dao.FriendMapper;
import com.jxufe.dao.UserMapper;
import com.jxufe.entity.Apply;
import com.jxufe.entity.Friend;
import com.jxufe.entity.User;
import com.jxufe.service.FriendService;
import com.jxufe.vo.FriendVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FriendServiceImpl implements FriendService {

    @Resource
    FriendMapper friendMapper;

    @Resource
    UserMapper userMapper;

    @Override
    public List<FriendVO> friendListByUser(Long id) {
        List<FriendVO> friendVOList = friendMapper.friendListByUser(id);
        return friendVOList;
    }

    @Override
    public FriendVO friendHottest(String username) {
        FriendVO friendVO = friendMapper.friendHottest(username);
        return friendVO;
    }

    @Override
    public List<FriendVO> friendsOften(String username) {
        List<FriendVO> friendVOList = friendMapper.friendsOften(username);
        return friendVOList;
    }



    @Override
    public int deleteFriend(String meName, String heName) {
        int flag = friendMapper.deleteFriendByName(meName, heName);
        return 0;
    }

    @Override
    public int deleteFriendById(Long user1Id, Long user2Id) {
        int flag = friendMapper.deleteFriendById(user1Id, user2Id);
        return 0;
    }

    @Override
    public int saveFriend(Friend friend) {
        int flag = friendMapper.saveFriend(friend);
        return 0;
    }

    @Override
    public int addApply(Apply apply) {
        friendMapper.addApply(apply);
        return 0;
    }

    @Override
    public List<User> searchUser(String keyword) {
        List<User> users = userMapper.searchUser(keyword);
        return users;
    }
}
