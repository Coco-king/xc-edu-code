package com.xuecheng.api.course;

import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.CourseMarket;
import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.domain.course.response.AddCourseResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags = "课程管理接口，提供课程的增、删、改、查")
public interface CourseControllerApi {
    @ApiOperation("查询课程计划列表")
    @ApiImplicitParam(name = "courseId", value = "课程ID", required = true, paramType = "path", dataType = "String")
    TeachplanNode findTeachplanList(String courseId);

    @ApiOperation("添加课程计划")
    @ApiImplicitParam(name = "teachplan", value = "课程计划", required = true, paramType = "body", dataType = "Teachplan")
    ResponseResult addTeachplan(Teachplan teachplan);

    @ApiOperation("分页查询我的课程列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "每页记录数", required = true, paramType = "path", dataType = "int")
    })
    QueryResponseResult findCourseList(int page, int size, CourseListRequest courseListRequest);

    @ApiOperation("添加课程")
    @ApiImplicitParam(name = "courseBase", value = "课程信息", required = true, paramType = "body", dataType = "CourseBase")
    AddCourseResult addCourseBase(CourseBase courseBase);

    @ApiOperation("获取课程基础信息")
    @ApiImplicitParam(name = "courseId", value = "课程ID", required = true, paramType = "path", dataType = "String")
    CourseBase getCourseBaseById(String courseId);

    @ApiOperation("更新课程信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "courseBase", value = "课程信息", required = true, paramType = "body", dataType = "CourseBase"),
            @ApiImplicitParam(name = "courseId", value = "课程ID", required = true, paramType = "path", dataType = "String")
    })
    ResponseResult updateCourseBase(String courseId, CourseBase courseBase);

    @ApiOperation("获取课程营销信息")
    @ApiImplicitParam(name = "courseId", value = "课程ID", required = true, paramType = "path", dataType = "String")
    CourseMarket getCourseMarketById(String courseId);

    @ApiOperation("更新课程营销信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "courseMarket", value = "课程营销信息", required = true, paramType = "body", dataType = "CourseMarket"),
            @ApiImplicitParam(name = "courseId", value = "课程ID", required = true, paramType = "path", dataType = "String")
    })
    ResponseResult updateCourseMarket(String courseId, CourseMarket courseMarket);
}
