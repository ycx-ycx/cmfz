package com.baizhi.ycx.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.excel.EasyExcel;
import com.baizhi.ycx.dao.AlbumDao;
import com.baizhi.ycx.dao.ArticleDao;
import com.baizhi.ycx.dao.BannerDao;
import com.baizhi.ycx.dao.UserDao;
import com.baizhi.ycx.entity.*;
import com.baizhi.ycx.service.AlbumService;
import com.baizhi.ycx.service.ArticleService;
import com.baizhi.ycx.service.UserService;
import com.baizhi.ycx.util.SmsUtils;
import io.goeasy.GoEasy;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserDao userDao;

    @Autowired
    BannerDao bannerDao;

    @Autowired
    AlbumDao albumDao;

    @Autowired
    ArticleDao articleDao;

    @Autowired
    UserService userService;

    @Autowired
    AlbumService albumService;

    @Autowired
    ArticleService articleService;


    Jedis jedis = new Jedis("192.168.5.15",6379);//前台

    @RequestMapping("imageUpload")
    public void imageUpload(){
        List<User> users = userDao.selectAll();
        String url="F:\\后期项目\\day7-poiEasyExcel\\实例\\"+new Date().getTime()+".xls";
        EasyExcel.write(url, User.class)
                .sheet("用户")
                .doWrite(users);
    }

    @RequestMapping("leadExcel")
    public void leadExcel(){
        String url = "F:\\后期项目\\day7-poiEasyExcel\\实例\\1578131322565.xls";
        EasyExcel.read(url,User.class,new UserListener()).sheet().doRead();
    }

    @RequestMapping("queryByPage")
    public Map queryByPage(Integer page,Integer rows){
        HashMap map = new HashMap();
        int totalCount = userDao.selectCount(null);
        int totalPage=totalCount%rows==0?totalCount/rows:totalCount/rows+1;
        List<User> users = userDao.selectByRowBounds(null, new RowBounds((page - 1) * rows, rows));
        for (User user : users) {
            System.out.println(user);
        }
        map.put("records",totalCount);
        map.put("total",totalPage);
        map.put("rows",users);
        map.put("page",page);
        return map;

    }

    @RequestMapping("showUserCount")
    public Map showUserCount(){
        HashMap hashMap = new HashMap();
        ArrayList manList = new ArrayList();

        manList.add(userDao.selectByCreateDate("男",1));
        manList.add(userDao.selectByCreateDate("男",7));
        manList.add(userDao.selectByCreateDate("男",30));
        manList.add(userDao.selectByCreateDate("男",365));
        ArrayList womenList = new ArrayList();
        womenList.add(userDao.selectByCreateDate("女",1));
        womenList.add(userDao.selectByCreateDate("女",7));
        womenList.add(userDao.selectByCreateDate("女",30));
        womenList.add(userDao.selectByCreateDate("女",365));

        hashMap.put("man",manList);
        hashMap.put("women",womenList);

        return hashMap;

    }

    @RequestMapping("showUserLocation")
    public Map showUserLocation(){
        HashMap hashMap = new HashMap();

        List<UserDto> manDto = userDao.selectByLocation("男");
        System.out.println(manDto);
        List<UserDto> womenDto = userDao.selectByLocation("女");
        System.out.println(womenDto);
        hashMap.put("man",manDto);
        hashMap.put("women",womenDto);


        return hashMap;
    }

    @RequestMapping("save")
    public void save(String oper,String id,User user){
        if("add".equals(oper)){
            user.setId(UUID.randomUUID().toString());
            user.setRigest_date(new Date());
            System.out.println(user);
            userDao.insert(user);
            GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-1f3d26f67d504cfcb47a5830274af472");
            Map map = showUserCount();
            String ss = JSONUtils.toJSONString(map);
            System.out.println(ss);
            goEasy.publish("cmfz", ss);
        }else if("del".equals(oper)){
            userDao.deleteByPrimaryKey(id);
            GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-1f3d26f67d504cfcb47a5830274af472");
            Map map = showUserCount();
            String ss = JSONUtils.toJSONString(map);
            System.out.println(ss);
            goEasy.publish("cmfz", ss);
        }

    }


    //前台
    //登陆
    @RequestMapping("login")
    public Map login(String phone,String password){
        HashMap hashMap = new HashMap();
        User login = userService.login(phone, password);
        if (login!=null) {
            hashMap.put("status", "-200");
        }else {
            hashMap.put("user", login);
            hashMap.put("status","200");
        }
        return  hashMap;
    }

    //发送验证码
    @RequestMapping("code")
    public Map sendCode(String phone) {
        HashMap hashMap = new HashMap();
        try {
            String s = UUID.randomUUID().toString();
            String substring = "ycxxcy";
            SmsUtils.send(phone, substring);

            //将验证码填进redis
            jedis.set("code", substring);
            jedis.setex("code", 120, substring);
            hashMap.put("status", "200");
            hashMap.put("message", "短信发送成功");
        }catch (Exception e){
            hashMap.put("status", "-200");
            hashMap.put("message", "短信未发送");
        }
        return hashMap;
    }

    //注册
    @RequestMapping("reigser")
    public Map reigser(String codes){
        HashMap hashMap = new HashMap();

        String s = jedis.get("code");
        System.out.println(s);

        if (codes.equals(s)){
            hashMap.put("status",200);
            hashMap.put("message","注册成功");
        }else {
            hashMap.put("status",-200);
            hashMap.put("message","注册失败");
        }
        return hashMap;
    }

    //补充个人信息接口
    @RequestMapping("information")
    public Map information(User user){
        HashMap hashMap = new HashMap();

        userService.information(user);
        hashMap.put("status","200");
        hashMap.put("User",user);

        return hashMap;
    }

    //修改个人信息
    @RequestMapping("updateUser")
    public Map updateUser(User user) {
        HashMap hashMap = new HashMap();

        try {
            userService.updateUser(user);
            hashMap.put("status", "200");
        } catch (Exception e) {
            e.printStackTrace();
            hashMap.put("status", "-200");
        }
        return hashMap;
    }

    //金刚道友
    @RequestMapping("selectRandom")
    public Map selectRandom(HttpSession session) {
        HashMap hashMap = new HashMap();
        User users = (User) session.getAttribute("user");
        try {

            List<User> user = userService.selectRandom("1");
            hashMap.put("user", user);
            hashMap.put("status", "200");
        } catch (Exception e) {
            e.printStackTrace();
            hashMap.put("status", "-200");
        }
        return hashMap;
    }

    //一级页面
    //查5条轮播图
    //查询专辑
    //查询文章
    @RequestMapping("selectAll")
    public Map selectBanner(String uid, String type, String sub_type) {

        HashMap hashMap = new HashMap();

        if ("all".equals(type)) {
            List<Banner> banners = userService.selectBanner();
            List<Album> albums = userService.selectAlbum(uid);

            hashMap.put("banners", banners);
            hashMap.put("albums", albums);
            hashMap.put("status", "200");

        } else if ("wen ".equals(type)) {
            List<Album> albums = albumService.selectAllAlbum();

            hashMap.put("albums", albums);
            hashMap.put("status", "200");

        } else if ("si".equals(type)) {
            if ("ssyj".equals(sub_type)) {

                List<Article> articles = userService.selectArticle(uid);

                hashMap.put("articles", articles);
                hashMap.put("status", "200");

            } else {
                List<Article> articles = articleService.selectAllArticle();

                hashMap.put("articles", articles);
                hashMap.put("status", "200");
            }
        }
        return hashMap;
    }
}
