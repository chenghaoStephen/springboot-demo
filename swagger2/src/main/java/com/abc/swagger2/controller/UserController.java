package com.abc.swagger2.controller;

import com.abc.swagger2.pojo.User;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@Api("user interfaces")
@RestController
@RequestMapping("/user")
public class UserController {

    @ApiOperation(value = "查询用户信息")
    @GetMapping(value = "/info/{id}")
    public User info(@PathVariable("id") Long id) {
        User user = new User();
        user.setId(id);
        return user;
    }

    @ApiOperation(value = "添加用户", produces="application/json")
    @ApiOperationSupport(ignoreParameters = {"age"}) //在请求参数中去除
    @PostMapping(value = "/add")
    public String add(User user) {
        return "{'id':10}";
    }

}
