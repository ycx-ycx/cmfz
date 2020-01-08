package com.baizhi.ycx.service;

import com.baizhi.ycx.entity.Article;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ArticleService {
    //文件上传
    public String articleUploa(MultipartFile inputfile, String id, HttpServletRequest request);



    //前台
    //查一个
    public Article selectById(String id);

    //查所有
    public List<Article> selectAllArticle();

}
