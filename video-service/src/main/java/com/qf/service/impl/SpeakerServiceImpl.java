package com.qf.service.impl;

import com.qf.dao.SpeakerMapper;
import com.qf.pojo.Speaker;
import com.qf.pojo.SpeakerExample;
import com.qf.service.SpeakerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class SpeakerServiceImpl implements SpeakerService {

    @Autowired
    private SpeakerMapper speakerMapper;

    @Override
    public List<Speaker> findAll() {
        return speakerMapper.selectByExampleWithBLOBs(null);
    }

    @Override
    public Speaker findByID(Integer id) {
        return speakerMapper.selectByPrimaryKey(id);
    }

    @Override
    public int addSpeaker(Speaker speaker) {
        return speakerMapper.insert(speaker);
    }

    @Override
    public int updateSpeaker(Speaker speaker) {
        return speakerMapper.updateByPrimaryKeyWithBLOBs(speaker);
    }

    @Override
    public int deleteSpeaker(Integer id) {
        return speakerMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int delBatchSpeaker(Integer[] ids) {
        SpeakerExample speakerExample = new SpeakerExample();
        SpeakerExample.Criteria criteria = speakerExample.createCriteria();
        criteria.andIdIn(Arrays.asList(ids));
        return speakerMapper.deleteByExample(speakerExample);
    }
}
