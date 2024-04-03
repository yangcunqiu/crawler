package com.cqyang.demo.crawler.model.po;

import com.cqyang.demo.crawler.model.enums.CollectTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Request维度log
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CollectRawLog extends BasicPO {

    private Long id;
    // 爬虫唯一键
    private Long crawlerUniqueKey;
    // 任务
    private Long taskUniqueKey;
    // 请求
    private Long requestUniqueKey;
    /**
     * 采集类型
     * @see CollectTypeEnum
     */
    private int collectType;
    // 原始请求
    private String rawRequest;
    // 原始响应
    private String rawResponse;
}
