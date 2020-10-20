package com.qf.service.impl;

import com.qf.dao.CourseMapper;
import com.qf.pojo.Course;
import com.qf.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Override
    public List<Course> findALl() {
        return courseMapper.selectByExample(null);
    }

    @Override
    public List<Map> findBySubjectId(String subjectId) {
        return courseMapper.findBySubjectId(subjectId);
    }

    @Override
    public Course findById(Integer courseId) {
        return courseMapper.selectByPrimaryKey(courseId);
    }
}
