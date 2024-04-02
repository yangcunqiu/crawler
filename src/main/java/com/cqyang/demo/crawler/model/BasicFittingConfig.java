package com.cqyang.demo.crawler.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BasicFittingConfig extends CrawlerFittingConfig{

    /**
     * 获取不到Request是否退出爬虫 (默认 true)
     */
    private boolean exitWhenComplete = true;
    /**
     * 每次获取新Request的等待时间 (默认 30000ms, 只有当 exitWhenComplete 为 false 时才有意义)
     */
    private long emptySleepTime = 30000;

    /**
     * 是否允许在执行任务过程中添加新的Request (默认 true)
     */
    private boolean spawnUrl = true;

    /**
     * 线程数 用来执行一次Request的线程数 (默认 1)
     */
    private int threadNum = 1;
}
