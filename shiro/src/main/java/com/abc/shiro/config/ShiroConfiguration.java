package com.abc.shiro.config;

import com.abc.shiro.realm.CustomRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

@Configuration
public class ShiroConfiguration {

    @Autowired
    private CustomRealm customRealm;

    /**
     * 配置ShiroFilter
     */
    public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") DefaultWebSecurityManager manager) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(manager);
        // 配置登录的url（认证失败自动跳转）和验证权限失败的url
        bean.setLoginUrl("/login");
        bean.setUnauthorizedUrl("/error");
        // 配置自定义的Filter

        // 配置访问权限
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/login", "anon"); // anon不认证
        filterChainDefinitionMap.put("/dologin", "anon");
        filterChainDefinitionMap.put("/roles1", "roles[admin]");
        filterChainDefinitionMap.put("/roles2", "roles[admin,user]"); // 同时具有admin，user角色
        filterChainDefinitionMap.put("/permission1", "perms[user:update]");
        filterChainDefinitionMap.put("/permission2", "perms[user:update,user:select]"); // 同时具有多个权限
        filterChainDefinitionMap.put("/**", "authc"); // authc需要认证，/**表示所有路径，放在最后
        bean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return bean;
    }

    /**
     * 配置核心类SecurityManager
     */
    @Bean(name="securityManager")
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        // 配置自定义Realm
        manager.setRealm(customRealm);
        // 配置加密算法（秘钥在自定义Realm时配置）
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("md5");
        matcher.setHashIterations(1);
        customRealm.setCredentialsMatcher(matcher);
        return manager;
    }

    /**
     * 开启注解支持@RequiresRoles、@RequiresPermissions
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

}
