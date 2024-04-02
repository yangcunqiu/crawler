package com.cqyang.demo.crawler.core;

import com.cqyang.demo.crawler.model.CrawlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.Map;

/**
 * 所有爬虫都要走的Process
 */
@Slf4j
@Component
public abstract class CrawlerProcessor extends CrawlerModule implements PageProcessor {



    @Override
    public void process(Page page) {
        CrawlerContext crawlerContext = getCrawlerContext(page);


        log.info("CrawlerProcessor process");



        execute(page, crawlerContext);

        // 保存一下最原始的数据

    }

    protected abstract void execute(Page page, CrawlerContext context);


}
