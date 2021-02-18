package com.xuecheng.manage_course.base;

import com.xuecheng.manage_course.service.CategoryService;
import com.xuecheng.manage_course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;

public class CourseBaseController {
    @Autowired
    protected CourseService courseService;
    @Autowired
    protected CategoryService categoryService;
}
