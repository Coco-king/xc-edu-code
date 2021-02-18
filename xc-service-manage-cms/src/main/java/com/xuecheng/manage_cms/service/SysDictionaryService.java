package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.system.SysDictionary;
import com.xuecheng.manage_cms.base.CmsBaseService;
import org.springframework.stereotype.Service;

@Service
public class SysDictionaryService extends CmsBaseService {

    /**
     * 根据类型查询对应的数据字典
     */
    public SysDictionary getByType(String type) {
        return sysDictionaryRepository.findByDType(type);
    }
}
