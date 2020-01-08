package com.baizhi.ycx.service;

import com.baizhi.ycx.dao.AdminDao;
import com.baizhi.ycx.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdminServiceImpl implements AdminService{

    @Autowired
    AdminDao adminDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Admin loginAdmin(Admin admin) {

        Admin admin1 = adminDao.selectOne(admin);
        return admin1;
    }
}
