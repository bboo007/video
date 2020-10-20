package com.qf.controller;

import com.qf.pojo.Subject;
import com.qf.service.CourseService;
import com.qf.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @Autowired
    private SubjectService subjectService;

    @RequestMapping("map/{subjectId}")
    public String map(@PathVariable("subjectId") String subjectId, Model model) {
        List<Map> mapList = courseService.findBySubjectId(subjectId);
        List<Subject> subjectList = subjectService.findAll();
        model.addAttribute("subject",mapList.get(0));
        model.addAttribute("subjectList",subjectList);
        return "forward:/WEB-INF/jsp/before/course.jsp";
    }
}
