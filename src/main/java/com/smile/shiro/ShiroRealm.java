package com.smile.shiro;

import com.smile.model.sys.SysPerm;
import com.smile.model.sys.SysRole;
import com.smile.model.sys.SysUser;
import com.smile.shiro.token.JwtProperties;
import com.smile.shiro.token.JwtToken;
import com.smile.shiro.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * TODO
 *
 * @author shimingen
 * @date 2019/5/30 17:49
 */
@Service
@AllArgsConstructor
public class ShiroRealm extends AuthorizingRealm {

    private final JwtProperties jwtProperties;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 用户名信息验证
     * @param auth
     * @return
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) {
        String token = (String) auth.getCredentials();
        SysUser sysUser = JwtUtil.verifyAndGetUser(token, jwtProperties.getBase64Secret());
        if (sysUser == null) {
            throw new AuthenticationException("认证失败");
        }
        return new SimpleAuthenticationInfo(token, token, "shiroRealm");
    }

    /**
     * 检查用户权限
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SysUser sysUser = JwtUtil.verifyAndGetUser(principals.toString(), jwtProperties.getBase64Secret());
        if (Objects.isNull(sysUser)){
            throw new UnauthorizedException("授权失败");
        }
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //获取角色列表
        List<SysRole> roleList = sysUser.getRoleList();
        List<String> roles = roleList.stream()
                .map(SysRole::getRoleName)
                .collect(Collectors.toList());
        simpleAuthorizationInfo.addRoles(roles);
        //获取权限列表
        List<String> perms = roleList.stream()
                .map(SysRole::getPermList)
                .map(sysPerms -> sysPerms.toArray(new SysPerm[0]))
                .flatMap(Arrays::stream)
                .map(SysPerm::getPerm)
                .collect(Collectors.toList());
        simpleAuthorizationInfo.addStringPermissions(perms);
        return simpleAuthorizationInfo;
    }
}
