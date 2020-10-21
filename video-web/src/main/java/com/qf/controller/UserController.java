package com.qf.controller;

import com.mysql.cj.Session;
import com.qf.pojo.User;
import com.qf.service.UserService;
import com.qf.utils.ImageCut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

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

    @RequestMapping("changeProfile")
    public String changeProfile(Model model, HttpSession session) {
        String email = (String) session.getAttribute("userAccount");
        User user = userService.findByEmail(email);
        model.addAttribute("user", user);
        return "forward:/WEB-INF/jsp/before/change_profile.jsp";
    }

    @RequestMapping("changeAvatar/{id}")
    public String changeAvatar(Model model,@PathVariable("id") Integer id) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "forward:/WEB-INF/jsp/before/change_avatar.jsp";
    }

    @RequestMapping("update")
    public String update(User user) {
        int affectedRows = userService.update(user);
        return "redirect:/user/show";
    }

    @RequestMapping("upLoadImage")
    public String upLoadImage(MultipartFile photo, Double x1, Double y1, Double x2 ,Double y2, Integer id) throws IOException {
        String path = "D:/tomcat/apache-tomcat-9.0.33/webapps/video/";
        String originalFilename = photo.getOriginalFilename();
        File file = new File(path + originalFilename);
        photo.transferTo(file);
        ImageCut.cutImage(file.getPath(), x1.intValue(), y1.intValue(), x2.intValue(), y2.intValue());
        userService.updateImgUrl(id, originalFilename);
        return "redirect:/user/show";
    }
}
