package com.jxufe.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OtherInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        response.setContentType("text/html; charset=utf-8");
        if (request.getServletPath().equals("/index")) {
            if (request.getSession().getAttribute("user") == null && request.getAttribute("flag") == null) {
                request.setAttribute("flag", true);
                request.getRequestDispatcher("/index").forward(request, response);
            } else {
                response.sendRedirect("user_index");
            }
        } else if (request.getServletPath().equals("/forum")) {
            if (request.getSession().getAttribute("user") == null) {
                response.sendRedirect("forum");
            } else {
                response.sendRedirect("user_forum");
            }
        } else if (request.getServletPath().equals("/types")) {
            if (request.getSession().getAttribute("user") == null) {
                response.sendRedirect("types");
            } else {
                response.sendRedirect("user_types");
            }
        } else if (request.getServletPath().equals("/resource")) {
            if (request.getSession().getAttribute("user") == null) {
                response.sendRedirect("resource");
            } else {
                response.sendRedirect("user_resource");
            }
        } else if (request.getServletPath().equals("/search")) {
            if (request.getSession().getAttribute("user") == null) {
                response.sendRedirect("search");
            } else {
                response.sendRedirect("user_search");
            }
        }

        return true;
    }
}
