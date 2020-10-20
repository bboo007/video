package com.qf.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qf.pojo.Course;
import com.qf.pojo.Speaker;
import com.qf.pojo.Video;
import com.qf.service.CourseService;
import com.qf.service.SpeakerService;
import com.qf.service.VideoService;
import com.qf.utils.VideoQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("video")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @Autowired
    private SpeakerService speakerService;

    @Autowired
    private CourseService courseService;

    @RequestMapping("list")
    public String list(Model model, VideoQueryVo videoQueryVo,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       @RequestParam(defaultValue = "1") Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<Map> videoList = videoService.findByPage(videoQueryVo);
        PageInfo<Map> pageInfo = new PageInfo<>(videoList);
        List<Speaker> speakerList = speakerService.findAll();
        List<Course> courseList = courseService.findALl();

        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("speakerList", speakerList);
        model.addAttribute("courseList", courseList);
        model.addAttribute("videoQueryVo", videoQueryVo);

        return "forward:/WEB-INF/jsp/behind/videoList.jsp";
    }

    @RequestMapping("addOrEdit")
    public String addOrEdit(Integer id, Model model){
        if (null != id) {
            Video video = videoService.findByID(id);
            model.addAttribute("video", video);
        }
        List<Speaker> speakerList = speakerService.findAll();
        List<Course> courseList = courseService.findALl();

        model.addAttribute("speakerList", speakerList);
        model.addAttribute("courseList", courseList);

        return "forward:/WEB-INF/jsp/behind/addVideo.jsp";
    }

    @RequestMapping("saveOrUpdate")
    public String saveOrUpdate(Video video){
        if (video.getId() == null) {
            int affectedRows = videoService.addVideo(video);
        } else {
            int affectedRows = videoService.updateVideo(video);
        }
        return "redirect:/video/list";
    }

    @ResponseBody
    @RequestMapping("videoDel")
    public String videoDel(Integer id) {
        int affectedRows = videoService.deleteVideo(id);
        return affectedRows == 1 ? "success" : "";
    }

    @RequestMapping("delBatchVideos")
    public String delBatchVideos(Integer[] ids){
        int affectedRows = videoService.delBatchVideos(ids);
        return "redirect:/video/list";
    }
}
