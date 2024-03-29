package com.jxufe.web;

import com.jxufe.entity.User;
import com.jxufe.service.FriendService;
import com.jxufe.vo.FriendVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("friend")
public class FriendController {

    @Autowired
    FriendService friendService;

    @PostMapping("/add")
    public void addFriend() {

    }

    @PostMapping("/apply/add")
    public void addApply() {

    }

    @GetMapping({"/", "/list"})
    @ResponseBody
    public List<FriendVO> friendListByUser(HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<FriendVO> friendVOList = friendService.friendListByUser(user.getId());
        return friendVOList;
    }

    @GetMapping("/hottest")
    @ResponseBody
    public FriendVO friendHottest(HttpSession session) {
        User user = (User) session.getAttribute("user");
        FriendVO friendVO = friendService.friendHottest(user.getUsername());
        return friendVO;
    }

    @GetMapping("/often")
    @ResponseBody
    public List<FriendVO> friendsOften(HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<FriendVO> friendVOList = friendService.friendsOften(user.getUsername());
        return friendVOList;
    }

    @GetMapping("/delete/{id}")
    public void deleteFriend(@PathVariable Long id) {

    }

    @GetMapping("/search/{keyword}")
    public void searchFriends(@PathVariable String keyword) {

    }
}
