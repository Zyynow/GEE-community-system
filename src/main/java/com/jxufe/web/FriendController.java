package com.jxufe.web;

import com.jxufe.entity.Apply;
import com.jxufe.entity.Friend;
import com.jxufe.entity.User;
import com.jxufe.service.FriendService;
import com.jxufe.vo.FriendVO;
import com.jxufe.vo.SearchVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("friend")
public class FriendController {

    @Autowired
    FriendService friendService;

    @GetMapping("/")
    public String doFriends() {
        return "friends";
    }

    /**
     * 一般来说，发送get请求都是传递param，发送post请求都是传递data
     *
     * @param friend
     * @param applyId
     * @param response
     */
    @PostMapping("/add")
    @Transactional
    public void addFriend(@RequestBody Friend friend, @RequestParam("applyId") Long applyId, HttpServletResponse response)
            throws IOException {
        int p = friendService.saveFriend(friend);
        int q = friendService.deleteApply(applyId);
        if (p == 0 || q == 0) {
            response.getWriter().print("<script language='javascript'>alert('好友同意失败了哦，找找是什么原因吧');" +
                    "window.location.href='/dev/friend';</script>");
        }
    }

    @PostMapping("/apply/add")
    public void addApply(@RequestBody Apply apply, HttpServletResponse response) throws IOException {
        int p = friendService.addApply(apply);
        if (p == 0) {
            response.getWriter().print("<script language='javascript'>alert('好友申请失败了哦，找找是什么原因吧');" +
                    "window.location.href='/dev/friend';</script>");
        }
    }

    @GetMapping("/list")
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

    @DeleteMapping("/delete/{id}")
    @Transactional
    public void deleteFriend(@PathVariable Long id, HttpSession session, HttpServletResponse response) throws IOException {
        User user = (User) session.getAttribute("user");
        int p = friendService.deleteFriendById(id, user.getId());
        if (p == 0) {
            response.getWriter().print("<script language='javascript'>alert('好友删除失败了哦，找找是什么原因吧');" +
                    "window.location.href='/dev/friend';</script>");
        }
    }

    @DeleteMapping("/apply/delete/{id}")
    @Transactional
    public void deleteApply(@PathVariable Long id, HttpServletResponse response) throws IOException {
        int p = friendService.deleteApply(id);
        if (p == 0) {
            response.getWriter().print("<script language='javascript'>alert('申请删除失败了哦，找找是什么原因吧');" +
                    "window.location.href='/dev/friend';</script>");
        }
    }

    @GetMapping("/search")
    @ResponseBody
    public List<SearchVO> searchUsers(@RequestParam(value = "keyword", required = false) String keyword,
                                      HttpServletResponse response, HttpSession session) throws IOException {
        User user = (User) session.getAttribute("user");
        if (keyword.trim().equals("")) {
            response.getWriter().print("<script language='javascript'>alert('搜索关键字输入不可以为空！');" +
                    "window.location.href='/dev/friend';</script>");
        }
        return friendService.searchUser(keyword, user.getId());
    }

    @GetMapping("/apply/list")
    @ResponseBody
    public List<Apply> getApplyList(HttpSession session) {
        User user = (User) session.getAttribute("user");
        return friendService.applyList(user.getId());
    }

    @GetMapping("/count")
    @ResponseBody
    public int getApplys(HttpSession session) {
        User user = (User) session.getAttribute("user");
        return friendService.friendsCount(user.getId());
    }
}
