package com.baizhi.ycx.controller;

import com.baizhi.ycx.dao.GuruDao;
import com.baizhi.ycx.entity.Guru;
import com.baizhi.ycx.service.GuruService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("guru")
public class GuruController {

    @Autowired
    GuruDao guruDao;
    @Autowired
    GuruService guruService;

    @RequestMapping("showAllGuru")
    public List<Guru> showAllGuru(){
        List<Guru> gurus = guruDao.selectAll();
        return gurus;
    }



    //前台
    //展示上师列表
    @RequestMapping("selectAllGuru")
    public Map selectAllGuru(){
        HashMap hashMap = new HashMap();

        List<Guru> gurus = guruService.selectAll();

        hashMap.put("status","200");
        hashMap.put("guru",gurus);

        return hashMap;
    }

}
