package com.abc.shiro.config;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class CustomFilter extends AuthorizationFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        // 获取主体
        Subject subject = getSubject(request, response);
        // 获取角色
        String[] roles = (String [])mappedValue;
        if (roles == null || roles.length == 0) {
            return false;
        }
        // 判断用户是否拥有角色（拥有角色之一即通过校验）
        for (String role : roles) {
            if (subject.hasRole(role)) {
                return true;
            }
        }
        return false;
    }
}
