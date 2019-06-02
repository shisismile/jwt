package com.smile.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author smile
 */
@ApiModel(description = "验证码")
@Data
public class SysCaptcha {
    @ApiModelProperty("签名")
    private String signature;
    @ApiModelProperty("验证码")
    private String code;
    @ApiModelProperty("验证码图片")
    private String codeImg;
    @ApiModelProperty("验证码创建时间")
    private LocalDateTime createTime;
}
