package com.jxufe.service;

import com.jxufe.entity.Apply;
import com.jxufe.entity.Friend;
import com.jxufe.entity.User;
import com.jxufe.vo.FootprintVO;
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

    public boolean isFriend(Long user1Id, Long user2Id);

    public FootprintVO getViewNumMax(Long id);

    FootprintVO getCollectionNumMax(Long id);

    FootprintVO getCommentNumMax(Long id);

    boolean isFriendByName(String toName, String username);

    void deleteFriendByUser(Long id);

    void deleteApplyByUser(Long id);

    void updateFriends(Long userId, String nickname, String description, String avatar);

    void updateApplys(Long userId, String nickname, String avatar);
}
