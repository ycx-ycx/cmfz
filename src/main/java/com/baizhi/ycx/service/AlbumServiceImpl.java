package com.baizhi.ycx.service;


import com.baizhi.ycx.dao.AlbumDao;
import com.baizhi.ycx.entity.Album;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AlbumServiceImpl implements AlbumService{

    @Autowired
    AlbumDao albumDao;

    @Override
    public List<Album> selectAllAlbum() {
        return albumDao.selectAll();
    }

    @Override
    public Album selectById(String id) {
        return albumDao.selectByPrimaryKey(id);

    }
}
