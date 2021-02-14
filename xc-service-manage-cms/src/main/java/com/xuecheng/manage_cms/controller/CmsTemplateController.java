package com.xuecheng.manage_cms.controller;

import com.xuecheng.api.cms.CmsTemplateControllerApi;
import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.domain.cms.ext.CmsTemplateExt;
import com.xuecheng.framework.domain.cms.request.QueryTemplateRequest;
import com.xuecheng.framework.domain.cms.response.CmsTemplateResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.base.CmsBaseController;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cms/template")
public class CmsTemplateController extends CmsBaseController implements CmsTemplateControllerApi {
    /**
     * 根据条件分页查询所有模板集合
     */
    @Override
    @GetMapping("/list/{page}/{size}")
    public QueryResponseResult findAll(@PathVariable("page") int page, @PathVariable("size") int size, QueryTemplateRequest queryTemplateRequest) {
        return cmsTemplateService.findAll(page, size, queryTemplateRequest);
    }

    @Override
    @GetMapping("/list")
    public QueryResponseResult findAll() {
        return cmsPageService.findAll();
    }

    /**
     * 根据模板ID查询
     */
    @Override
    @GetMapping("/get/{id}")
    public CmsTemplateExt findOne(@PathVariable("id") String id) {
        return cmsTemplateService.findById(id);
    }

    @Override
    @PostMapping("/save")
    public CmsTemplateResult save(@RequestBody CmsTemplateExt cmsTemplate) {
        return cmsTemplateService.save(cmsTemplate);
    }

    @Override
    @DeleteMapping("/delete/{id}")
    public ResponseResult delete(@PathVariable("id") String id) {
        return cmsTemplateService.delete(id);
    }

    @Override
    @PutMapping("/update/{id}")
    public CmsTemplateResult update(@PathVariable("id") String id, @RequestBody CmsTemplateExt cmsTemplate) {
        return cmsTemplateService.update(id, cmsTemplate);
    }
}
