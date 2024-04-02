package com.cqyang.demo.crawler.core;

import com.cqyang.demo.crawler.model.CrawlerContext;
import com.cqyang.demo.crawler.util.SnowflakeIdUtil;
import org.apache.commons.collections4.MapUtils;
import us.codecraft.webmagic.Request;

import java.util.HashMap;
import java.util.Map;

public abstract class CrawlerRequestBuilder {

    public abstract int getType();

    protected abstract String getUrl();

    protected abstract String getMethod();

    protected abstract Map<String, Object> getHeader();

    protected Request buildRequest(CrawlerContext context) {
        Request request = new Request();
        request.setMethod(getMethod());
        request.setUrl(getUrl());

        Map<String, Object> header = getHeader();
        if (MapUtils.isNotEmpty(header)) {
            header.forEach((k, v) -> request.addHeader(k, String.valueOf(v)));
        }

        // 为每次请求生成唯一key
        Long requestUniqueKey = SnowflakeIdUtil.nextId();
        context.setUniqueKey(requestUniqueKey);

        // 扩展参数
        Map<String, Object> extraMap = new HashMap<>();
        extraMap.put("context", context);
        request.setExtras(extraMap);
        return request;
    }
}
