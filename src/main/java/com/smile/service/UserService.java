package com.smile.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smile.model.sys.SysUser;
import com.smile.param.LoginUserParam;
import com.smile.param.Result;
import com.smile.param.UserEnum;
import com.smile.repository.sys.SysUserMapper;
import com.smile.shiro.SecurityConsts;
import com.smile.shiro.token.JwtProperties;
import com.smile.shiro.token.JwtToken;
import com.smile.shiro.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.smile.param.ResultStatusEnum.*;
import static com.smile.param.UserEnum.LOCK;

/**
 * @author smile
 */
@Service
@AllArgsConstructor
public class UserService extends ServiceImpl<SysUserMapper, SysUser> {
    private final SysUserMapper sysUserMapper;
    private final CaptchaService captchaService;
    private final JwtProperties jwtProperties;
    private final SetOperations<String,Object> setOperations;

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
        try{
        Boolean check = captchaService.check(loginUserParam.getSignature(), loginUserParam.getCaptcha());
        if(!check){
            return Result.<JwtToken>build().error(INVALID_CAPCHA);
        }
        SysUser sysUser = this.findSysUserByUsername(loginUserParam.getUsername());
        if(Objects.isNull(sysUser)){
            return Result.<JwtToken>build().error(INVALID_USER);
        }
        if(!BCrypt.checkpw(loginUserParam.getPassword(), sysUser.getPassword())){
            return Result.<JwtToken>build().error(INVALID_PASSWORD);
        }
        if(Objects.equals(UserEnum.valueOf(sysUser.getStatus()),LOCK)){
            return Result.<JwtToken>build().error(LOCKED_USER);
        }
        String jti = JwtUtil.createJti();
        sysUser.setJti(jti);
        // 4.生成token
        String accessToken = JwtUtil.createAccessToken(sysUser, jwtProperties.getIss(), jwtProperties.getAud(),
                jwtProperties.getExpirationSeconds() * 1000, jwtProperties.getBase64Secret());

        String refreshToken = JwtUtil.createRefreshToken(sysUser.getUsername(), jwtProperties.getIss(), jwtProperties.getAud(),
                jwtProperties.getExpirationSeconds() * 7000, jwtProperties.getBase64Secret());
        setOperations.add(SecurityConsts.LOGIN_SALT + sysUser.getUsername(),jti);
        JwtToken token = new JwtToken(accessToken, refreshToken, "Bearer", jwtProperties.getExpirationSeconds());
        return Result.<JwtToken>build()
                .ok()
                .withData(token);
        }catch (Exception e){
            e.printStackTrace();
            return Result.<JwtToken>build().error(INTER_ERROR);
        }

    }
}
