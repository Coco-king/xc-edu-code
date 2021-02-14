package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.cms.CmsConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@Api(tags = "CMS页面管理数据模型接口")
public interface CmsConfigControllerApi {
    @ApiOperation("根据id查询对应的页面数据模型")
    @ApiImplicitParam(name = "id", value = "数据模型ID", required = true, paramType = "path", dataType = "String")
    CmsConfig findById(String id);
}
