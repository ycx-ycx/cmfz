package com.baizhi.ycx.controller;

import com.baizhi.ycx.dao.AlbumDao;
import com.baizhi.ycx.entity.Album;
import com.baizhi.ycx.service.AlbumService;
import com.baizhi.ycx.service.ArticleService;
import com.baizhi.ycx.util.HttpUtil;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.*;

@RestController
@RequestMapping("album")
public class AlbumController {

    @Autowired
    AlbumDao albumDao;

    @Autowired
    AlbumService albumService;

    @RequestMapping("showAllAlbums")
    public Map showAllAlbums(Integer page,Integer rows){
        HashMap hashMap = new HashMap();
        int i = albumDao.selectCount(null);
        Integer total = i%rows==0?i/rows:i/rows+1;
        List<Album> albums = albumDao.selectByRowBounds(null, new RowBounds((page - 1) * rows, rows));
        hashMap.put("records",i);
        hashMap.put("total",total);
        hashMap.put("rows",albums);
        hashMap.put("page",page);
        return hashMap;
    }
    @RequestMapping("editAlbum")
    // oper 标示 album 数据 id 删除的id
    public Map editAlbum(String oper,Album album,String[] id){
        HashMap hashMap = new HashMap();
        // 添加逻辑
        if (oper.equals("add")) {
            String albumId = UUID.randomUUID().toString();
            album.setId(albumId);
            albumDao.insert(album);
            hashMap.put("albumId", albumId);
            // 修改逻辑
        } else if (oper.equals("edit")) {
            albumDao.updateByPrimaryKeySelective(album);
            hashMap.put("albumId", album.getId());
            // 删除
        } else {
            albumDao.deleteByIdList(Arrays.asList(id));
        }
        return hashMap;
    }
    @RequestMapping("uploadAlbum")
    public Map uploadAlbum(HttpSession session,MultipartFile cover, String albumId, HttpServletRequest request){
        HashMap hashMap = new HashMap();
        String realPath = session.getServletContext().getRealPath("/upload/albumImg/");
        File file = new File(realPath);
        if (!file.exists()){
            file.mkdirs();
        }
        // 网络路径
        String http = HttpUtil.getHttp(cover, request, "/upload/albumImg/");
        Album album = new Album();
        album.setId(albumId);
        album.setCover(http);
        albumDao.updateByPrimaryKeySelective(album);
        hashMap.put("status",200);
        return hashMap;
    }


    //前台
    //专辑详情
    @RequestMapping("selectById")
    public Map selectById(String id) {
        HashMap hashMap = new HashMap();
        Album album = albumService.selectById(id);
        hashMap.put("album",album);
        hashMap.put("status","200");
        return hashMap;
    }

}
