package com.xuecheng.manage_cms.controller;

import com.xuecheng.api.cms.CmsSiteControllerApi;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.manage_cms.base.CmsBaseController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cms/site")
public class CmsSiteController extends CmsBaseController implements CmsSiteControllerApi {
    /**
     * 查询所有站点集合
     */
    @Override
    @GetMapping("/list")
    public QueryResponseResult findAll() {
        return cmsSiteService.findAll();
    }
}
