package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.domain.cms.ext.CmsTemplateExt;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CmsTemplateRepository extends MongoRepository<CmsTemplate, String> {
    CmsTemplate findBySiteIdAndTemplateNameAndTemplateParameter(String siteId, String templateName, String templateParameter);
}
