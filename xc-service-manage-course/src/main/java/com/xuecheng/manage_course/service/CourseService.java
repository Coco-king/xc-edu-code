package com.xuecheng.manage_course.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.CourseMarket;
import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.CourseInfo;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.domain.course.response.AddCourseResult;
import com.xuecheng.framework.domain.course.response.CourseCode;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.base.CourseBaseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService extends CourseBaseService {
    /**
     * 查询指定课程id的树形结构
     */
    public TeachplanNode findTeachplanList(String courseId) {
        return teachplanMapper.findTeachplanList(courseId);
    }

    /**
     * 添加课程计划
     */
    @Transactional
    public ResponseResult addTeachplan(Teachplan teachplan) {
        if (teachplan == null || StringUtils.isBlank(teachplan.getCourseid()) ||
                StringUtils.isBlank(teachplan.getPname())) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        //判断父节点id是否为空
        String parentid = teachplan.getParentid();
        if (StringUtils.isBlank(parentid)) {
            //为空，根据课程id查询父节点
            parentid = getTeachplanRoot(teachplan.getCourseid());
        }
        //不为空，取出父节点信息
        Optional<Teachplan> optional = teachplanRepository.findById(parentid);
        if (!optional.isPresent()) {
            ExceptionCast.cast(CourseCode.COURSE_PUBLISH_COURSENOTFOUND);
        }
        Teachplan plan = optional.get();
        teachplan.setParentid(parentid);
        teachplan.setCourseid(plan.getCourseid());
        //设置父节点的级别
        teachplan.setGrade(plan.getGrade().equals("1") ? "2" : "3");
        teachplanRepository.save(teachplan);
        return ResponseResult.SUCCESS();
    }

    /**
     * 根据课程id查询或新增父节点
     */
    private String getTeachplanRoot(String courseid) {
        if (StringUtils.isBlank(courseid))
            ExceptionCast.cast(CourseCode.COURSE_PUBLISH_COURSEIDISNULL);
        Optional<CourseBase> optional = courseBaseRepository.findById(courseid);
        if (!optional.isPresent())
            ExceptionCast.cast(CourseCode.COURSE_PUBLISH_COURSENOTFOUND);
        CourseBase courseBase = optional.get();
        List<Teachplan> list = teachplanRepository.findByCourseidAndParentid(courseBase.getId(), "0");
        if (CollectionUtils.isEmpty(list)) {
            //为空，没有以该课程ID为根节点的课程计划，新建一个根节点的课程计划
            Teachplan teachplan = new Teachplan();
            teachplan.setCourseid(courseid);
            teachplan.setParentid("0");
            teachplan.setPname(courseBase.getName());
            teachplan.setGrade("1");
            teachplan.setOrderby(1);
            teachplan.setTrylearn("1");
            teachplanRepository.save(teachplan);
            return teachplan.getId();
        }
        return list.get(0).getId();
    }

    /**
     * 分页查询课程列表
     */
    public QueryResponseResult findCourseList(int page, int size, CourseListRequest courseListRequest) {
        if (page < 1) page = 1;
        if (size < 7) size = 7;
        //开启分页
        PageHelper.startPage(page, size);
        Page<CourseInfo> courseInfos = courseMapper.findCourseListPage(courseListRequest);
        return new QueryResponseResult(CommonCode.SUCCESS, new QueryResult<>(courseInfos.getResult(), courseInfos.getTotal()));
    }

    /**
     * 新增课程
     */
    @Transactional
    public AddCourseResult addCourseBase(CourseBase courseBase) {
        if (StringUtils.isBlank(courseBase.getName()))
            ExceptionCast.cast(CourseCode.COURSE_PUBLISH_COURSENAMEMUSTNOTNULL);
        courseBase.setId(null);
        courseBaseRepository.save(courseBase);
        return new AddCourseResult(CommonCode.SUCCESS, courseBase.getId());
    }

    /**
     * 获取课程基本信息
     */
    public CourseBase getCourseBaseById(String courseId) {
        if (StringUtils.isBlank(courseId))
            ExceptionCast.cast(CourseCode.COURSE_PUBLISH_COURSEIDISNULL);
        return courseBaseRepository.findById(courseId).orElse(new CourseBase());
    }

    /**
     * 更新课程基本信息
     */
    @Transactional
    public ResponseResult updateCourseBase(String courseId, CourseBase courseBase) {
        if (StringUtils.isBlank(courseId))
            ExceptionCast.cast(CourseCode.COURSE_PUBLISH_COURSEIDISNULL);
        if (StringUtils.isBlank(courseBase.getName()))
            ExceptionCast.cast(CourseCode.COURSE_PUBLISH_COURSENAMEMUSTNOTNULL);
        courseBaseRepository.save(courseBase);
        return ResponseResult.SUCCESS();
    }

    /**
     * 获取课程营销信息
     */
    public CourseMarket getCourseMarketById(String courseId) {
        if (StringUtils.isBlank(courseId))
            ExceptionCast.cast(CourseCode.COURSE_PUBLISH_COURSEIDISNULL);
        return courseMarketRepository.findById(courseId).orElse(new CourseMarket());
    }

    /**
     * 更新课程营销信息
     */
    @Transactional
    public ResponseResult updateCourseMarket(String courseId, CourseMarket courseMarket) {
        if (StringUtils.isBlank(courseId))
            ExceptionCast.cast(CourseCode.COURSE_PUBLISH_COURSEIDISNULL);
        if (StringUtils.isBlank(courseMarket.getCharge()) || StringUtils.isBlank(courseMarket.getValid()))
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        if (courseMarket.getStartTime() != null && courseMarket.getEndTime() != null) {
            if (courseMarket.getEndTime().getTime() <= courseMarket.getStartTime().getTime()) {
                ExceptionCast.cast(CourseCode.COURSE_PUBLISH_TIMEISFAIL);
            }
        }
        Optional<CourseMarket> optional = courseMarketRepository.findById(courseId);
        //存在更新
        if (optional.isPresent()) {
            CourseMarket market = optional.get();
            //保留原始的ID
            market.setCharge(courseMarket.getCharge());
            market.setEndTime(courseMarket.getEndTime());
            market.setStartTime(courseMarket.getStartTime());
            market.setQq(courseMarket.getQq());
            market.setPrice_old(market.getPrice());
            market.setPrice(courseMarket.getPrice());
            market.setValid(courseMarket.getValid());
            courseMarketRepository.save(market);
        } else {
            //不存在，新增
            courseMarketRepository.save(courseMarket);
        }
        return ResponseResult.SUCCESS();
    }
}
