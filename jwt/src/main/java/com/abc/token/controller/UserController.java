package com.abc.token.controller;

import com.abc.token.interceptor.LoginInterceptor;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RequestMapping("/user")
@RestController
public class UserController {

    /**
     * 配置JWT的加密算法，秘钥，数据
     */
    @RequestMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password) {
        // 登录
        Algorithm algorithm = Algorithm.HMAC256(LoginInterceptor.JWT_KEY);
        String token = JWT.create()
                .withClaim("username", username)
                .withExpiresAt(new Date(System.currentTimeMillis() + 3600000)) // 设置过期时间
                .sign(algorithm);
        return token;
    }

    /**
     * 解析token，能够解析则认证通过；解析失败，要捕获异常
     */
    @RequestMapping("/info")
    public String info(@RequestAttribute String username) {
        return "当前登录：" + username;
    }
}
