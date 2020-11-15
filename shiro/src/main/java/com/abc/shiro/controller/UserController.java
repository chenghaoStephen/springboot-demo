package com.abc.shiro.controller;

import com.abc.shiro.entity.UserEntity;
import com.abc.shiro.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("UserController")
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 跳转到登录页
     */
    @RequestMapping(value = "login")
    public String login(){
        return "login";
    }

    /**
     * 认证失败界面
     */
    @RequestMapping(value = "usererror")
    public String error(){
        return "usererror";
    }

    /**
     * 登录，使用统一异常处理
     */
    @RequestMapping(value = "dologin")
    public String dologin(UserEntity userEntity) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userEntity.getUsername(), userEntity.getPassword());
        subject.login(token);
        // 调用check方法，没有权限，抛出异常
        subject.checkRole("admin");
        subject.checkPermission("user:update");
        // 以下两个方法判断用户是否具有权限
        if(subject.hasRole("admin")){
            System.out.println("拥有admin角色！");
        }
        if(subject.isPermitted("user:update")){
            System.out.println("拥有user:update权限！");
        }
        return "index";
    }

    @RequestMapping(value = "exit")
    public String exit() {
        Subject subject = SecurityUtils.getSubject();
        // 主体执行退出，清空session数据
        subject.logout();
        return "login";
    }

    /**
     * 测试角色权限校验（此种方式未触发统一异常处理，推荐使用注解方式）
     */
    @RequestMapping(value = "roles")
    public String roles() {
        System.out.println("拥有角色");
        return "index";
    }

    @RequestMapping(value = "custom")
    public String custom() {
        System.out.println("拥有角色");
        return "index";
    }

    @RequestMapping(value = "permissions")
    public String permissions() {
        System.out.println("拥有权限");
        return "index";
    }

    /**
     * 测试注解
     */
    @RequestMapping(value = "roles1")
    @RequiresRoles("user")
    public String roles1() {
        System.out.println("拥有角色");
        return "index";
    }

    @RequiresPermissions("user:select")
    @RequestMapping(value = "permissions1")
    public String permissions1() {
        System.out.println("拥有权限");
        return "index";
    }

    @RequestMapping(value = "roles2")
    @RequiresRoles("admin")
    public String roles2() {
        System.out.println("拥有角色");
        return "index";
    }

    @RequestMapping(value = "permissions2")
    @RequiresPermissions("user:update")
    public String permissions2() {
        System.out.println("拥有权限");
        return "index";
    }

}
