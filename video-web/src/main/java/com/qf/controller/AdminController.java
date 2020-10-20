package com.qf.controller;

import com.qf.pojo.Admin;
import com.qf.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @RequestMapping("login")
    public String login(Admin admin, HttpSession session) {
        if (admin.getUsername() == null) {
            return "forward:/WEB-INF/jsp/behind/login.jsp";
        } else {
            Admin resultAdmin = adminService.findByBean(admin);
            if (resultAdmin != null && resultAdmin.getPassword().equals(admin.getPassword())
                    && resultAdmin.getUsername().equals(admin.getUsername())){
                session.setAttribute("admin", resultAdmin);
                return "forward:/video/list";
            }
            session.setAttribute("adminLoginInfo", "密码错误");
            return "forward:/WEB-INF/jsp/behind/login.jsp";
        }
    }

    @RequestMapping("exit")
    public String exit(HttpSession session) {
        session.invalidate();
        return "forward:/admin/login";
    }
}
