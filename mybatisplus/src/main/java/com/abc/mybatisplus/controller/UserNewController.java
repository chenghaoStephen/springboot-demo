package com.abc.mybatisplus.controller;


import com.abc.mybatisplus.entity.UserNew;
import com.abc.mybatisplus.service.IUserNewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author stephen
 * @since 2020-11-17
 */
@RestController
@RequestMapping("/user-new")
public class UserNewController {

    @Autowired
    private IUserNewService userNewService;

    @RequestMapping("/add")
    public void add() {
        UserNew user = new UserNew();
        user.setName("tom");
        user.setAge(20);
        user.setEmail("tom@163.com");
//        // 手动添加数据
//        user.setCreateTime(LocalDateTime.now());
//        user.setUpdateTime(LocalDateTime.now());
        if (userNewService.save(user)) {
            userNewService.list().forEach(System.out::println);
        } else {
            System.out.println("添加数据失败");
        }
    }
}
