package com.qf.service;

import com.qf.pojo.Speaker;

import java.util.List;

public interface SpeakerService {
    List<Speaker> findAll();

    Speaker findByID(Integer id);

    int addSpeaker(Speaker speaker);

    int updateSpeaker(Speaker speaker);

    int deleteSpeaker(Integer id);

    int delBatchSpeaker(Integer[] ids);
}
