package com.xuecheng.manage_course.service;

import com.xuecheng.framework.domain.course.ext.CategoryNode;
import com.xuecheng.manage_course.base.CourseBaseService;
import org.springframework.stereotype.Service;

@Service
public class CategoryService extends CourseBaseService {
    /**
     * 查询树状结构的分类列表
     */
    public CategoryNode findList() {
        return categoryMapper.findList();
    }
}
