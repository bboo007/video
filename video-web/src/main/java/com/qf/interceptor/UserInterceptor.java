package com.qf.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UserInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        if (null != session) {
            Object userAccount = session.getAttribute("userAccount");
            if (null != userAccount) {
                return true;
            }
        }
        response.sendRedirect("/subject/list");
        return false;
    }
}
