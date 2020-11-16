package com.abc.mybatisplus.controller;


import com.abc.mybatisplus.entity.User;
import com.abc.mybatisplus.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author stephen
 * @since 2020-11-16
 */
@RestController
@RequestMapping("/")
public class UserController {
    @Autowired
    private IUserService userService;

    @RequestMapping("/users")
    public List<User> users() {
        return userService.list();
    }

}
