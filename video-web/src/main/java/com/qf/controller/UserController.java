package com.qf.controller;

import com.mysql.cj.Session;
import com.qf.pojo.User;
import com.qf.service.UserService;
import com.qf.utils.ImageCut;
import com.qf.utils.MailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    // 用户登录
    @ResponseBody
    @RequestMapping("login")
    public String login(String email, String password, HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        User user = userService.findByEmail(email);
        if (null != user) {
            if (user.getPassword().equals(password)) {
                session.setAttribute("userAccount", user.getEmail());
                session.setAttribute("user", user);
                return "success";
            }
        }
        return "false";
    }

    // 用户信息展示
    @RequestMapping("show")
    public String show() {
        return "forward:/WEB-INF/jsp/before/my_profile.jsp";
    }

    // 更改用户信息
    @RequestMapping("changeProfile")
    public String changeProfile() {
        return "forward:/WEB-INF/jsp/before/change_profile.jsp";
    }

    // 更改头像
    @RequestMapping("changeAvatar")
    public String changeAvatar() {
        return "forward:/WEB-INF/jsp/before/change_avatar.jsp";
    }

    // 更新信息
    @RequestMapping("update")
    public String update(User user, HttpSession session) {
        User userSession = (User) session.getAttribute("user");
        userSession.setNickname(user.getNickname());
        userSession.setSex(user.getSex());
        userSession.setBirthday(user.getBirthday());
        userSession.setAddress(user.getAddress());
        userService.update(userSession);
        session.setAttribute("user", userSession);
        return "redirect:/user/show";
    }

    // 更新头像
    @RequestMapping("upLoadImage")
    public String upLoadImage(MultipartFile photo, Double x1, Double y1, Double x2 ,Double y2,
                              HttpSession session) throws IOException {
        User user = (User) session.getAttribute("user");
        String path = "D:/tomcat/apache-tomcat-9.0.33/webapps/video/";
        String originalFilename = photo.getOriginalFilename();
        File file = new File(path + originalFilename);
        photo.transferTo(file);
        ImageCut.cutImage(file.getPath(), x1.intValue(), y1.intValue(), x2.intValue(), y2.intValue());
        userService.updateImgUrl(user.getId(), originalFilename);
        user.setImgurl(originalFilename);
        session.setAttribute("user", user);
        return "redirect:/user/show";
    }

    // 更新密码
    @RequestMapping("updatePassword")
    public String updatePassword(String oldPassword, String newPassword, HttpSession session) {
        if (null == oldPassword || null == newPassword) {
            return "forward:/WEB-INF/jsp/before/password_safe.jsp";
        } else {
            User user = (User) session.getAttribute("user");
            if (!user.getPassword().equals(oldPassword)) {
                return "forward:/WEB-INF/jsp/before/password_safe.jsp";
            } else {
                user.setPassword(newPassword);
                userService.update(user);
                session.invalidate();
                return "redirect:/subject/list";
            }
        }
    }

    // 验证密码
    @ResponseBody
    @RequestMapping("validatePassword")
    public String validatePassword(String password, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user.getPassword().equals(password)) {
            return "success";
        }
        return "";
    }

    // 验证邮箱
    @ResponseBody
    @RequestMapping("validateEmail")
    public String validateEmail(String email) {
        User user = userService.findByEmail(email);
        if (null == user) {
            return "success";
        }
        return "";
    }

    // 退出登录
    @RequestMapping("logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/subject/list";
    }

    // 注册用户
    @ResponseBody
    @RequestMapping("insertUser")
    public String insertUser(User user) {
        user.setCode("3333");
        user.setPhonenum("88888888");
        user.setCreatetime(new Date());
        int affectedRows = userService.insertUser(user);
        return affectedRows == 1 ? "success" : "";
    }

    // 忘记密码
    @RequestMapping("forgetPassword")
    public String forgetPassword() {
        return "forward:/WEB-INF/jsp/before/forget_password.jsp";
    }

    // 发送验证码
    @ResponseBody
    @RequestMapping("sendEmail")
    public String sendEmail(String email, HttpSession session) {
        User user = userService.findByEmail(email);
        if (null == user) {
            return "hasNoUser";
        } else {
            String code = MailUtils.getValidateCode(6);
            session.setAttribute("code", code);
            session.setMaxInactiveInterval(60);
            boolean sendMail = MailUtils.sendMail(email, code, "验证码");
            return sendMail ? "success" : "";
        }
    }

    // 验证验证码
    @ResponseBody
    @RequestMapping("validateEmailCode")
    public String validateEmailCode(String code, HttpSession session) {
        String sendCode = (String) session.getAttribute("code");
        if (sendCode.equals(code)) {
            return "success";
        }
        return "";
    }

    // 重置密码
    @RequestMapping("resetPassword")
    public String resetPassword(String code, String email, HttpSession session, Model model) {
        String sendCode = (String) session.getAttribute("code");
        if (sendCode.equals(code)) {
            User user = userService.findByEmail(email);
            if (null != user) {
                model.addAttribute("email", email);
                return "forward:/WEB-INF/jsp/before/reset_password.jsp";
            }
        }
        return "redirect:/subject/list";
    }

    // 更新密码
    @ResponseBody
    @RequestMapping("resetUpdate")
    public String resetUpdate(String email, String password) {
        int affectedRows = userService.updatePasswordByEmail(password, email);
        return affectedRows == 1 ? "success" : "";
    }

    @RequestMapping("toReset")
    public String toRest(){
        return "forward:/WEB-INF/jsp/before/reset_password.jsp";
    }
}
