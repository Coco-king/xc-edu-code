package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.domain.cms.ext.CmsTemplateExt;
import com.xuecheng.framework.domain.cms.request.QueryTemplateRequest;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.domain.cms.response.CmsTemplateResult;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.base.CmsBaseService;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Optional;

@Service
public class CmsTemplateService extends CmsBaseService {
    /**
     * 查询所有模板集合
     */
    public QueryResponseResult findAll(int page, int size, QueryTemplateRequest queryTemplateRequest) {
        if (queryTemplateRequest == null) queryTemplateRequest = new QueryTemplateRequest();
        if (page < 1) page = 1;
        if (size < 1) size = 5;
        //设置别名和名字字段模糊匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("templateName", ExampleMatcher.GenericPropertyMatchers.contains());
        //初始化条件对象
        CmsTemplate cmsTemplate = new CmsTemplate();
        String siteId = queryTemplateRequest.getSiteId();
        String templateName = queryTemplateRequest.getTemplateName();
        //判断值不为空则添加条件
        if (StringUtils.isNoneBlank(siteId)) cmsTemplate.setSiteId(siteId);
        if (StringUtils.isNoneBlank(templateName)) cmsTemplate.setTemplateName(templateName);
        //封装查询条件
        Example<CmsTemplate> example = Example.of(cmsTemplate, matcher);
        //执行查询
        Page<CmsTemplate> pages = cmsTemplateRepository.findAll(example, PageRequest.of(page - 1, size));
        return new QueryResponseResult(CommonCode.SUCCESS,
                new QueryResult<>(pages.getContent(), pages.getTotalElements()));
    }

    /**
     * 根据模板ID查询模板
     */
    public CmsTemplateExt findById(String id) {
        Optional<CmsTemplate> optional = cmsTemplateRepository.findById(id);
        //容器中对象不为空返回对象
        if (!optional.isPresent()) {
            ExceptionCast.cast(CmsCode.CMS_COURSE_PERVIEWISNULL);
        }
        CmsTemplate template = optional.get();
        CmsTemplateExt ext = new CmsTemplateExt();
        BeanUtils.copyProperties(template, ext);
        String pageTemplate = cmsPageService.getPageTemplate(template.getTemplateFileId());
        ext.setTemplateValue(pageTemplate);
        return ext;
    }

    @SneakyThrows
    public CmsTemplateResult save(CmsTemplateExt c) {
        if (StringUtils.isAllBlank(c.getSiteId(), c.getTemplateName(), c.getTemplateParameter()) || StringUtils.isBlank(c.getTemplateValue()))
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAURLISNULL);
        //查询数据库中是否有该条数据
        CmsTemplate res = cmsTemplateRepository.findBySiteIdAndTemplateNameAndTemplateParameter(c.getSiteId(), c.getTemplateName(), c.getTemplateParameter());
        //不为空，页面已存在，操作失败
        if (res != null) ExceptionCast.cast(CmsCode.CMS_ADDPAGE_EXISTSNAME);
        //设置页面id为空防止注入
        c.setTemplateId(null);
        CmsTemplate cmsTemplate = new CmsTemplate();
        InputStream inputStream = IOUtils.toInputStream(c.getTemplateValue(), "utf-8");
        ObjectId objectId = gridFsTemplate.store(inputStream, c.getTemplateName() + ".ftl");
        c.setTemplateFileId(objectId.toString());
        BeanUtils.copyProperties(c, cmsTemplate);
        cmsTemplate = cmsTemplateRepository.save(cmsTemplate);
        return new CmsTemplateResult(CommonCode.SUCCESS, cmsTemplate);
    }

    /**
     * 根据id删除模板
     */
    public ResponseResult delete(String id) {
        if (StringUtils.isBlank(id)) ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAISNULL);
        CmsTemplate cmsTemplate = findById(id);
        if (cmsTemplate == null) ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAURLISNULL);
        cmsTemplateRepository.deleteById(id);
        gridFsTemplate.delete(Query.query(Criteria.where("_id").is(cmsTemplate.getTemplateFileId())));
        return ResponseResult.SUCCESS();
    }

    @SneakyThrows
    public CmsTemplateResult update(String id, CmsTemplateExt cmsTemplate) {
        CmsTemplateExt one = findById(id);
        if (one == null) ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAURLISNULL);
        //one不为空，更新模板
        //更新模板并不需要旧的页面模板
        one.setTemplateValue(null);
        one.setSiteId(cmsTemplate.getSiteId());
        one.setTemplateParameter(cmsTemplate.getTemplateParameter());
        String templateName = cmsTemplate.getTemplateName();
        one.setTemplateName(templateName);
        //取出新的模板内容
        String templateValue = cmsTemplate.getTemplateValue();
        InputStream inputStream = IOUtils.toInputStream(templateValue, "utf-8");
        //先把旧的文件删掉
        gridFsTemplate.delete(Query.query(Criteria.where("_id").is(cmsTemplate.getTemplateFileId())));
        //储存新的文件
        ObjectId objectId = gridFsTemplate.store(inputStream, templateName + ".ftl");
        one.setTemplateFileId(objectId.toString());
        cmsTemplateRepository.save(one);
        return new CmsTemplateResult(CommonCode.SUCCESS, one);
    }
}
