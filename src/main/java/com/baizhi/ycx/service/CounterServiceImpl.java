package com.baizhi.ycx.service;

import com.baizhi.ycx.dao.CounterDao;
import com.baizhi.ycx.dao.CourseDao;
import com.baizhi.ycx.entity.Counter;
import com.baizhi.ycx.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CounterServiceImpl implements CounterService{

    @Autowired
    CounterDao counterDao;
    @Override
    public Counter selectById(String id) {
        return counterDao.selectByPrimaryKey(id);
    }

    @Override
    public void insertCounter(Counter counter) {
        counterDao.insertSelective(counter);
    }

    @Override
    public void deleteCounter(String id) {
        counterDao.deleteByPrimaryKey(id);
    }

    @Override
    public void updateCountById(String id, String count) {
        counterDao.updateCount(id,count);
    }

}
