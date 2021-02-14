package com.xuecheng.manage_cms.base;

import com.xuecheng.framework.web.BaseController;
import com.xuecheng.manage_cms.service.CmsConfigService;
import com.xuecheng.manage_cms.service.CmsPageService;
import com.xuecheng.manage_cms.service.CmsSiteService;
import com.xuecheng.manage_cms.service.CmsTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

public class CmsBaseController extends BaseController {
    @Autowired
    protected RestTemplate restTemplate;
    @Autowired
    protected CmsSiteService cmsSiteService;
    @Autowired
    protected CmsPageService cmsPageService;
    @Autowired
    protected CmsTemplateService cmsTemplateService;
    @Autowired
    protected CmsConfigService cmsConfigService;
}
