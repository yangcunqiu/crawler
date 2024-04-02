package com.cqyang.demo.crawler.core;

import com.cqyang.demo.crawler.model.enums.CrawlerModuleCodeEnum;
import org.springframework.stereotype.Component;

@Component
public class BasicModule extends CrawlerModule{

    @Override
    protected String getModuleCode() {
        return CrawlerModuleCodeEnum.DEFAULT_BASIC.getCode();
    }
}
