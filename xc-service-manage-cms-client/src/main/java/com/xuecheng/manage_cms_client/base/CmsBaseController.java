package com.xuecheng.manage_cms_client.base;

import com.xuecheng.framework.web.BaseController;
import com.xuecheng.manage_cms_client.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

public class CmsBaseController extends BaseController {
    @Autowired
    protected RestTemplate restTemplate;
    @Autowired
    protected PageService pageService;
}
