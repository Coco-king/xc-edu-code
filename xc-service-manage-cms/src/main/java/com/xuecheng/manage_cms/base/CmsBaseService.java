package com.xuecheng.manage_cms.base;

import com.mongodb.client.gridfs.GridFSBucket;
import com.xuecheng.manage_cms.dao.CmsConfigRepository;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import com.xuecheng.manage_cms.dao.CmsSiteRepository;
import com.xuecheng.manage_cms.dao.CmsTemplateRepository;
import com.xuecheng.manage_cms.service.CmsPageService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.web.client.RestTemplate;

public class CmsBaseService {
    @Autowired
    protected RestTemplate restTemplate;
    @Autowired
    protected GridFsTemplate gridFsTemplate;
    @Autowired
    protected RabbitTemplate rabbitTemplate;
    @Autowired
    protected GridFSBucket gridFSBucket;
    @Autowired
    protected CmsPageRepository cmsPageRepository;
    @Autowired
    protected CmsSiteRepository cmsSiteRepository;
    @Autowired
    protected CmsTemplateRepository cmsTemplateRepository;
    @Autowired
    protected CmsConfigRepository cmsConfigRepository;
    @Autowired
    protected CmsPageService cmsPageService;
}
