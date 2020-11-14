package com.abc.token.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 生成一个随机串作为token传到前端，前端保存，每次请求放入Header中
     */
    @RequestMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password) {
        // 登录
        String key = "token_" + UUID.randomUUID().toString();
        stringRedisTemplate.opsForValue().set(key, username, 3600, TimeUnit.SECONDS);
        return key;
    }

    @RequestMapping("/info")
    public String info(@RequestHeader String token) {
        return "当前登录的是：" + stringRedisTemplate.opsForValue().get(token);
    }
}
