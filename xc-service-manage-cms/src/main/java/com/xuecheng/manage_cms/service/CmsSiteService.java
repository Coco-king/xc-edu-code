package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.manage_cms.base.CmsBaseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CmsSiteService extends CmsBaseService {
    /**
     * 查询所有站点集合
     */
    public QueryResponseResult findAll() {
        List<CmsSite> list = cmsSiteRepository.findAll();
        return new QueryResponseResult(CommonCode.SUCCESS, new QueryResult<>(list, list.size()));
    }
}
