package com.qf.controller;

import com.qf.pojo.Video;
import com.qf.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("test")
public class TestController {

    @Autowired
    private TestService testService;

    @ResponseBody
    @RequestMapping("one")
    public List<Video> One(){
        return testService.findALl();
    }
}
