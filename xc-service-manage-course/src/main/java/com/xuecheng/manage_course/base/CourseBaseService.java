package com.xuecheng.manage_course.base;

import com.xuecheng.manage_course.dao.*;
import org.springframework.beans.factory.annotation.Autowired;

public class CourseBaseService {
    @Autowired
    protected CourseMapper courseMapper;
    @Autowired
    protected CourseBaseRepository courseBaseRepository;
    @Autowired
    protected TeachplanMapper teachplanMapper;
    @Autowired
    protected TeachplanRepository teachplanRepository;
    @Autowired
    protected CategoryMapper categoryMapper;
    @Autowired
    protected CourseMarketRepository courseMarketRepository;
    @Autowired
    protected CoursePicRepository coursePicRepository;
}
