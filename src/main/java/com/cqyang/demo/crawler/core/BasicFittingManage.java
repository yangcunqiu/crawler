package com.cqyang.demo.crawler.core;

import com.alibaba.fastjson.JSON;
import com.cqyang.demo.crawler.model.BasicFittingConfig;
import com.cqyang.demo.crawler.model.enums.CrawlerFittingModuleTypeEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class BasicFittingManage extends CrawlerFittingManage<BasicFittingConfig, BasicModule>{

    @Autowired
    private List<BasicModule> crawlerModules;

    @Override
    public Integer fittingType() {
        return CrawlerFittingModuleTypeEnum.BASIC.getType();
    }

    @Override
    public Integer sort() {
        return CrawlerFittingModuleTypeEnum.BASIC.getSort();
    }

    @Override
    protected Integer fittingModuleCount() {
        return null;
    }

    @Override
    protected List<BasicModule> listCrawlerModule() {
        return crawlerModules;
    }

    @Override
    protected BasicFittingConfig getFittingConfig(String fittingConfigJson) {
        if (StringUtils.isBlank(fittingConfigJson)) {
            return new BasicFittingConfig();
        }
        return JSON.parseObject(fittingConfigJson, BasicFittingConfig.class);
    }

    @Override
    protected Crawler fitting(Crawler crawler, List<BasicModule> basicModules, BasicFittingConfig basicFittingConfig) {
        if (Objects.nonNull(crawler)) {
            crawler.setExitWhenComplete(basicFittingConfig.isExitWhenComplete());
            crawler.setEmptySleepTime(basicFittingConfig.getEmptySleepTime());
            crawler.setSpawnUrl(basicFittingConfig.isSpawnUrl());
            crawler.thread(basicFittingConfig.getThreadNum());
        }

        return crawler;
    }
}
