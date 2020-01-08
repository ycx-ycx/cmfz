package com.baizhi.ycx.service;

import com.baizhi.ycx.dao.GuruDao;
import com.baizhi.ycx.entity.Guru;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuruServiceImpl implements GuruService{

    @Autowired
    GuruDao guruDao;

    @Override
    public List<Guru> selectAll() {
        List<Guru> gurus = guruDao.selectAll();
        return gurus;
    }

}
