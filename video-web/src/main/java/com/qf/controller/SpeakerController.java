package com.qf.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qf.pojo.Speaker;
import com.qf.service.SpeakerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("speaker")
public class SpeakerController {

    @Autowired
    private SpeakerService speakerService;

    @RequestMapping("list")
    public String list(Model model,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       @RequestParam(defaultValue = "1") Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<Speaker> speakerList = speakerService.findAll();
        PageInfo<Speaker> pageInfo = new PageInfo<>(speakerList);

        model.addAttribute("pageInfo", pageInfo);

        return "forward:/WEB-INF/jsp/behind/speakerList.jsp";
    }

    @RequestMapping("addOrEdit")
    public String addOrEdit(Integer id, Model model){
        if (null != id) {
            Speaker speaker = speakerService.findByID(id);
            model.addAttribute("speaker", speaker);
        }

        return "forward:/WEB-INF/jsp/behind/addSpeaker.jsp";
    }

    @RequestMapping("saveOrUpdate")
    public String saveOrUpdate(Speaker speaker){
        if (speaker.getId() == null) {
            int affectedRows = speakerService.addSpeaker(speaker);
        } else {
            int affectedRows = speakerService.updateSpeaker(speaker);
        }
        return "redirect:/speaker/list";
    }

    @ResponseBody
    @RequestMapping("speakerDel")
    public String speakerDel(Integer id) {
        int affectedRows = speakerService.deleteSpeaker(id);
        return affectedRows == 1 ? "success" : "";
    }

    @RequestMapping("delBatchSpeaker")
    public String delBatchSpeaker(Integer[] ids){
        int affectedRows = speakerService.delBatchSpeaker(ids);
        return "redirect:/speaker/list";
    }
}
