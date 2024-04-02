package com.cqyang.demo.crawler.model;


import com.cqyang.demo.crawler.medical.model.enums.MedicalBizTypeEnum;
import com.cqyang.demo.crawler.model.enums.CollectTypeEnum;
import lombok.Data;

@Data
public class CrawlerContext {
    public static final String EXTRA_KEY = "crawlerContext";

    // 爬虫唯一键
    private long uniqueKey;

    /**
     * 采集类型
     * @see CollectTypeEnum
     */
    private Integer taskType;

    /**
     * // 业务类型
     * @see MedicalBizTypeEnum
     */
    private Integer bizType;

    /**
     * 每次请求的唯一键
     */
    private Long requestUniqueKey;
}
