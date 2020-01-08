package com.baizhi.ycx.service;

import com.baizhi.ycx.dao.CourseDao;
import com.baizhi.ycx.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CourseServiceImpl implements CourseService{

    @Autowired
    CourseDao courseDao;


    @Override
    public void insertCourse(Course course) {
        courseDao.insertSelective(course);
    }

    @Override
    public void deleteById(String id) {
        courseDao.deleteByPrimaryKey(id);
    }

    @Override
    public List<Course> selectByUid(String userId) {
        List<Course> courses = courseDao.selectByUid(userId);
        return courses;
    }

}
