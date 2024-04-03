package com.cqyang.demo.crawler.mapper;

import com.cqyang.demo.crawler.model.po.CollectRawLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CollectRawLogMapper {

    void insert(CollectRawLog rawLog);
}
