package com.cqyang.demo.crawler.model;


import com.cqyang.demo.crawler.medical.builder.MedicalBuilder;
import com.cqyang.demo.crawler.medical.model.MedicalPageResponse;
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
    private Integer collectType;
    /**
     * 采集列表id, 只有当采集类型为COLLECT_DETAIL时才有值
     */
    private Long collectListId;

    /**
     * // 业务类型
     * @see MedicalBizTypeEnum
     */
    private Integer bizType;

    /**
     * 任务唯一键
     */
    private Long taskUniqueKey;

    /**
     * 任务标识
     */
    private String taskTag;

    /**
     * 每次请求的唯一键
     */
    private Long requestUniqueKey;

    /**
     * 采集的唯一键, 防止重复采集
     */
    private String collectUniqueKey;

    /**
     * 请求的条件
     */
    private String keyword;

    /**
     * 原始响应
     */
    private String rawText;

    /**
     * 采集到的数据
     */
    private String data;

    private MedicalBuilder<?, ?> builder;
}
