package com.cqyang.demo.crawler.core;

import com.cqyang.demo.crawler.model.CrawlerContext;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Request;

import java.util.List;

/**
 * 不同场景添加任务的manage
 * @param <T>
 */
@Component
public abstract class CrawlerManage<T> {

    /**
     * 获取适用场景
     */
    public abstract Integer getScene();

    /**
     * 初始化必要的数据
     */
    public void initData() {}

    /**
     * 获取配置
     */
    protected abstract T getConfig(String configJson);

    /**
     * 添加任务
     */
    protected abstract void invoke0(Crawler crawler, String tag, T t, CrawlerContext context);

    /**
     * 添加任务
     * @param crawler 爬虫
     * @param tag 任务标识
     * @param configJson 配置json
     */
    public void addTask(Crawler crawler, String tag, String configJson, CrawlerContext context) {
        invoke0(crawler, tag, getConfig(configJson), context);
    }
}
