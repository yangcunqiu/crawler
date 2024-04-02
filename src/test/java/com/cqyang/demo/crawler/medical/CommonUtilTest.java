package com.cqyang.demo.crawler.medical;

import com.cqyang.demo.crawler.medical.model.AdmdvsTree;
import com.cqyang.demo.crawler.medical.model.MedicalRegion;
import com.cqyang.demo.crawler.medical.util.RegionUtil;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

public class CommonUtilTest {

    @Test
    public void testMedicalRegionTree() {
        List<AdmdvsTree> admdvsTrees = RegionUtil.getMedicalRegionTree();
        System.out.println(admdvsTrees);
    }

    @Test
    public void testMedicalRegion() {
        List<MedicalRegion> medicalRegionList = RegionUtil.getMedicalRegionList();
        System.out.println(medicalRegionList);
    }
}
