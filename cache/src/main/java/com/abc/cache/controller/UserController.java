package com.abc.cache.controller;

import com.abc.cache.entity.User;
import com.abc.cache.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/info/{id}")
    public User getUserInfo(@PathVariable int id) {
        return userService.loadById(id);
    }

    @GetMapping("/save")
    public String saveUser(User user) {
        userService.saveUser(user);
        return "保存成功";
    }

    @GetMapping("/update")
    public String updateUser(User user) {
        userService.updateById(user);
        return "修改成功";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable int id) {
        userService.deleteById(id);
        return "删除成功";
    }
}
