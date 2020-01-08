package com.baizhi.ycx.dao;

import com.baizhi.ycx.entity.Counter;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface CounterDao extends Mapper<Counter>{

    //变更计数器
    public void updateCount(@Param("id") String id ,@Param("count") String count);
}