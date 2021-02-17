package com.xuecheng.manage_cms.controller;

import com.xuecheng.api.cms.CmsPageControllerApi;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.base.CmsBaseController;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cms/page")
public class CmsPageController extends CmsBaseController implements CmsPageControllerApi {
    /**
     * 分页和条件查询数据
     *
     * @param page             当前页
     * @param size             每页显示数量
     * @param queryPageRequest 请求的参数bean
     */
    @Override
    @GetMapping("/list/{page}/{size}")
    public QueryResponseResult findAll(@PathVariable("page") int page, @PathVariable("size") int size, QueryPageRequest queryPageRequest) {
        return cmsPageService.findAll(page, size, queryPageRequest);
    }

    /**
     * 新增页面
     */
    @Override
    @PostMapping("/save")
    public CmsPageResult save(@RequestBody CmsPage cmsPage) {
        return cmsPageService.save(cmsPage);
    }

    /**
     * 根据ID查询页面
     */
    @Override
    @GetMapping("/get/{id}")
    public CmsPage findOne(@PathVariable("id") String id) {
        return cmsPageService.findById(id);
    }

    /**
     * ID为条件，CmsPage为值，修改
     */
    @Override
    @PutMapping("/update/{id}")
    public CmsPageResult update(@PathVariable("id") String id, @RequestBody CmsPage cmsPage) {
        return cmsPageService.update(id, cmsPage);
    }

    /**
     * 根据id删除页面
     */
    @Override
    @DeleteMapping("/delete/{id}")
    public ResponseResult delete(@PathVariable("id") String id) {
        return cmsPageService.delete(id);
    }

    /**
     * 发布页面
     */
    @Override
    @PostMapping("/postPage/{id}")
    public ResponseResult post(@PathVariable("id") String id) {
        return cmsPageService.post(id);
    }
}
