package com.cqyang.demo.crawler.mapper;

import com.cqyang.demo.crawler.model.po.CollectList;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CollectListMapper {

    void insert(CollectList collectList);
}
