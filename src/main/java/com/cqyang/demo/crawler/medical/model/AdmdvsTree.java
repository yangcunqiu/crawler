package com.cqyang.demo.crawler.medical.model;

import lombok.Data;

import java.util.List;

/**
 * 医保局用到的地区code
 * <a href="https://fuwu.nhsa.gov.cn/ebus/fuwu/api/nthl/api/dic/queryAdmdvsTree">...</a>
 */
@Data
public class AdmdvsTree {
    private String code;
    private String prntCode;
    private String name;
    private String admdvsLv;
    private List<AdmdvsTree> list;
}
