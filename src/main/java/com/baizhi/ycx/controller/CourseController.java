package com.baizhi.ycx.controller;

import com.baizhi.ycx.entity.Course;
import com.baizhi.ycx.entity.User;
import com.baizhi.ycx.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
@RequestMapping("course")
public class CourseController {

    //前台
    @Autowired
    CourseService courseService;

    //根据用户id查功课
    @RequestMapping("selectByUid")
    public Map selectByUid(String userId){
        HashMap hashMap = new HashMap();

        List<Course> course = courseService.selectByUid(userId);
        hashMap.put("course",course);
        hashMap.put("status","200");

        return hashMap;
    }

    //添加功课
    @RequestMapping("insertCourse")
    public Map insertCourse(Course course, HttpSession session) {
        User user = (User) session.getAttribute("user");
        HashMap hashMap = new HashMap();

        course.setId(UUID.randomUUID().toString().replace("-", ""));
        course.setUserId("1");
        course.setCreateDate(new Date());

        courseService.insertCourse(course);

        hashMap.put("course", course);
        hashMap.put("status", "200");

        return hashMap;
    }

    //删除功课
    @RequestMapping("deleteCourse")
    public Map deleteCourse(String id) {
        HashMap hashMap = new HashMap();

        courseService.deleteById(id);

        hashMap.put("id", id);
        hashMap.put("status", "200");

        return hashMap;
    }

}
