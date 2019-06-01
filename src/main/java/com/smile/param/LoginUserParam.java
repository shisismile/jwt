package com.smile.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "用户登录信息")
@Data
public class LoginUserParam {

    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("用户密码")
    private String password;
    @ApiModelProperty("验证码")
    private String captcha;
    @ApiModelProperty("验证码签名")
    private String signature;
}
