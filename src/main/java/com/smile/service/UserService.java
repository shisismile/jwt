package com.smile.service;

import com.smile.model.sys.SysUser;
import com.smile.param.LoginUserParam;
import com.smile.param.Result;
import com.smile.param.ResultStatusEnum;
import com.smile.param.UserEnum;
import com.smile.repository.sys.SysUserRepository;
import com.smile.shiro.SecurityConsts;
import com.smile.shiro.token.JwtProperties;
import com.smile.shiro.token.JwtToken;
import com.smile.shiro.util.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.smile.param.ResultStatusEnum.*;
import static com.smile.param.UserEnum.LOCK;

/**
 * TODO
 *
 * @author shimingen
 * @date 2019/5/31 18:14
 */
@Service
@AllArgsConstructor
@Slf4j
public class UserService {
    private final SysUserRepository sysUserRepository;
    private final SysCaptchaService sysCaptchaService;
    private final SetOperations<String, Object> setOperations;
    private final JwtProperties jwtProperties;

    public SysUser getSysUserByUserName(String username) {
        return sysUserRepository.findByUsername(username);
    }

    public Result<JwtToken> signin(LoginUserParam loginUserParam) {
        try {
            boolean checkCapcha = sysCaptchaService.check(loginUserParam.getCaptcha(), loginUserParam.getSignature());
            if (!checkCapcha) {
                return Result.<JwtToken>build().error(INVALID_CAPCHA);
            }
            SysUser sysUser = this.getSysUserByUserName(loginUserParam.getUsername());
            if (Objects.isNull(sysUser)) {
                return Result.<JwtToken>build().error(INVALID_USER);
            } else {
                if (!BCrypt.checkpw(loginUserParam.getPassword(), sysUser.getPassword())) {
                    return Result.<JwtToken>build().error(INVALID_PASSWORD);
                }
            }
            if (Objects.equals(LOCK, UserEnum.valueOf(sysUser.getStatus()))) {
                return Result.<JwtToken>build().error(LOCKED_USER);
            }
            String jti = JwtUtil.createJti();
            sysUser.setJti(jti);
            String accessToken = JwtUtil.createAccessToken(sysUser, jwtProperties.getIss(), jwtProperties.getAud(), jwtProperties.getExpirationSeconds() * 1000, jwtProperties.getBase64Secret());
            String refreshToken = JwtUtil.createRefreshToken(sysUser.getUsername(), jwtProperties.getIss(), jwtProperties.getAud(), jwtProperties.getExpirationSeconds() * 1000, jwtProperties.getBase64Secret());
            // 缓存至redis
            setOperations.add(SecurityConsts.LOGIN_SALT + sysUser.getUsername(), jti);
            JwtToken jwtToken = new JwtToken(accessToken, refreshToken, "Bearer", jwtProperties.getExpirationSeconds());
            return Result.<JwtToken>build()
                    .ok()
                    .withData(jwtToken);
        } catch (Exception e) {
            log.error("error", e);
            return Result.<JwtToken>build()
                    .error(ResultStatusEnum.INTER_ERROR);
        }
    }

    public void save(String username, String password) {
        SysUser sysUser=new SysUser();
        sysUser.setUsername(username);
        String pwd = BCrypt.hashpw(password, BCrypt.gensalt());
        sysUser.setPassword(pwd);
        sysUserRepository.save(sysUser);
    }
}
