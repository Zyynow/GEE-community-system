package com.jxufe.web;

import com.jxufe.entity.User;
import com.jxufe.service.BlogService;
import com.jxufe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/login")
    public String doLoginPage() {
        return "login";
    }

    @PostMapping("/login/success")
    public String doLogin(HttpServletResponse response, @RequestParam String username, @RequestParam String password, @RequestParam String vCode,
                          HttpSession session, RedirectAttributes attributes) {
        String code = (String) session.getAttribute("vCode");
        if (!code.equals(vCode)) {
            attributes.addFlashAttribute("message", "验证码错误");
            return "redirect:/login";
        }
        User user = userService.checkUserLogin(username, password);
        //如果通过service对象调用dao对象中的findByUsernameAndPassword()然后返回不是null(有此用户名和密码)
        if (user != null) {
            user.setPassword(null);
            Cookie usernameCookie = new Cookie("username", username);
            Cookie passwordCookie = new Cookie("password", password);
            usernameCookie.setMaxAge(60 * 60 * 24);
            passwordCookie.setMaxAge(60 * 60 * 24);
            usernameCookie.setPath("/");
            passwordCookie.setPath("/");
            response.addCookie(usernameCookie);
            response.addCookie(passwordCookie);
            session.setAttribute("user", user);

            if (session.getAttribute("lastPath") != null) {
                if (session.getAttribute("lastPath").equals("/")) {
                    return "redirect:/user_index";
                } else if (session.getAttribute("lastPath").toString().substring(0, 6).equals("/user_")) {
                    return "redirect:" + session.getAttribute("lastPath");
                }
                return "redirect:" + "/user_" + ((String) session.getAttribute("lastPath")).substring(1);
            } else {
                return "redirect:/user_index";
            }
        } else {
            //给前端页面一个message
            attributes.addFlashAttribute("message", "用户名或密码错误");
            return "redirect:/login";
        }
    }

    @GetMapping("/register")
    public String doRegister() {
        return "register";
    }

    @PostMapping("/register/success")
    public String doRegister(User user, RedirectAttributes attributes) {
        if (userService.checkUserExist(user.getUsername()) != null) {
            attributes.addFlashAttribute("message", "用户名已被占用！");
            return "redirect:/register"; //重定向 -> 重新发一个url -> 不在原来page域 -> 刷新后massage起作用
        }
        Integer flag = userService.addNewUser(user);
        if (flag == 0) {
            attributes.addFlashAttribute("message", "注册失败");
            return "redirect:/register";
        }
        return "redirect:/index";
    }

    /**
     * 去除日志
     *
     * @param session
     * @return 重定向到/index
     */
    @GetMapping("/login/exit")
    public String doExit(HttpSession session) {
        session.removeAttribute("user");
        session.removeAttribute("lastPath");
        return "redirect:/index";
    }

    @GetMapping("/login/delete")
    public String doDelete(HttpSession session, HttpServletResponse response) throws IOException {
        User user = (User) session.getAttribute("user");
        userService.removeUser(user.getUsername());
        session.removeAttribute("user");
        session.removeAttribute("lastPath");
        return "redirect:/index";
    }

    @GetMapping("/edit/password")
    public String doEditPassword() {
        return "edit_pwd";
    }

    @PostMapping("/edit/password/success")
    public void doEditPassword(HttpServletResponse response, HttpSession session,
                               @RequestParam String newPwd, @RequestParam String oldPwd, @RequestParam String confirmPwd) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        if (!newPwd.equals(confirmPwd)) {
            response.getWriter().print("<script language='javascript'>alert('两次密码输入不一致');" +
                    "window.location.href='/dev/edit/password';</script>");
        } else {
            Integer res = userService.checkPassword(((User) session.getAttribute("user")).getUsername(), oldPwd);
            if (res == 0) {
                response.getWriter().print("<script language='javascript'>alert('原密码校验失败');" +
                        "window.location.href='/dev/edit/password';</script>");
            } else {
                userService.editPassword(((User) session.getAttribute("user")).getUsername(), newPwd, oldPwd);
                session.removeAttribute("lastPath");
                response.getWriter().print("<script language='javascript'>alert('修改成功，请重新登录');" +
                        "window.location.href='/dev/login';</script>");
            }
        }
    }

}
