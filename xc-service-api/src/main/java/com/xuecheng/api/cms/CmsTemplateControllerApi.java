package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.domain.cms.ext.CmsTemplateExt;
import com.xuecheng.framework.domain.cms.request.QueryTemplateRequest;
import com.xuecheng.framework.domain.cms.response.CmsTemplateResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags = "CMS页面管理模板接口")
public interface CmsTemplateControllerApi {
    @ApiOperation("根据条件分页查询所有模板")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "每页记录数", required = true, paramType = "path", dataType = "int")
    })
    QueryResponseResult findAll(int page, int size, QueryTemplateRequest queryTemplateRequest);

    @ApiOperation("查询所有")
    QueryResponseResult findAll();

    @ApiOperation("根据模板ID查询")
    @ApiImplicitParam(name = "id", value = "模板ID", required = true, paramType = "path", dataType = "String")
    CmsTemplateExt findOne(String id);

    @ApiOperation("保存模板")
    @ApiImplicitParam(name = "cmsTemplate", value = "模板对象", required = true, paramType = "body", dataType = "CmsTemplateExt")
    CmsTemplateResult save(CmsTemplateExt cmsTemplate);

    @ApiOperation("根据模板ID删除模板")
    @ApiImplicitParam(name = "id", value = "模板ID", required = true, paramType = "path", dataType = "String")
    ResponseResult delete(String id);

    @ApiOperation("根据模板ID修改模板")
    CmsTemplateResult update(String id, CmsTemplateExt cmsTemplate);
}
