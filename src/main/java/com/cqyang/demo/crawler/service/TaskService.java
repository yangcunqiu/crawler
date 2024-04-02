package com.cqyang.demo.crawler.service;

import com.cqyang.demo.crawler.model.request.AddTaskRequest;

public interface TaskService {

    /**
     * 向爬虫添加一个任务
     */
    void addTask(AddTaskRequest addTaskRequest);
}
