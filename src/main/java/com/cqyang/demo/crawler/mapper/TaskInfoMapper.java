package com.cqyang.demo.crawler.mapper;

import com.cqyang.demo.crawler.model.po.TaskInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TaskInfoMapper {

    void insert(TaskInfo taskInfo);
}
