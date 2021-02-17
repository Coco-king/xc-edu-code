package com.xuecheng.manage_cms_client.base;

import com.mongodb.client.gridfs.GridFSBucket;
import com.xuecheng.manage_cms_client.dao.CmsPageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

public class CmsBaseService {
    @Autowired
    protected GridFsTemplate gridFsTemplate;
    @Autowired
    protected GridFSBucket gridFSBucket;
    @Autowired
    protected CmsPageRepository cmsPageRepository;
}
