package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.CoursePic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoursePicRepository extends JpaRepository<CoursePic, String> {
    List<CoursePic> findByCourseid(String courseId);
}
