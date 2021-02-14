package com.xuecheng.manage_cms.service;

import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.base.CmsBaseService;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CmsPageService extends CmsBaseService {
    /**
     * 分页和条件查询数据
     *
     * @param page             当前页
     * @param size             每页显示数量
     * @param queryPageRequest 请求的参数bean
     */
    public QueryResponseResult findAll(int page, int size, QueryPageRequest queryPageRequest) {
        if (queryPageRequest == null) queryPageRequest = new QueryPageRequest();
        if (page < 1) page = 1;
        if (size < 1) size = 5;
        //设置别名和名字字段模糊匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("pageName", ExampleMatcher.GenericPropertyMatchers.contains());
        //初始化条件对象
        CmsPage cmsPage = new CmsPage();
        String siteId = queryPageRequest.getSiteId();
        String templateId = queryPageRequest.getTemplateId();
        String pageAliase = queryPageRequest.getPageAliase();
        String pageType = queryPageRequest.getPageType();
        //判断值不为空则添加条件
        if (StringUtils.isNoneBlank(siteId)) cmsPage.setSiteId(siteId);
        if (StringUtils.isNoneBlank(templateId)) cmsPage.setTemplateId(templateId);
        if (StringUtils.isNoneBlank(pageAliase)) cmsPage.setPageAliase(pageAliase);
        if (StringUtils.isNoneBlank(pageType)) cmsPage.setPageType(pageType);
        //封装查询条件
        Example<CmsPage> example = Example.of(cmsPage, matcher);
        //执行查询
        Page<CmsPage> pages = cmsPageRepository.findAll(example, PageRequest.of(page - 1, size));
        return new QueryResponseResult(CommonCode.SUCCESS,
                new QueryResult<>(pages.getContent(), pages.getTotalElements()));
    }

    public QueryResponseResult findAll() {
        List<CmsTemplate> all = cmsTemplateRepository.findAll();
        return new QueryResponseResult(CommonCode.SUCCESS, new QueryResult<>(all, all.size()));
    }

    /**
     * 新增页面
     */
    public CmsPageResult save(CmsPage c) {
        if (c.getPageName() == null && c.getSiteId() == null && c.getPageWebPath() == null)
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAURLISNULL);
        //查询数据库中是否有该条数据
        CmsPage res = cmsPageRepository.findByPageNameAndSiteIdAndPageWebPath(c.getPageName(), c.getSiteId(), c.getPageWebPath());
        //不为空，页面已存在，操作失败
        if (res != null) ExceptionCast.cast(CmsCode.CMS_ADDPAGE_EXISTSNAME);
        //设置页面id为空防止注入
        c.setPageId(null);
        CmsPage cmsPage = cmsPageRepository.save(c);
        return new CmsPageResult(CommonCode.SUCCESS, cmsPage);
    }

    /**
     * 根据ID查询页面
     */
    public CmsPage findById(String id) {
        Optional<CmsPage> optional = cmsPageRepository.findById(id);
        //容器中对象不为空返回对象，为空则返回null
        if (!optional.isPresent()) {
            ExceptionCast.cast(CmsCode.CMS_COURSE_PERVIEWISNULL);
        }
        return optional.get();
    }

    /**
     * ID为条件，CmsPage为值，修改
     */
    public CmsPageResult update(String id, CmsPage cmsPage) {
        CmsPage one = findById(id);
        if (one == null) ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAURLISNULL);
        //one不为空，更新模板ID
        one.setTemplateId(cmsPage.getTemplateId());
        //更新所属站点
        one.setSiteId(cmsPage.getSiteId());
        //更新页面别名
        one.setPageAliase(cmsPage.getPageAliase());
        //更新页面名称
        one.setPageName(cmsPage.getPageName());
        //更新页面类型
        one.setPageType(cmsPage.getPageType());
        //更新页面参数
        one.setPageParameter(cmsPage.getPageParameter());
        //更新访问路径
        one.setPageWebPath(cmsPage.getPageWebPath());
        //更新物理路径
        one.setPagePhysicalPath(cmsPage.getPagePhysicalPath());
        //更新DataUrl
        one.setDataUrl(cmsPage.getDataUrl());
        cmsPageRepository.save(one);
        return new CmsPageResult(CommonCode.SUCCESS, one);
    }

    /**
     * 根据id删除页面
     */
    public ResponseResult delete(String id) {
        if (StringUtils.isBlank(id)) ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAISNULL);
        CmsPage cmsPage = findById(id);
        if (cmsPage == null) ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAURLISNULL);
        cmsPageRepository.deleteById(id);
        return ResponseResult.SUCCESS();
    }

    /**
     * 页面静态化
     */
    public String getPageHtml(String pageId) {
        if (StringUtils.isBlank(pageId))
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        CmsPage cmsPage = findById(pageId);
        if (cmsPage == null)
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_HTMLNOTFOUND);
        String templateId = cmsPage.getTemplateId();
        if (StringUtils.isBlank(templateId))
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        //获取template
        Optional<CmsTemplate> optional = cmsTemplateRepository.findById(templateId);
        if (!optional.isPresent()) ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        CmsTemplate template = optional.get();
        //获取数据模型
        Map templateModel = getTemplateModel(cmsPage);
        //获取页面模板
        String pageTemplate = getPageTemplate(template.getTemplateFileId());
        //执行页面静态化
        return generateHtml(pageTemplate, templateModel);
    }

    @SneakyThrows
    private String generateHtml(String pageTemplate, Map model) {
        Configuration configuration = new Configuration(Configuration.getVersion());
        StringTemplateLoader templateLoader = new StringTemplateLoader();
        templateLoader.putTemplate("template", pageTemplate);
        configuration.setTemplateLoader(templateLoader);
        Template template = configuration.getTemplate("template");
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
    }

    @SneakyThrows
    public String getPageTemplate(String templateFileId) {
        if (StringUtils.isBlank(templateFileId)) ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATENOTFOUND);
        GridFSFile file = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(templateFileId)));
        if (file == null) ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        GridFSDownloadStream downloadStream = gridFSBucket.openDownloadStream(file.getObjectId());
        //获取流
        GridFsResource resource = new GridFsResource(file, downloadStream);
        return IOUtils.toString(resource.getInputStream(), StandardCharsets.UTF_8);
    }

    private Map getTemplateModel(CmsPage cmsPage) {
        String dataUrl = cmsPage.getDataUrl();
        if (StringUtils.isBlank(dataUrl)) ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAURLISNULL);
        ResponseEntity<Map> entity = restTemplate.getForEntity(dataUrl, Map.class);
        Map body = entity.getBody();
        if (body == null) ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAISNULL);
        return body;
    }

}
