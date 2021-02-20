package com.xuecheng.manage_cms_client.mq;

import com.alibaba.fastjson.JSON;
import com.xuecheng.manage_cms_client.base.CmsBaseController;
import com.xuecheng.manage_cms_client.service.PageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ConsumerPostPage extends CmsBaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerPostPage.class);

    @RabbitListener(queues = "${xuecheng.mq.queue}")
    public void postPage(String msg) {
        Map map = JSON.parseObject(msg, Map.class);
        LOGGER.info("接受到的页面信息为：{}", map.toString());
        pageService.savePageToServerPath((String) map.get("pageId"));
    }
}
