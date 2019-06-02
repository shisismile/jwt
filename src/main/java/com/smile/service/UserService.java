package com.smile.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smile.handler.InvalidCaptchaException;
import com.smile.model.sys.SysUser;
import com.smile.param.LoginUserParam;
import com.smile.param.Result;
import com.smile.repository.sys.SysUserMapper;
import com.smile.shiro.token.JwtToken;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author smile
 */
@Service
@AllArgsConstructor
public class UserService extends ServiceImpl<SysUserMapper, SysUser> {
    private final SysUserMapper sysUserMapper;
    private final CaptchaService captchaService;

    /**
     * 通过用户名查询用户
     *
     * @param username
     * @return
     */
    public SysUser findSysUserByUsername(String username) {
        return sysUserMapper.findSysUserByUsername(username);
    }

    /**
     * 获取用户登录token
     * @param loginUserParam
     * @return
     */
    public Result<JwtToken> getToken(LoginUserParam loginUserParam) {
        Boolean check = captchaService.check(loginUserParam.getSignature(), loginUserParam.getCaptcha());
        if(!check){
            throw new InvalidCaptchaException("验证码验证失败!");
        }


        return null;
    }
}
