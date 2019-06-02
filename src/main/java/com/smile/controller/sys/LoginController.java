package com.smile.controller.sys;

import com.smile.param.CaptchaResult;
import com.smile.param.LoginUserParam;
import com.smile.param.Result;
import com.smile.service.CaptchaService;
import com.smile.service.UserService;
import com.smile.shiro.token.JwtToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author smile
 */
@Api(tags = "登录接口")
@RestController
@RequestMapping("login")
@AllArgsConstructor
public class LoginController {
    private final CaptchaService captchaService;
    private final UserService userService;

    @ApiOperation("获取验证码")
    @GetMapping("captcha")
    public Result<CaptchaResult> getCaptcha(@ApiParam(name = "signature",value = "签名",required = true) @RequestParam String signature) throws IOException {
        return Result.<CaptchaResult>build().ok().withData(captchaService.getCaptcha(signature));
    }

    @ApiOperation("获取用户登录token")
    @GetMapping("token")
    public Result<JwtToken> token(LoginUserParam loginUserParam){
        return userService.getToken(loginUserParam);
    }

}
