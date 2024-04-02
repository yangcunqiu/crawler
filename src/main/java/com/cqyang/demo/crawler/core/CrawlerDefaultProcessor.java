package com.cqyang.demo.crawler.core;

import com.cqyang.demo.crawler.model.CrawlerContext;
import com.cqyang.demo.crawler.model.enums.CrawlerModuleCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;

/**
 * 默认processor
 */
@Component
@Slf4j
public class CrawlerDefaultProcessor extends CrawlerProcessor {

    @Override
    public String getModuleCode() {
        return CrawlerModuleCodeEnum.DEFAULT_PROCESSOR.getCode();
    }

    @Override
    protected void execute(Page page, CrawlerContext context) {
        log.info("CrawlerDefaultProcessor execute");
    }
}
