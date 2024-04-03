package com.cqyang.demo.crawler.medical;

import com.alibaba.fastjson.JSON;
import com.cqyang.demo.crawler.core.Crawler;
import com.cqyang.demo.crawler.core.CrawlerManage;
import com.cqyang.demo.crawler.exception.CrawlerException;
import com.cqyang.demo.crawler.medical.builder.MedicalBuilder;
import com.cqyang.demo.crawler.medical.model.enums.MedicalBizTypeEnum;
import com.cqyang.demo.crawler.model.CrawlerContext;
import com.cqyang.demo.crawler.model.enums.CollectTypeEnum;
import com.cqyang.demo.crawler.model.enums.CrawlerSceneEnum;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * 医保局爬虫
 */
@Component
@Slf4j
public class MedicalCrawlerManage extends CrawlerManage<MedicalConfig> {



    @Autowired
    private List<MedicalBuilder<?, ?>> medicalBuilderList;

    @Override
    public Integer getScene() {
        return CrawlerSceneEnum.MEDICAL.getScene();
    }

    @Override
    protected MedicalConfig getConfig(String configJson) {
        return JSON.parseObject(configJson, MedicalConfig.class);
    }

    @Override
    protected void invoke0(Crawler crawler, String tag, MedicalConfig medicalConfig, CrawlerContext context) {
        Integer bizType = medicalConfig.getBizType();
        context.setBizType(bizType);
        // 采集列表
        if (Objects.equals(medicalConfig.getTaskType(), CollectTypeEnum.COLLECT_LIST.getType())) {
            collectList(crawler, bizType, context);
        } else if (Objects.equals(medicalConfig.getTaskType(), CollectTypeEnum.COLLECT_DETAIL.getType())) {
            collectDetail();
        }
    }

    private void collectList(Crawler crawler, Integer bizType, CrawlerContext context) {
        MedicalBuilder<?, ?> builder = getMedicalBuilderByBizType(bizType);
        if (Objects.isNull(builder)) {
            throw new CrawlerException("医保局Request builder为null");
        }

        context.setCollectType(CollectTypeEnum.COLLECT_LIST.getType());

        context.setBuilder(builder);
        // 用每个业务的builder去添加Request
        builder.addRequest(crawler, context);
    }

    private void collectDetail() {

    }

    private MedicalBuilder<?, ?> getMedicalBuilderByBizType(Integer bizType) {
        for (MedicalBuilder<?, ?> medicalBuilder : medicalBuilderList) {
            if (Objects.equals(medicalBuilder.getType(), bizType)) {
                return medicalBuilder;
            }
        }
        return null;
    }
}


@Data
class MedicalConfig {

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
}