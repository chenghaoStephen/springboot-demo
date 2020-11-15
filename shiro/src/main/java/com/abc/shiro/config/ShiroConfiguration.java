package com.abc.shiro.config;

import com.abc.shiro.realm.CustomRealm;
import com.abc.shiro.session.CustomSessionDao;
import com.abc.shiro.session.CustomSessionManager;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfiguration {

    /**
     * 配置ShiroFilter
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(securityManager);
        // 配置登录的url（认证失败自动跳转）和验证权限失败的url
        bean.setLoginUrl("/login");
//        bean.setUnauthorizedUrl("/error");
        // 配置自定义的Filter
        Map<String, Filter> filterMap = new LinkedHashMap<>();
        filterMap.put("customFilter", new CustomFilter());
        bean.setFilters(filterMap);
        // 配置访问权限
        // 使用LinkedHashMap，按照顺序进行过滤，匹配上则不在继续向下匹配
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/login", "anon"); // anon不认证
        filterChainDefinitionMap.put("/dologin", "anon");
        filterChainDefinitionMap.put("/roles", "roles[admin,user]"); // 同时具有admin，user角色
        filterChainDefinitionMap.put("/custom", "customFilter[admin,user]"); // 具有admin，user角色之一
        filterChainDefinitionMap.put("/permissions", "perms[user:update,user:select]"); // 同时具有多个权限
        filterChainDefinitionMap.put("/**", "authc"); // authc需要认证，/**表示所有路径，放在最后
        bean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return bean;
    }

    /**
     * 配置核心类SecurityManager
     */
    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        // 配置自定义Realm
        CustomRealm customRealm = customRealm();
        manager.setRealm(customRealm);
        // 配置加密算法（秘钥在自定义Realm时配置）
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("md5");
        matcher.setHashIterations(1);
        customRealm.setCredentialsMatcher(matcher);
        // 配置自定义sessionManager
        manager.setSessionManager(sessionManager());
        return manager;
    }

    /**
     * 自定义Realm
     */
    @Bean
    public CustomRealm customRealm() {
        CustomRealm customRealm = new CustomRealm();
        return customRealm;
    }

    /**
     * 定义SessionManager
     */
    @Bean
    public DefaultWebSessionManager sessionManager() {
        CustomSessionManager sessionManager = new CustomSessionManager();
        sessionManager.setSessionDAO(sessionDao());
        return sessionManager;
    }

    /**
     * 自定义SessionDao
     */
    @Bean
    public SessionDAO sessionDao() {
        CustomSessionDao sessionDao = new CustomSessionDao();
        return sessionDao;
    }

    /**
     * Shiro生命周期处理器
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator(可选)和AuthorizationAttributeSourceAdvisor)即可实现此功能
     */
    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
        return authorizationAttributeSourceAdvisor;
    }

}
