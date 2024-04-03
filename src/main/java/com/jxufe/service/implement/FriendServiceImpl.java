package com.jxufe.service.implement;

import com.jxufe.dao.FriendMapper;
import com.jxufe.dao.UserMapper;
import com.jxufe.entity.Apply;
import com.jxufe.entity.Friend;
import com.jxufe.entity.User;
import com.jxufe.service.FriendService;
import com.jxufe.vo.FootprintVO;
import com.jxufe.vo.FriendVO;
import com.jxufe.vo.SearchVO;
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
        return flag;
    }

    @Override
    public int deleteFriendById(Long user1Id, Long user2Id) {
        int flag = friendMapper.deleteFriendById(user1Id, user2Id);
        return flag;
    }

    @Override
    public int saveFriend(Friend friend) {
        User user = userMapper.findUserById(friend.getUser2Id());
        if (user != null) {
            friend.setUser2Avatar(user.getAvatar());
            friend.setUser2Description(user.getDescription());
            friend.setUser2Nickname(user.getNickname());
            friend.setUser2Username(user.getUsername());
        }
        System.out.println(friend);
        int flag = friendMapper.saveFriend(friend);
        return flag;
    }

    @Override
    public int addApply(Apply apply) {

        int flag = friendMapper.addApply(apply);
        return flag;
    }

    @Override
    public int deleteApply(Long id) {
        int flag = friendMapper.deleteApplyById(id);
        return flag;
    }

    @Override
    public List<SearchVO> searchUser(String keyword, Long id) {
        List<SearchVO> users = userMapper.searchUser(keyword, id);
        return users;
    }

    @Override
    public int updateFriend(Friend friend) {
        int flag = friendMapper.updateFriend(friend);
        return flag;
    }

    @Override
    public List<Apply> applyList(Long id) {
        return friendMapper.applyList(id);
    }

    @Override
    public int friendsCount(Long id) {
        return friendMapper.getCount(id);
    }

    @Override
    public boolean isFriend(Long user1Id, Long user2Id) {
        return friendMapper.isFriend(user1Id, user2Id) != null;
    }

    @Override
    public FootprintVO getViewNumMax(Long id) {
        return friendMapper.getUserViewMax(id);
    }

    @Override
    public FootprintVO getCollectionNumMax(Long id) {
        return friendMapper.getCollectionMax(id);
    }

    @Override
    public FootprintVO getCommentNumMax(Long id) {
        return friendMapper.getCommentMax(id);
    }

    @Override
    public boolean isFriendByName(String toName, String username) {
        return friendMapper.isFriendByName(toName, username) != null;
    }
}
