package com.abc.shiro.controller;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 异常处理类
 */
@ControllerAdvice
@ResponseBody
public class CustomExceptionHandler {
    @ExceptionHandler(Exception.class)
    public String custonException(Exception e) {
        return "***异常！***";
    }

    @ExceptionHandler(AuthenticationException.class)
    public String authenticationException(AuthenticationException e) {
        return "***认证失败！***";
    }

    @ExceptionHandler(UnauthorizedException.class)
    public String unauthorizedException(UnauthorizedException e) {
        return "***角色授权或者权限授权失败！***";
    }

}
