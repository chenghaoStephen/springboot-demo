package com.abc.shiro.controller;

import com.abc.shiro.entity.UserEntity;
import com.abc.shiro.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("UserController")
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "login")
    public String login(){
        return "login";
    }

    @RequestMapping(value = "usererror")
    public String error(){
        return "usererror";
    }

    @RequestMapping(value = "dologin")
    public String dologin(UserEntity userEntity){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userEntity.getUsername(),userEntity.getPassword());
        try {
            subject.login(token);
            try {
                subject.checkRole("admin");
                subject.checkPermission("user:update");
                //理解非注解也可以做转跳拦截之类的动作
                if(subject.hasRole("admin")){
                    System.out.println("拥有admin角色！");
                }
                if(subject.isPermitted("user:update")){
                    System.out.println("拥有user:update权限！");
                }
                return "index";
            }catch (UnauthorizedException exception){
                System.out.println("角色授权或者权限授权失败！");
                return "error";
            }
        }catch (AuthenticationException e){
            System.out.println("认证失败！");
            return "usererror";
        }
    }

    @RequestMapping(value = "exit")
    public String exit(){
        Subject subject = SecurityUtils.getSubject();
        // 主体执行退出，清空session数据
        subject.logout();
        return "login";
    }

}
