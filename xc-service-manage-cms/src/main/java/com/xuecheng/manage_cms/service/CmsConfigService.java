package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.manage_cms.base.CmsBaseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CmsConfigService extends CmsBaseService {
    /**
     * 根据id查询数据模型
     */
    public CmsConfig findById(String id) {
        return cmsConfigRepository.findById(id).orElse(null);
    }
}
