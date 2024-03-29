package com.jxufe.service;

import com.jxufe.entity.Apply;
import com.jxufe.entity.Friend;
import com.jxufe.entity.User;
import com.jxufe.vo.FriendVO;

import java.util.List;

public interface FriendService {

    public List<FriendVO> friendListByUser(Long id);

    public FriendVO friendHottest(String username);

    public List<FriendVO> friendsOften(String username);

    public int deleteFriend(String meName, String heName);

    public int deleteFriendById(Long user1Id, Long user2Id);

    public int saveFriend(Friend friend);

    public int addApply(Apply apply);

    public List<User> searchUser(String keyword);
}
