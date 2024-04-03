package com.cqyang.demo.crawler.mapper;

import com.cqyang.demo.crawler.model.po.CollectDetail;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CollectDetailMapper {

    void insert(CollectDetail collectDetail);
}
