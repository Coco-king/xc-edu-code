package com.xuecheng.manage_cms.controller;

import com.xuecheng.manage_cms.base.CmsBaseController;
import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.nio.charset.StandardCharsets;

@Controller
public class CmsPagePreviewController extends CmsBaseController {

    @SneakyThrows
    @GetMapping("/cms/preview/{pageId}")
    public void preview(@PathVariable("pageId") String pageId) {
        String html = cmsPageService.getPageHtml(pageId);
        response.getOutputStream().write(html.getBytes(StandardCharsets.UTF_8));
    }
}
