package com.cqyang.demo.crawler.medical;

import com.alibaba.fastjson.JSON;
import com.cqyang.demo.crawler.core.CrawlerPipeline;
import com.cqyang.demo.crawler.medical.model.MedicalPageResponse;
import com.cqyang.demo.crawler.model.CrawlerContext;
import com.cqyang.demo.crawler.model.enums.CrawlerModuleCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;

@Slf4j
@Component
public class MedicalPipeline extends CrawlerPipeline {
    @Override
    protected String getModuleCode() {
        return CrawlerModuleCodeEnum.MEDICAL_PIPELINE.getCode();
    }


    @Override
    protected void saveCollectList(CrawlerContext crawlerContext) {
        String data = crawlerContext.getData();
        MedicalPageResponse<?> medicalPageResponse = JSON.parseObject(data, MedicalPageResponse.class);
        int total = medicalPageResponse.getTotal();



    }

    @Override
    protected void saveCollectDetail(CrawlerContext crawlerContext) {
        super.saveCollectDetail(crawlerContext);
    }

    @Override
    protected void execute(ResultItems resultItems, Task task, CrawlerContext crawlerContext) {
        log.info("MedicalPipeline process");
    }

//    @Override
//    public void process(ResultItems resultItems, Task task) {
//        log.info("MedicalPipeline process");
//        MedicalPageResponse<?> page = resultItems.get("page");
//        log.info("page: {}", JSON.toJSONString(page));
//    }
}
