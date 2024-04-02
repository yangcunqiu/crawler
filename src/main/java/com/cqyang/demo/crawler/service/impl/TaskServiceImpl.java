package com.cqyang.demo.crawler.service.impl;

import com.cqyang.demo.crawler.core.Crawler;
import com.cqyang.demo.crawler.core.CrawlerManage;
import com.cqyang.demo.crawler.exception.CrawlerException;
import com.cqyang.demo.crawler.model.CrawlerContext;
import com.cqyang.demo.crawler.model.request.AddTaskRequest;
import com.cqyang.demo.crawler.service.CrawlerService;
import com.cqyang.demo.crawler.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class TaskServiceImpl implements TaskService {
    @Autowired
    private CrawlerService crawlerService;

    @Override
    public void addTask(AddTaskRequest addTaskRequest) {
        Long crawlerUniqueKey = addTaskRequest.getCrawlerUniqueKey();
        Crawler crawler = crawlerService.getCrawler(crawlerUniqueKey);
        if (Objects.isNull(crawler)) {
            throw new CrawlerException("爬虫不存在");
        }
        CrawlerManage<?> manage = crawlerService.getCrawlerManageByUniqueKey(crawlerUniqueKey);
        if (Objects.isNull(manage)) {
            throw new CrawlerException("爬虫manage不存在, 无法添加任务");
        }
        CrawlerContext context = new CrawlerContext();
        context.setUniqueKey(crawlerUniqueKey);
        manage.addTask(crawler, addTaskRequest.getTag(), addTaskRequest.getConfigJson(), context);
    }


}
