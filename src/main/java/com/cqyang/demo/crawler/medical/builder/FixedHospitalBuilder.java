package com.cqyang.demo.crawler.medical.builder;

import com.alibaba.fastjson.JSON;
import com.cqyang.demo.crawler.core.Crawler;
import com.cqyang.demo.crawler.medical.model.MedicalRegion;
import com.cqyang.demo.crawler.medical.util.EncryptUtil;
import com.cqyang.demo.crawler.medical.model.FixedHospital;
import com.cqyang.demo.crawler.medical.model.FixedHospitalRequest;
import com.cqyang.demo.crawler.medical.model.MedicalEncryptRequest;
import com.cqyang.demo.crawler.medical.model.enums.MedicalBizTypeEnum;
import com.cqyang.demo.crawler.medical.util.RegionUtil;
import com.cqyang.demo.crawler.model.CrawlerContext;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.model.HttpRequestBody;

import javax.script.ScriptException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class FixedHospitalBuilder extends MedicalBuilder<FixedHospital, FixedHospitalRequest> {

    @Override
    public int getType() {
        return MedicalBizTypeEnum.FIXED_HOSPITAL.getType();
    }

    @Override
    protected String getUrl() {
        return "https://fuwu.nhsa.gov.cn/ebus/fuwu/api/nthl/api/CommQuery/queryFixedHospital";
    }

    @Override
    protected String getMethod() {
        return "POST";
    }

    @Override
    protected MedicalEncryptRequest buildEncryptRequest(FixedHospitalRequest fixedHospitalRequest) throws ScriptException {
        return EncryptUtil.encryptFixedHospital(fixedHospitalRequest);
    }

    @Override
    protected FixedHospital buildPageListData(ScriptObjectMirror scriptObjectMirror) {
        FixedHospital fixedHospital = new FixedHospital();
        fixedHospital.setMedinsTypeName(String.valueOf(scriptObjectMirror.get("medinsTypeName")));
        fixedHospital.setMedinsNatu(String.valueOf(scriptObjectMirror.get("medinsNatu")));
        fixedHospital.setHospLv(String.valueOf(scriptObjectMirror.get("hospLv")));
        fixedHospital.setMedinsType(String.valueOf(scriptObjectMirror.get("medinsType")));
        fixedHospital.setUscc(String.valueOf(scriptObjectMirror.get("uscc")));
        fixedHospital.setOpenElec(String.valueOf(scriptObjectMirror.get("openElec")));
        fixedHospital.setBusinessLvEpc(String.valueOf(scriptObjectMirror.get("businessLvEpc")));
        fixedHospital.setLnt(String.valueOf(scriptObjectMirror.get("lnt")));
        fixedHospital.setBusinessLvCfc(String.valueOf(scriptObjectMirror.get("businessLvCfc")));
        fixedHospital.setBusinessLvMpc(String.valueOf(scriptObjectMirror.get("businessLvMpc")));
        fixedHospital.setMedinsLvName(String.valueOf(scriptObjectMirror.get("medinsLvName")));
        fixedHospital.setMedinsLv(String.valueOf(scriptObjectMirror.get("medinsLv")));
        fixedHospital.setMedinsName(String.valueOf(scriptObjectMirror.get("medinsName")));
        fixedHospital.setAddr(String.valueOf(scriptObjectMirror.get("addr")));
        fixedHospital.setMedinsCode(String.valueOf(scriptObjectMirror.get("medinsCode")));
        fixedHospital.setLat(String.valueOf(scriptObjectMirror.get("lat")));
        fixedHospital.setBusinessLvEbc(String.valueOf(scriptObjectMirror.get("businessLvEbc")));
        return fixedHospital;
    }

    @Override
    protected Map<String, Object> getHeader() {
        try {
            return EncryptUtil.getHeader();
        } catch (ScriptException e) {
            return null;
        }
    }

    @Override
    public void addRequest(Crawler crawler, CrawlerContext context) {
        List<MedicalRegion> medicalRegionList = RegionUtil.getMedicalRegionList();
        medicalRegionList.forEach(region -> {
            try {
                Request request = buildRequest(context);
                // body
                FixedHospitalRequest fixedHospitalRequest = new FixedHospitalRequest();
                fixedHospitalRequest.setRegnCode(region.getAreaCode());
                fixedHospitalRequest.setPageNum(1);
                fixedHospitalRequest.setPageSize(10);
                request.setRequestBody(HttpRequestBody.json(JSON.toJSONString(buildEncryptRequest(fixedHospitalRequest)), StandardCharsets.UTF_8.name()));
                // add
                crawler.addRequest(request);
            } catch (Exception e) {
                log.error("buildRequest fail, ", e);
            }
        });
    }


}
