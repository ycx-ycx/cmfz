package com.baizhi.ycx.service;

import com.baizhi.ycx.entity.Album;

import java.util.List;

public interface AlbumService {

    //前台
    //查所有
    public List<Album> selectAllAlbum();

    public Album selectById(String id);
}