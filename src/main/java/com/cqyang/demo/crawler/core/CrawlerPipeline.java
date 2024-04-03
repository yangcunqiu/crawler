package com.cqyang.demo.crawler.core;

import com.alibaba.fastjson.JSON;
import com.cqyang.demo.crawler.mapper.CollectDetailMapper;
import com.cqyang.demo.crawler.mapper.CollectListMapper;
import com.cqyang.demo.crawler.mapper.CollectRawLogMapper;
import com.cqyang.demo.crawler.model.CrawlerContext;
import com.cqyang.demo.crawler.model.enums.CollectStatusEnum;
import com.cqyang.demo.crawler.model.enums.CollectTypeEnum;
import com.cqyang.demo.crawler.model.po.CollectDetail;
import com.cqyang.demo.crawler.model.po.CollectList;
import com.cqyang.demo.crawler.model.po.CollectRawLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Objects;

@Component
@Slf4j
public abstract class CrawlerPipeline extends CrawlerModule implements Pipeline {

    @Autowired
    private CollectRawLogMapper rawLogMapper;
    @Autowired
    private CollectListMapper collectListMapper;
    @Autowired
    private CollectDetailMapper collectDetailMapper;

    @Override
    public void process(ResultItems resultItems, Task task) {
        CrawlerContext crawlerContext = getCrawlerContext(resultItems);
        String rawText = crawlerContext.getRawText();
        // 保存原始数据
        CollectRawLog rawLog = new CollectRawLog();
        rawLog.setCrawlerUniqueKey(crawlerContext.getUniqueKey());
        rawLog.setTaskUniqueKey(crawlerContext.getTaskUniqueKey());
        rawLog.setRequestUniqueKey(crawlerContext.getRequestUniqueKey());
        rawLog.setRawRequest(JSON.toJSONString(resultItems.getRequest()));
        rawLog.setRawResponse(rawText);
        rawLogMapper.insert(rawLog);

        // 保存采集数据
        saveCollect(resultItems, task, crawlerContext);

        // 执行各自的逻辑
        execute(resultItems, task, crawlerContext);
    }

    private void saveCollect(ResultItems resultItems, Task task, CrawlerContext crawlerContext) {
        // 持久化
        Integer taskType = crawlerContext.getCollectType();
        if (Objects.equals(taskType, CollectTypeEnum.COLLECT_LIST.getType())) {
            // 列表
            saveCollectList(crawlerContext);
        } else if (Objects.equals(taskType, CollectTypeEnum.COLLECT_DETAIL.getType())) {
            // 详情
            saveCollectDetail(crawlerContext);
        }
    }

    protected void saveCollectList(CrawlerContext crawlerContext) {
        CollectDetail collectDetail = new CollectDetail();
        collectDetail.setTaskUniqueKey(crawlerContext.getTaskUniqueKey());
        collectDetail.setTaskTag(crawlerContext.getTaskTag());
        collectDetail.setRequestUniqueKey(crawlerContext.getRequestUniqueKey());
        collectDetail.setCollectListId(collectDetail.getCollectListId());
        collectDetail.setUniqueKey(crawlerContext.getCollectUniqueKey());
        collectDetail.setKeyword(crawlerContext.getKeyword());
        collectDetail.setData(crawlerContext.getData());
        collectDetail.setStatus(CollectStatusEnum.SUCCESS_COLLECT.getStatus());
        collectDetailMapper.insert(collectDetail);
    }

    protected void saveCollectDetail(CrawlerContext crawlerContext) {
        CollectList collectList = new CollectList();
        collectList.setTaskUniqueKey(crawlerContext.getTaskUniqueKey());
        collectList.setTaskTag(crawlerContext.getTaskTag());
        collectList.setRequestUniqueKey(crawlerContext.getRequestUniqueKey());
        collectList.setUniqueKey(crawlerContext.getCollectUniqueKey());
        collectList.setKeyword(crawlerContext.getKeyword());
        collectList.setData(crawlerContext.getData());
        collectList.setStatus(CollectStatusEnum.SUCCESS_COLLECT.getStatus());
        collectListMapper.insert(collectList);
    }

    protected abstract void execute(ResultItems resultItems, Task task, CrawlerContext crawlerContext);
}
