package com.jxufe.dao;

import com.jxufe.entity.Apply;
import com.jxufe.entity.Friend;
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
}
