package com.xuecheng.api.cms;

import com.xuecheng.framework.model.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "CMS页面管理站点接口")
public interface CmsSiteControllerApi {
    @ApiOperation("查询所有站点")
    QueryResponseResult findAll();
}
