package com.cqyang.demo.crawler.model.po;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 任务信息表
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TaskInfo extends BasicPO {

    private Integer id;
    // 爬虫
    private Long crawlerUniqueKey;
    // 使用的爬虫的配置
    private String crawlerFittingModuleJson;
    // 任务唯一键
    private Long uniqueKey;
    // 任务标识
    private String tag;
    // 任务配置
    private String configJson;
}
