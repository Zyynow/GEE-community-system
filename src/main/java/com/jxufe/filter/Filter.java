package com.jxufe.filter;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Filter implements javax.servlet.Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("---------------------init()---------------------");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        request.setAttribute("username", "");
        request.setAttribute("password", "");
        request.setAttribute("username_admin", "");
        request.setAttribute("password_admin", "");
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return;
        } else {
            for (Cookie c : cookies) {
                if ("username".equals(c.getName())) {
                    String value = c.getValue();
                    request.setAttribute("username", value);
                }
                if ("password".equals(c.getName())) {
                    String value = c.getValue();
                    request.setAttribute("password", value);
                }
                if ("username_admin".equals(c.getName())) {
                    String value = c.getValue();
                    request.setAttribute("username_admin", value);
                }
                if ("password_admin".equals(c.getName())) {
                    String value = c.getValue();
                    request.setAttribute("password_admin", value);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
