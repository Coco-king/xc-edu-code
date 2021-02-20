package com.xuecheng.manage_cms.controller;

import com.xuecheng.manage_cms.base.CmsBaseController;
import io.swagger.annotations.Api;
import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.nio.charset.StandardCharsets;

@Controller
@Api(tags = "页面预览功能接口")
public class CmsPagePreviewController extends CmsBaseController {

    @SneakyThrows
    @GetMapping("/cms/preview/{pageId}")
    public void preview(@PathVariable("pageId") String pageId) {
        String html = cmsPageService.getPageHtml(pageId);
        response.setHeader("Content-type", "text/html;charset=utf-8");
        response.getOutputStream().write(html.getBytes(StandardCharsets.UTF_8));
    }
}
