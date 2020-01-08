package com.baizhi.ycx.controller;

import com.baizhi.ycx.code.CreateValidateCode;
import com.baizhi.ycx.entity.Admin;
import com.baizhi.ycx.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @ResponseBody
    @RequestMapping("/login")
    public String login(Admin admin, String code, HttpSession session){
        Object code1 = session.getAttribute("code");
        String message = null;

        Admin admin1 = adminService.loginAdmin(admin);

        if (admin!=null){
            if (code1.equals(code)){
                session.setAttribute("admin", admin1);
                return message = null;
            }else {
                return message = "验证码不正确";
            }
        }else {
            return message = "账户或密码不正确";
        }
    }

    //验证码
    @RequestMapping("/code")
    public void login(HttpServletResponse response, HttpSession session) throws IOException {
        CreateValidateCode createValidateCode = new CreateValidateCode();
        String code = createValidateCode.getCode();
        createValidateCode.write(response.getOutputStream());
        session.setAttribute("code",code);
    }
}
