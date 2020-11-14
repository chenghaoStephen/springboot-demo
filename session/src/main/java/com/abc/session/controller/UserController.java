package com.abc.session.controller;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RequestMapping("/user")
@RestController
public class UserController {

    /**
     * 重启应用后，session消失，证明session由tomcat管理
     * 第一次访问接口时，Response Headers中会返回一个Set Cookie，设置Session
     * 再次访问时，会把Cookie:JSESSIONID加入到Request Headers中
     * 后端根据sessionId查询session信息
     */
    @RequestMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session) {
        // 登录
        session.setAttribute("login_user", username);
        return "登录成功";
    }

    @RequestMapping("/info")
    public String info(HttpSession session) {
        return "当前登录的是：" + session.getAttribute("login_user");
    }
}
