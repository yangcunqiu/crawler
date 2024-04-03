package com.cqyang.demo.crawler.service.impl;

import com.alibaba.fastjson.JSON;
import com.cqyang.demo.crawler.core.Crawler;
import com.cqyang.demo.crawler.core.CrawlerManage;
import com.cqyang.demo.crawler.exception.CrawlerException;
import com.cqyang.demo.crawler.mapper.TaskInfoMapper;
import com.cqyang.demo.crawler.model.CrawlerContext;
import com.cqyang.demo.crawler.model.po.TaskInfo;
import com.cqyang.demo.crawler.model.request.AddTaskRequest;
import com.cqyang.demo.crawler.model.vo.CrawlerInfoVO;
import com.cqyang.demo.crawler.service.CrawlerService;
import com.cqyang.demo.crawler.service.TaskService;
import com.cqyang.demo.crawler.util.SnowflakeIdUtil;
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

    @Autowired
    private TaskInfoMapper taskInfoMapper;

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

        // 生成任务唯一key
        Long taskUniqueKey = SnowflakeIdUtil.nextId();
        // 持久化任务
        CrawlerInfoVO crawlerInfoVO = crawlerService.findByUniqueKey(crawlerUniqueKey);
        TaskInfo taskInfo = new TaskInfo();
        taskInfo.setCrawlerUniqueKey(crawlerUniqueKey);
        taskInfo.setCrawlerFittingModuleJson(JSON.toJSONString(crawlerInfoVO.getFittingModuleVOList()));
        taskInfo.setUniqueKey(taskUniqueKey);
        taskInfo.setTag(addTaskRequest.getTag());
        taskInfo.setConfigJson(addTaskRequest.getConfigJson());
        taskInfoMapper.insert(taskInfo);

        // manage去添加任务
        manage.addTask(crawler, addTaskRequest.getTag(), taskUniqueKey,addTaskRequest.getConfigJson(), context);
    }


}
