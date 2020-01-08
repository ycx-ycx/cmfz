package com.baizhi.ycx.service;

import com.baizhi.ycx.dao.UserDao;
import com.baizhi.ycx.entity.Album;
import com.baizhi.ycx.entity.Article;
import com.baizhi.ycx.entity.Banner;
import com.baizhi.ycx.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService{

    @Autowired
    UserDao userDao;

    //修改个人信息
    @Override
    public void updateUser(User user) {
        userDao.updateByPrimaryKeySelective(user);
    }

    //随机展示（金刚）
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<User> selectRandom(String id) {
        return userDao.selectRandom(id);
    }

    //补充个人信息
    @Override
    public void information(User user) {
        userDao.information(user);
    }

    //登陆
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public User login(String phone, String password) {
        User login = userDao.login(phone, password);
        return login;
    }

    //查5条轮播图
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Banner> selectBanner() {
        return userDao.selectBanner();
    }

    //查询专辑
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Album> selectAlbum(String uid) {
        return userDao.selectAlbum(uid);
    }

    //查询文章
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Article> selectArticle(String uid) {
        return userDao.selectArticle(uid);
    }
}
