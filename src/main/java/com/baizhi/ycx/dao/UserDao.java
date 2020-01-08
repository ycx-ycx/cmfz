package com.baizhi.ycx.dao;

import com.baizhi.ycx.entity.*;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserDao extends Mapper<User> {

    //按时间和性别查询用户活跃度
    public Integer selectByCreateDate(@Param("sex")  String sex,@Param("day") Integer day);
    //按性别和地区查
    public List<UserDto> selectByLocation(String sex);



    //前台
    //登陆
    public User login(@Param("phone") String phone,@Param("password") String password);

    //补充个人信息接口
    public void information(User user);

    //随机展示（金刚道友）
    public List<User> selectRandom(String id);

    //一级页面
    //查5条轮播图
    public List<Banner> selectBanner();

    //查询专辑
    public List<Album> selectAlbum(String uid);

    //查询文章
    public List<Article> selectArticle(String uid);

}
