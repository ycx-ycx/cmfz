package com.baizhi.ycx.service;

import com.baizhi.ycx.dao.ArticleDao;
import com.baizhi.ycx.entity.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    ArticleDao articleDao;

    @Override
    public String articleUploa(MultipartFile inputfile, String id, HttpServletRequest request) {
        //获取http
        String scheme = request.getScheme();
        //获取IP
        String localhost =null;
        try {
            localhost = InetAddress.getLocalHost().toString();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        //获取端口号
        int serverPort = request.getServerPort();
        //获取项目名
        String contextPath = request.getContextPath();


        //根据相对路径获取绝对路径
        String realPath = request.getServletContext().getRealPath("/upload/articleImg/");
        File file = new File(realPath);
        //创建文件
        if(!file.exists()){
            file.mkdir();
        }
        //获取文件名
        String filname = inputfile.getOriginalFilename();
        //防止图片发生覆盖，重新给图片命名
        String newName=new Date().getTime()+"-"+filname;

        String uri = scheme +"://"+ localhost.split("/")[1] + ":" + serverPort + contextPath + "/upload/articleImg/" + newName;
        //文件上传
        try {
            inputfile.transferTo(new File(realPath, newName));
            //修改轮播图得信息
        }catch(IOException e){
            e.printStackTrace();
        }
        return uri;
    }

    @Override
    public Article selectById(String id) {
        Article article = articleDao.selectByPrimaryKey(id);
        return article;
    }

    @Override
    public List<Article> selectAllArticle() {
        List<Article> articles = articleDao.selectAll();
        return articles;
    }
}