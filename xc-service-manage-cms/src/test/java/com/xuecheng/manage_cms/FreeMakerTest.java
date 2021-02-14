package com.xuecheng.manage_cms;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.manage_cms.service.CmsPageService;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@SpringBootTest
@RunWith(SpringRunner.class)
public class FreeMakerTest {

    @Autowired
    private GridFSBucket gridFSBucket;

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private CmsPageService cmsPageService;

    @Test
    public void queryFile() throws IOException {
        GridFSFile file = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is("6027f209717a470b70c0ff79")));
        if (file != null) {
            GridFSDownloadStream downloadStream = gridFSBucket.openDownloadStream(file.getObjectId());
            //获取流
            GridFsResource resource = new GridFsResource(file, downloadStream);
            String string = IOUtils.toString(resource.getInputStream(), StandardCharsets.UTF_8);
            System.out.println(string);
        }
    }

    @Test
    public void testGetPageHtml() {
        String pageHtml = cmsPageService.getPageHtml("60280963717a472cb029d93b");
        System.out.println(pageHtml);
    }
}
