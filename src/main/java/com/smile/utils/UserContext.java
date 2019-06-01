package com.smile.utils;

import com.smile.model.sys.SysUser;

import java.util.Optional;

/**
 * @author smile
 */
public class UserContext {
    /**
     * 存放用户
     */
    private static ThreadLocal<SysUser> userInheritableThreadLocal = new InheritableThreadLocal<>();

    /**
     * 保存用户
     * @param sysUser
     */
    public static void setSysUser(SysUser sysUser){
        userInheritableThreadLocal.set(sysUser);
    }

    /**
     * 获取用户
     * @return
     */
    public static Optional<SysUser> getSysUser(){
        return Optional.ofNullable(userInheritableThreadLocal.get());
    }

    /**
     * 获取用户名
     * @return
     */
    public static Optional<String> getSysUsername(){
        return getSysUser().map(SysUser::getUsername);
    }

}
