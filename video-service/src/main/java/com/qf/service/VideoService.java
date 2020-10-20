package com.qf.service;

import com.qf.pojo.Video;
import com.qf.utils.VideoQueryVo;

import java.util.List;
import java.util.Map;

public interface VideoService {
    List<Video> findAll();

    List<Map<String, Object>> findByPage(VideoQueryVo videoQueryVo);

    Video findByID(Integer id);

    int addVideo(Video video);

    int updateVideo(Video video);

    int deleteVideo(Integer id);

    int delBatchVideos(Integer[] ids);

    List<Video> findByCourseId(Integer courseId);
}
