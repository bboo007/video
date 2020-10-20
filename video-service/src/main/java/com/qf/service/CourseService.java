package com.qf.service;

import com.qf.pojo.Course;

import java.util.List;
import java.util.Map;

public interface CourseService {

    List<Course> findALl();

    List<Map> findBySubjectId(String subjectId);
}
