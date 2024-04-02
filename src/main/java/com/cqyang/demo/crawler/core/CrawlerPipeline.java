package com.cqyang.demo.crawler.core;

import com.cqyang.demo.crawler.model.CrawlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

@Component
@Slf4j
public abstract class CrawlerPipeline extends CrawlerModule implements Pipeline {

    @Override
    public void process(ResultItems resultItems, Task task) {
        CrawlerContext crawlerContext = getCrawlerContext(resultItems);
        log.info("CrawlerPipeline process");
        execute(resultItems, task, crawlerContext);
    }

    protected abstract void execute(ResultItems resultItems, Task task, CrawlerContext crawlerContext);
}
