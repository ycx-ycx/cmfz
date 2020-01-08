package com.baizhi.ycx.dao;


import com.baizhi.ycx.entity.Course;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface CourseDao extends Mapper<Course> {

    //根据用户id查询对应功课
    public List<Course> selectByUid(String userId);

}
