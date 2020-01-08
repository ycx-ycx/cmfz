package com.baizhi.ycx.service;

import com.baizhi.ycx.entity.Album;
import com.baizhi.ycx.entity.Article;
import com.baizhi.ycx.entity.Banner;
import com.baizhi.ycx.entity.User;

import java.util.List;


public interface UserService {

    //登陆
    public User login(String phone, String password);

    //补充个人信息接口
    public void information(User user);

    //修改个人信息
    public void updateUser(User user);

    //随机展示（金刚）
    public List<User> selectRandom(String id);

    //一级页面
    //查5条轮播图
    public List<Banner> selectBanner();

    //查询专辑
    public List<Album> selectAlbum(String uid);

    //查询文章
    public List<Article> selectArticle(String uid);

}
