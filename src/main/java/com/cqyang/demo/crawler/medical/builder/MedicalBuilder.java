package com.cqyang.demo.crawler.medical.builder;

import com.cqyang.demo.crawler.core.Crawler;
import com.cqyang.demo.crawler.core.CrawlerRequestBuilder;
import com.cqyang.demo.crawler.medical.model.MedicalEncryptRequest;
import com.cqyang.demo.crawler.medical.model.MedicalPageResponse;
import com.cqyang.demo.crawler.model.CrawlerContext;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import org.apache.commons.collections4.MapUtils;
import us.codecraft.webmagic.Request;

import javax.script.ScriptException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class MedicalBuilder<T, R> extends CrawlerRequestBuilder {

    protected abstract MedicalEncryptRequest buildEncryptRequest(R r) throws ScriptException;

    protected abstract T buildPageListData(ScriptObjectMirror scriptObjectMirror);

    public MedicalPageResponse<T> buildPageResponse(ScriptObjectMirror scriptObjectMirror) {
        MedicalPageResponse<T> medicalPageResponse = new MedicalPageResponse<>();
        medicalPageResponse.setStartRow((int) scriptObjectMirror.get("startRow"));
        medicalPageResponse.setPrePage((int) scriptObjectMirror.get("prePage"));
        medicalPageResponse.setHasNextPage((boolean) scriptObjectMirror.get("hasNextPage"));
        medicalPageResponse.setNextPage((int) scriptObjectMirror.get("nextPage"));
        medicalPageResponse.setPageSize((int) scriptObjectMirror.get("pageSize"));
        medicalPageResponse.setEndRow((int) scriptObjectMirror.get("endRow"));
        medicalPageResponse.setPageNum((int) scriptObjectMirror.get("pageNum"));
        medicalPageResponse.setNavigatePages((int) scriptObjectMirror.get("navigatePages"));
        medicalPageResponse.setTotal((int) scriptObjectMirror.get("total"));
        medicalPageResponse.setNavigateFirstPage((int) scriptObjectMirror.get("navigateFirstPage"));
        medicalPageResponse.setPages((int) scriptObjectMirror.get("pages"));
        medicalPageResponse.setSize((int) scriptObjectMirror.get("size"));
        medicalPageResponse.setLastPage((boolean) scriptObjectMirror.get("isLastPage"));
        medicalPageResponse.setHasPreviousPage((boolean) scriptObjectMirror.get("hasPreviousPage"));
        medicalPageResponse.setNavigateLastPage((int) scriptObjectMirror.get("navigateLastPage"));
        medicalPageResponse.setFirstPage((boolean) scriptObjectMirror.get("isFirstPage"));
        // list
        List<T> list = new ArrayList<>();
        Object listObject = scriptObjectMirror.get("list");
        if (listObject instanceof ScriptObjectMirror) {
            ScriptObjectMirror listScriptObjectMirror = (ScriptObjectMirror) listObject;
            List<Object> values = new ArrayList<>(listScriptObjectMirror.values());
            for (Object value : values) {
                if (value instanceof ScriptObjectMirror) {
                    list.add(buildPageListData((ScriptObjectMirror) value));
                }
            }
        }
        medicalPageResponse.setList(list);
        return medicalPageResponse;
    }

    public abstract void addRequest(Crawler crawler, CrawlerContext context);

}
