package com.cqyang.demo.crawler.model.request;

import com.sun.istack.internal.NotNull;
import lombok.Data;

/**
 * 添加任务
 */
@Data
public class AddTaskRequest {

    /**
     * 爬虫唯一键
     */
    @NotNull
    private Long crawlerUniqueKey;

    private String tag;

    private String configJson;

}
