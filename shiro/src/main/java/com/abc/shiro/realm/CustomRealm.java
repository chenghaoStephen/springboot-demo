package com.abc.shiro.realm;

import com.abc.shiro.common.Const;
import com.abc.shiro.entity.UserEntity;
import com.abc.shiro.service.PermissionService;
import com.abc.shiro.service.RoleService;
import com.abc.shiro.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 自定义Realm
 */
@Component("realm")
public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;

    /**
     * 重写授权方法，进行权限校验时，从该处获取权限数据
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 从认证信息中拿出用户名
        String username = (String)principalCollection.getPrimaryPrincipal();
        // 从数据库获取角色、权限数据（可配置从缓存中获取）
        System.out.println("从数据库获取授权数据");
        Set<String> roleList = roleService.fetchRolesByUsername(username);
        Set<String> permissionList = permissionService.fetchPermissionsByUsername(username);
        // 封装权限信息并返回
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(roleList);
        authorizationInfo.setStringPermissions(permissionList);
        return authorizationInfo;
    }

    /**
     * 重写认证方法，调用subject.login时，从该处获取用户数据进行匹配
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 从认证信息中拿出用户名
        String username = (String)authenticationToken.getPrincipal();
        // 根据用户名获取用户信息
        UserEntity userEntity = userService.loadByUsername(username);
        if (userEntity == null) {
            return null;//没有用户信息
        }
        // 封装认证信息
        SimpleAuthenticationInfo authenticationInfo
                = new SimpleAuthenticationInfo(username, userEntity.getPassword(), getName());
        // 加密算法加盐
        authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(Const.AUTHEN_SALT + username));
        return authenticationInfo;
    }

    public static void main(String[] args) {
        Md5Hash md5Hash = new Md5Hash("123456", "salt_benzhu");
        System.out.println(md5Hash.toString());
    }
}
