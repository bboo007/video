package com.qf.interceptor;

import com.qf.pojo.Admin;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AdminInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        if (null != session) {
            Admin admin = (Admin) session.getAttribute("admin");
            if (null != admin) {
                return admin.getUsername() != null && admin.getPassword() != null;
            }
        }
        response.sendRedirect("/admin/login");
        return false;
    }
}
