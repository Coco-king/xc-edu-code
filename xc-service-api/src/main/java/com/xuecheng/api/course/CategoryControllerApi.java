package com.xuecheng.api.course;

import com.xuecheng.framework.domain.course.ext.CategoryNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "课程分类管理")
public interface CategoryControllerApi {
    @ApiOperation("查询分类")
    CategoryNode findList();
}
