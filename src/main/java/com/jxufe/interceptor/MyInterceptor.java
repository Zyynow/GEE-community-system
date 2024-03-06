package com.jxufe.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        response.setContentType("text/html; charset=utf-8");
        if (request.getSession().getAttribute("user") == null) {
            response.getWriter().print("<script language='javascript'>alert('请先登录账户');" +
                    "window.location.href='/dev/login';</script>");
            return false;
        }

        return true;
    }
}
