package com.jxufe.dao;

import com.jxufe.entity.Apply;
import com.jxufe.entity.Friend;
import com.jxufe.vo.FootprintVO;
import com.jxufe.vo.FriendVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FriendMapper {

    int saveFriend(Friend friend);

    int addApply(Apply apply);

    List<Apply> applyList(Long id);

    int deleteFriendByName(String meName, String heName);

    List<FriendVO> friendsOften(String username);

    FriendVO friendHottest(String username);

    List<FriendVO> friendListByUser(Long id);

    int deleteFriendById(Long user1Id, Long user2Id);

    int deleteApplyById(Long id);

    int updateFriend(Friend friend);

    Integer getCount(Long id);

    Friend isFriendByName(String user1Name, String user2Name);

    Friend isFriend(Long user1Id, Long user2Id);

    FootprintVO getUserViewMax(Long id);

    FootprintVO getCollectionMax(Long id);

    FootprintVO getCommentMax(Long id);

    void deleteFriendByUser(Long userId);

    void deleteApplyByUser(Long userId);

    void updateFriendsByUser1(Long userId, String nickname, String description, String avatar);

    void updateFriendsByUser2(Long userId, String nickname, String description, String avatar);

    void updateApplys(Long userId, String nickname, String avatar);
}
