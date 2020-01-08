package com.baizhi.ycx.service;

import com.baizhi.ycx.dao.UserDao;
import com.baizhi.ycx.dao.UserguruDao;
import com.baizhi.ycx.entity.User;
import com.baizhi.ycx.entity.Userguru;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserguruServiceImpl implements UserguruService{

    @Autowired
    UserguruDao userguruDao;

    @Override
    public void insert(Userguru userguru) {
        userguruDao.insert(userguru);
    }
}
