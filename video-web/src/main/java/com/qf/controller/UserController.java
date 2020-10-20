package com.qf.controller;

import com.mysql.cj.Session;
import com.qf.pojo.User;
import com.qf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping("login")
    public String login(String email, String password, HttpSession session) {
        User user = userService.findByEmail(email);
        if (null != user) {
            if (user.getPassword().equals(password)) {
                session.setAttribute("userAccount", user.getEmail());
                return "success";
            }
        }
        return "false";
    }

    @RequestMapping("show")
    public String show(HttpSession session, Model model) {
        String email = (String) session.getAttribute("userAccount");
        User user = userService.findByEmail(email);
        model.addAttribute("user", user);
        return "forward:/WEB-INF/jsp/before/my_profile.jsp";
    }
}
