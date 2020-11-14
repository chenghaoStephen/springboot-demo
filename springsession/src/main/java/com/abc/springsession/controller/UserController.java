package com.abc.springsession.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RequestMapping("/user")
@RestController
public class UserController {

    /**
     * 使用Spring Session将Session信息存储在Redis上
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
