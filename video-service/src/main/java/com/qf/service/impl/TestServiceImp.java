package com.qf.service.impl;

import com.qf.dao.VideoMapper;
import com.qf.pojo.Video;
import com.qf.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestServiceImp implements TestService {

    @Autowired
    private VideoMapper videoMapper;

    public List<Video> findALl() {
        return videoMapper.selectByExample(null);
    }
}
