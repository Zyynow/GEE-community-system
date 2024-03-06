package com.jxufe.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 拦截器是在面向切面编程中应用的，就是在你的service或者一个方法前调用一个方法，或者在方法后调用一个方法。
 * 是基于JAVA的反射机制
 *
 * 执行顺序：
 * 1. 请求到达 DispatcherServlet(只有经过了DispatcherServlet才被拦截)
 * 2. DispatcherServlet 发送至 Interceptor 执行 preHandle
 * 3. 请求达到 Controller
 * 4. 请求结束后 postHandle 执行
 *
 * interceptor和filter执行关系：
 * 过滤前->拦截前->Action处理->拦截后->过滤后
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        if (request.getSession().getAttribute("admin") == null) {
            response.sendRedirect("/dev/admin"); //重定向到/admin(登录)页面
            return false;
        }

        return true;
    }
}
