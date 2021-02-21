package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.domain.course.response.CmsPostPageResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags = "CMS页面管理接口，提供页面的增、删、改、查")
public interface CmsPageControllerApi {

    @ApiOperation("分页查询页面列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "每页记录数", required = true, paramType = "path", dataType = "int")
    })
    QueryResponseResult findAll(int page, int size, QueryPageRequest queryPageRequest);

    @ApiOperation("新增页面")
    @ApiImplicitParam(name = "cmsPage", value = "页面对象", required = true, paramType = "body", dataType = "CmsPage")
    CmsPageResult save(CmsPage cmsPage);

    @ApiOperation("根据页面ID查询")
    @ApiImplicitParam(name = "id", value = "页面ID", required = true, paramType = "path", dataType = "String")
    CmsPage findOne(String id);

    @ApiOperation("修改页面")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "页面ID", required = true, paramType = "path", dataType = "String"),
            @ApiImplicitParam(name = "cmsPage", value = "页面对象", required = true, paramType = "body", dataType = "CmsPage")
    })
    CmsPageResult update(String id, CmsPage cmsPage);

    @ApiOperation("根据页面ID删除页面")
    @ApiImplicitParam(name = "id", value = "页面ID", required = true, paramType = "path", dataType = "String")
    ResponseResult delete(String id);

    @ApiOperation("发布页面")
    @ApiImplicitParam(name = "id", value = "页面ID", required = true, paramType = "path", dataType = "String")
    ResponseResult post(String id);

    @ApiOperation("一键发布页面")
    @ApiImplicitParam(name = "cmsPage", value = "页面信息", required = true, paramType = "body", dataType = "CmsPage")
    CmsPostPageResult postPageQuick(CmsPage cmsPage);

    @ApiOperation("新增或修改页面")
    @ApiImplicitParam(name = "cmsPage", value = "页面对象", required = true, paramType = "body", dataType = "CmsPage")
    CmsPageResult saveOrUpdate(CmsPage cmsPage);
}
