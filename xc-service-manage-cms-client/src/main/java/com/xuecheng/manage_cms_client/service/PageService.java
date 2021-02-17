package com.xuecheng.manage_cms_client.service;

import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.manage_cms_client.base.CmsBaseService;
import org.apache.commons.io.IOUtils;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Optional;

@Service
public class PageService extends CmsBaseService {

    public void savePageToServerPath(String pageId) {
        Optional<CmsPage> optional = cmsPageRepository.findById(pageId);
        if (!optional.isPresent())
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_HTMLNOTFOUND);
        CmsPage cmsPage = optional.get();
        //拼装页面保存路径
        String filePath = cmsPage.getPagePhysicalPath() + cmsPage.getPageName();
        //查询html文件，转化为输出流，关闭流
        try (InputStream inputStream = getFileById(cmsPage.getHtmlFileId()); FileOutputStream outputStream = new FileOutputStream(filePath)) {
            IOUtils.copy(inputStream, outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public InputStream getFileById(String fileId) {
        GridFSFile file = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(fileId)));
        if (file == null) return null;
        GridFSDownloadStream downloadStream = gridFSBucket.openDownloadStream(file.getObjectId());
        GridFsResource resource = new GridFsResource(file, downloadStream);
        try {
            return resource.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
