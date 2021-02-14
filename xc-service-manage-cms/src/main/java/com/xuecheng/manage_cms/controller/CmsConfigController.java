package com.xuecheng.manage_cms.controller;

import com.xuecheng.api.cms.CmsConfigControllerApi;
import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.manage_cms.base.CmsBaseController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cms/config")
public class CmsConfigController extends CmsBaseController implements CmsConfigControllerApi {
    /**
     * 根据id查询数据模型
     */
    @Override
    @GetMapping("/model/{id}")
    public CmsConfig findById(@PathVariable("id") String id) {
        return cmsConfigService.findById(id);
    }
}
