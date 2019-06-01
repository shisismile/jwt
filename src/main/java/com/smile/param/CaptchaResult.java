package com.smile.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.awt.image.BufferedImage;

@ApiModel(description = "验证码返回值")
@Data
public class CaptchaResult {
    @ApiModelProperty("验证码签名")
    private String signature;
    @ApiModelProperty("验证码图片")
    private BufferedImage img;
    @ApiModelProperty("验证码图片base64")
    private String base64Img;
}
