package com.jxufe.service;

import com.jxufe.entity.Apply;
import com.jxufe.entity.Friend;
import com.jxufe.entity.User;
import com.jxufe.vo.FriendVO;
import com.jxufe.vo.SearchVO;

import java.util.List;

public interface FriendService {

    public List<FriendVO> friendListByUser(Long id);

    public FriendVO friendHottest(String username);

    public List<FriendVO> friendsOften(String username);

    public int deleteFriend(String meName, String heName);

    public int deleteFriendById(Long user1Id, Long user2Id);

    public int saveFriend(Friend friend);

    public int addApply(Apply apply);

    public int deleteApply(Long id);

    public List<SearchVO> searchUser(String keyword, Long id);

    public int updateFriend(Friend friend);

    public List<Apply> applyList(Long id);

    public int friendsCount(Long id);
}
