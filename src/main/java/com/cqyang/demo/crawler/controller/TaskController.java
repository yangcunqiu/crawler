package com.cqyang.demo.crawler.controller;

import com.cqyang.demo.crawler.model.CrawlerResult;
import com.cqyang.demo.crawler.model.request.AddTaskRequest;
import com.cqyang.demo.crawler.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/add")
    public CrawlerResult<?> addTask(@RequestBody @Validated AddTaskRequest addTaskRequest) {
        taskService.addTask(addTaskRequest);
        return CrawlerResult.success();
    }
}
