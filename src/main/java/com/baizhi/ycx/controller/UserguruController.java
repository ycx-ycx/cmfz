package com.baizhi.ycx.controller;

import com.baizhi.ycx.entity.User;
import com.baizhi.ycx.entity.Userguru;
import com.baizhi.ycx.service.UserguruService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("userGuru")
public class UserguruController {

    @Autowired
    UserguruService userguruService;

    //前台
    //添加关注上师
    @RequestMapping("insertGZ")
    public Map insert(HttpSession session, Userguru userguru){
        HashMap hashMap = new HashMap();

        User user = (User) session.getAttribute("user");

        userguru.setUid("3");
        userguruService.insert(userguru);
        hashMap.put("userguru",userguru);
        hashMap.put("status",200);

        return hashMap;
    }
}
