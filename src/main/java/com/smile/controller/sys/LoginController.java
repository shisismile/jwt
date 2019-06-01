package com.smile.controller.sys;


import com.smile.param.CaptchaResult;
import com.smile.param.LoginUserParam;
import com.smile.param.Result;
import com.smile.service.SysCaptchaService;
import com.smile.service.UserService;
import com.smile.shiro.token.JwtToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Api(tags = "登录接口")
@RestController
@RequestMapping("login")
@AllArgsConstructor
public class LoginController {

    private  final SysCaptchaService sysCaptchaService;
    private final UserService userService;

    /**
     * 验证码
     */
    @ApiOperation("验证码")
    @GetMapping("captcha.jpg")
    public Result<CaptchaResult> captcha()throws IOException {
        //获取图片验证码
        CaptchaResult captcha = sysCaptchaService.getCaptcha();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(captcha.getImg(), "jpg", baos);
        byte[] bytes = baos.toByteArray();//转换成字节
        BASE64Encoder encoder = new BASE64Encoder();
        String jpg_base64 =  encoder.encodeBuffer(bytes).trim();//转换成base64串
        jpg_base64 = jpg_base64.replaceAll("\n", "").replaceAll("\r", "");//删除 \r\n
        baos.close();
        captcha.setBase64Img(jpg_base64);
        captcha.setImg(null);
        return Result.<CaptchaResult>build().ok().withData(captcha);
    }

    @ApiOperation("登录")
    @GetMapping("signin")
    public Result<JwtToken> signin(LoginUserParam loginUserParam){
        return userService.signin(loginUserParam);
    }

    @ApiOperation("注册")
    @GetMapping("signup")
    public Result signup(@ApiParam(name = "username",value = "用户名")@RequestParam String username,
                         @ApiParam(name = "password",value = "密码")@RequestParam String password){
        userService.save(username,password);
        return Result.build().ok();
    }

}
