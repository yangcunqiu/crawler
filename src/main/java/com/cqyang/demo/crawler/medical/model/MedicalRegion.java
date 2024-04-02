package com.cqyang.demo.crawler.medical.model;

import lombok.Data;

/**
 * 地区查询对象
 */
@Data
public class MedicalRegion {
    private String provinceCode;
    private String provinceName;
    private String cityCode;
    private String cityName;
    private String areaCode;
    private String areaName;
}
