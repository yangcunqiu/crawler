package com.cqyang.demo.crawler.core;

import com.cqyang.demo.crawler.model.CrawlerContext;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.ResultItems;

import java.util.Optional;

public abstract class CrawlerModule {

    protected abstract String getModuleCode();

    protected CrawlerContext getCrawlerContext(Page page) {
        return Optional.ofNullable(page).map(Page::getRequest).map(Request::getExtras).map(extraMap -> (CrawlerContext)extraMap.get(CrawlerContext.EXTRA_KEY)).orElse(new CrawlerContext());
    }

    protected CrawlerContext getCrawlerContext(ResultItems resultItems) {
        return Optional.ofNullable(resultItems).map(ResultItems::getRequest).map(Request::getExtras).map(extraMap -> (CrawlerContext)extraMap.get(CrawlerContext.EXTRA_KEY)).orElse(new CrawlerContext());
    }
}
