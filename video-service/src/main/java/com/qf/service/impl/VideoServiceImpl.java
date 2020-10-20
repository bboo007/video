package com.qf.service.impl;

import com.qf.dao.VideoMapper;
import com.qf.pojo.Video;
import com.qf.pojo.VideoExample;
import com.qf.service.VideoService;
import com.qf.utils.VideoQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoMapper videoMapper;

    @Override
    public List<Video> findAll() {
        return null;
    }

    @Override
    public List<Map> findByPage(VideoQueryVo videoQueryVo) {
        return videoMapper.findByPage(videoQueryVo);
    }

    @Override
    public Video findByID(Integer id) {
        return videoMapper.selectByPrimaryKey(id);
    }

    @Override
    public int addVideo(Video video) {
        return videoMapper.insert(video);
    }

    @Override
    public int updateVideo(Video video) {
        return videoMapper.updateByPrimaryKeyWithBLOBs(video);
    }

    @Override
    public int deleteVideo(Integer id) {
        return videoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int delBatchVideos(Integer[] ids) {
        VideoExample videoExample = new VideoExample();
        VideoExample.Criteria criteria = videoExample.createCriteria();
        criteria.andIdIn(Arrays.asList(ids));
        return videoMapper.deleteByExample(videoExample);
    }
}
