package com.baizhi.ycx.controller;

import com.baizhi.ycx.dao.BannerDao;
import com.baizhi.ycx.entity.Banner;
import com.baizhi.ycx.util.HttpUtil;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.net.UnknownHostException;
import java.util.*;

@RestController
@RequestMapping("banner")
public class BannerController {

    @Autowired
    BannerDao bannerDao;

    @RequestMapping("showAllBanners")
    public Map showAllBanners(Integer page, Integer rows) {
        HashMap hashMap = new HashMap();
        int i = bannerDao.selectCount(null);
        Integer total = i%rows==0?i/rows:i/rows+1;
        List<Banner> banners = bannerDao.selectByRowBounds(null, new RowBounds((page - 1) * rows, rows));
        hashMap.put("records",i);
        hashMap.put("total",total);
        hashMap.put("rows",banners);
        hashMap.put("page",page);
        return hashMap;
    }

    @RequestMapping("editBanner")
    public Map editBanner(String oper, Banner banner, String[] id) {
        HashMap hashMap = new HashMap();
        // 添加逻辑
        if (oper.equals("add")) {
            String bannerId = UUID.randomUUID().toString();
            banner.setId(bannerId);
            bannerDao.insert(banner);
            hashMap.put("bannerId", bannerId);
            // 修改逻辑
        } else if (oper.equals("edit")) {
            bannerDao.updateByPrimaryKeySelective(banner);
            hashMap.put("bannerId", banner.getId());
            // 删除
        } else {
            bannerDao.deleteByIdList(Arrays.asList(id));
        }
        return hashMap;
    }

    @RequestMapping("uploadBanner")
    // MultipartFile url(上传的文件),String bannerId(轮播图Id 更新使用)
    public Map uploadBanner(MultipartFile url, String bannerId, HttpSession session, HttpServletRequest request) throws UnknownHostException {
        /*
            1. 相对路径 : /upload/img/XXX.jpg 优:方便处理 缺:耦合度较高,在网络环境下难以定位
            2. 网络路径 : http://localhost:9999/cmfz/upload/img/XXX.jpg 缺:处理麻烦(需要程序员手动处理) 优:定位,精准度较高
         */
        HashMap hashMap = new HashMap();
        // 获取真实路径
        String realPath = session.getServletContext().getRealPath("/upload/img/");
        // 判断文件夹是否存在
        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String http = HttpUtil.getHttp(url, request, "/upload/img/");
        // 文件上传 工具类完成
        // 更新数据库信息
        Banner banner = new Banner();
        banner.setId(bannerId);
        banner.setUrl(http);
        bannerDao.updateByPrimaryKeySelective(banner);
        hashMap.put("status", 200);
        return hashMap;
    }
}
