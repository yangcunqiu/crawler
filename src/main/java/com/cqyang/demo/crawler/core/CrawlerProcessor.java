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
        log.info("CrawlerProcessor process");
        CrawlerContext crawlerContext = getCrawlerContext(page);
        String rawText = page.getRawText();
        crawlerContext.setRawText(rawText);

        // 拿到采集到的数据
        String data = parseData(page, crawlerContext);
        crawlerContext.setData(data);

        // 执行各自逻辑
        execute(page, crawlerContext);
    }

    protected void execute(Page page, CrawlerContext context) {}

    protected abstract String parseData(Page page, CrawlerContext context);
}
