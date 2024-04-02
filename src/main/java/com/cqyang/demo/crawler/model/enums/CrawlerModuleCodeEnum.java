package com.cqyang.demo.crawler.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@AllArgsConstructor
@Getter
public enum CrawlerModuleCodeEnum {

    // processor
    DEFAULT_PROCESSOR("defaultProcessor", "com.cqyang.demo.crawler.core.CrawlerDefaultProcessor", "默认processor", CrawlerFittingModuleTypeEnum.PROCESSOR),
    MEDICAL_PROCESSOR("medicalProcessor", "com.cqyang.demo.crawler.medical.MedicalProcessor", "医保processor", CrawlerFittingModuleTypeEnum.PROCESSOR),

    // pipeline
    DEFAULT_PIPELINE("defaultPipeline", "com.cqyang.demo.crawler.core.CrawlerDefaultPipeline", "默认pipeline", CrawlerFittingModuleTypeEnum.PIPELINE),
    MEDICAL_PIPELINE("medicalPipeline", "com.cqyang.demo.crawler.medical.MedicalPipeline", "医保pipeline", CrawlerFittingModuleTypeEnum.PIPELINE),

    // basic
    DEFAULT_BASIC("defaultBasic", "com.cqyang.demo.crawler.core.CrawlerModule", "默认基础属性组装", CrawlerFittingModuleTypeEnum.BASIC),


    ;

    private final String code;
    private final String className;
    private final String desc;
    private final CrawlerFittingModuleTypeEnum moduleTypeEnum;

    public static CrawlerModuleCodeEnum getByCode(String code) {
        for (CrawlerModuleCodeEnum value : CrawlerModuleCodeEnum.values()) {
            if (StringUtils.equals(value.getCode(), code)) {
                return value;
            }
        }
        return null;
    }
}
