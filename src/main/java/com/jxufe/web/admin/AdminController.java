package com.jxufe.web.admin;

import com.jxufe.entity.Admin;
import com.jxufe.entity.User;
import com.jxufe.service.AdminService;
import com.jxufe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping({"", "/login"})
    public String doLoginPage() {
        return "admin/login";
    }

    @GetMapping("/index")
    public String index() {
        return "admin/index";
    }

    /**
     * @param username 用户输入的用户名
     * @param password 用户输入的密码
     * @param session  用来会话跟踪的类 可以缓存重要信息 session是服务器端对象 保存在服务器端
     * @param response 绑定响应
     * @return 返回一个页面
     */
    @PostMapping("/login/success")
    public String doLogin(ServletResponse response, @RequestParam String username, @RequestParam String password,
                          HttpSession session, RedirectAttributes attributes) throws IOException {
        Admin user = adminService.checkUserLogin(username, password);
        if (user != null) {
            user.setPassword(null);
            Cookie usernameCookie = new Cookie("username_admin", username);
            Cookie passwordCookie = new Cookie("password_admin", password);
            //设置cookie的时限
            usernameCookie.setMaxAge(60 * 60 * 24);
            passwordCookie.setMaxAge(60 * 60 * 24);
            //设置cookie的路径
            usernameCookie.setPath("/");
            passwordCookie.setPath("/");
            //把cookie发给浏览器
            ((HttpServletResponse) response).addCookie(usernameCookie);
            ((HttpServletResponse) response).addCookie(passwordCookie);
            //就把这个user放到HttpSession对象中 -> 多次用到
            session.setAttribute("admin", user);
            return "admin/index";
        } else {
            response.getWriter().print("<script language='javascript'>alert('用户名或密码错误');" +
                    "window.location.href='/dev/admin';</script>");
            return "redirect:/admin";
        }
    }

    @GetMapping("/register")
    public String doRegister() {
        return "admin/register";
    }

    @PostMapping("/register/success")
    public String doRegister(Admin user, RedirectAttributes attributes) {
        if (adminService.checkUserExist(user.getUsername()) != null) {
            attributes.addFlashAttribute("message", "用户名已被占用！");
            return "redirect:/admin/register";
        }
        Integer flag = adminService.addNewUser(user);
        if (flag == 0) {
            attributes.addFlashAttribute("message", "注册失败");
            return "redirect:/admin/register";
        }
        return "redirect:/admin";
    }

    /**
     * 去除日志
     *
     * @param session
     * @return 重定向到/admin
     */
    @GetMapping("/logout")
    public String doLogout(HttpSession session) {
        session.removeAttribute("admin");
        return "redirect:/admin";
    }

    @GetMapping("/edit/password")
    public String editPassword() {
        return "admin/edit_pwd";
    }

    @PostMapping("/edit/password/success")
    public void doEditPassword(HttpServletResponse response, HttpSession session,
                                 @RequestParam String newPwd, @RequestParam String oldPwd, @RequestParam String confirmPwd) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        if (!newPwd.equals(confirmPwd)) {
            response.getWriter().print("<script language='javascript'>alert('两次密码输入不一致');" +
                    "window.location.href='/dev/admin/edit/password';</script>");
        } else {
            Integer res = adminService.checkPassword(((Admin) session.getAttribute("admin")).getUsername(), oldPwd);
            if (res == 0) {
                response.getWriter().print("<script language='javascript'>alert('原密码校验失败');" +
                        "window.location.href='/dev/admin/edit/password';</script>");
            } else {
                adminService.editPassword(((Admin) session.getAttribute("admin")).getUsername(), newPwd, oldPwd);
                session.removeAttribute("lastPath");
                response.getWriter().print("<script language='javascript'>alert('修改成功，请重新登录');" +
                        "window.location.href='/dev/admin/login';</script>");
            }
        }
    }
}
