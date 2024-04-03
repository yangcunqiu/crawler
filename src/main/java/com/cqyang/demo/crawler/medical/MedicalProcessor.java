package com.cqyang.demo.crawler.medical;

import com.alibaba.fastjson.JSON;
import com.cqyang.demo.crawler.core.CrawlerProcessor;
import com.cqyang.demo.crawler.core.CrawlerRequestBuilder;
import com.cqyang.demo.crawler.medical.builder.MedicalBuilder;
import com.cqyang.demo.crawler.medical.model.MedicalEncryptResponse;
import com.cqyang.demo.crawler.medical.model.MedicalPageResponse;
import com.cqyang.demo.crawler.medical.util.EncryptUtil;
import com.cqyang.demo.crawler.model.CrawlerContext;
import com.cqyang.demo.crawler.model.enums.CrawlerModuleCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;

import java.util.Map;

@Slf4j
@Component
public class MedicalProcessor extends CrawlerProcessor {

    @Override
    public String getModuleCode() {
        return CrawlerModuleCodeEnum.MEDICAL_PROCESSOR.getCode();
    }


    @Override
    protected String parseData(Page page, CrawlerContext context) {
        String rawText = context.getRawText();
        if (StringUtils.isBlank(rawText)) {
            return null;
        }
        try {
            MedicalEncryptResponse medicalEncryptResponse = JSON.parseObject(rawText, MedicalEncryptResponse.class);

            MedicalBuilder<?, ?> builder = context.getBuilder();
            MedicalPageResponse<?> decrypt = EncryptUtil.decrypt(medicalEncryptResponse, builder);

            return JSON.toJSONString(decrypt);
        } catch (Exception e) {
            log.error("execute fail, ", e);
            return null;
        }
    }

    @Override
    public Site getSite() {
        return Site.me()
                .setCharset("utf-8")
                .setTimeOut(3000)
                .setRetryTimes(3)
                .setRetrySleepTime(1000);
    }
}
