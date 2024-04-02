package com.cqyang.demo.crawler.medical.util;

import com.alibaba.fastjson.JSON;
import com.cqyang.demo.crawler.medical.model.AdmdvsTree;
import com.cqyang.demo.crawler.medical.model.MedicalRegion;
import com.cqyang.demo.crawler.util.ClasspathFileReadUtil;
import org.apache.commons.collections4.CollectionUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class RegionUtil {

    public static List<MedicalRegion> medicalRegionList = new ArrayList<>();

    public static List<AdmdvsTree> getMedicalRegionTree() {
        InputStream inputStream = ClasspathFileReadUtil.getFileAsStream("medical/region.json");
        return JSON.parseArray(ClasspathFileReadUtil.getStringByInputStream(inputStream), AdmdvsTree.class);
    }

    public static List<MedicalRegion> getMedicalRegionList() {
        if (CollectionUtils.isEmpty(medicalRegionList)) {
            List<AdmdvsTree> admdvsTrees = getMedicalRegionTree();
            List<MedicalRegion> flatList = new ArrayList<>();
            for (AdmdvsTree admdvs : admdvsTrees) {
                flattenRegion(admdvs, flatList, new MedicalRegion());
            }
            medicalRegionList = flatList;
        }
        return medicalRegionList;
    }

    private static void flattenRegion(AdmdvsTree admdvs, List<MedicalRegion> flatList, MedicalRegion parentRegion) {
        MedicalRegion currentRegion = new MedicalRegion();
        // Copy parent information
        currentRegion.setProvinceCode(parentRegion.getProvinceCode());
        currentRegion.setProvinceName(parentRegion.getProvinceName());
        currentRegion.setCityCode(parentRegion.getCityCode());
        currentRegion.setCityName(parentRegion.getCityName());
        currentRegion.setAreaCode(parentRegion.getAreaCode());
        currentRegion.setAreaName(parentRegion.getAreaName());

        // Update parent information based on level
        switch (admdvs.getAdmdvsLv()) {
            case "1":
                currentRegion.setProvinceCode(admdvs.getCode());
                currentRegion.setProvinceName(admdvs.getName());
                break;
            case "2":
                currentRegion.setCityCode(admdvs.getCode());
                currentRegion.setCityName(admdvs.getName());
                break;
            case "3":
                currentRegion.setAreaCode(admdvs.getCode());
                currentRegion.setAreaName(admdvs.getName());
                break;
        }

        // Add to list if leaf node
        if (admdvs.getList() == null || admdvs.getList().isEmpty()) {
            flatList.add(currentRegion);
        } else {
            // Recursively process children
            for (AdmdvsTree child : admdvs.getList()) {
                flattenRegion(child, flatList, currentRegion);
            }
        }
    }
}
