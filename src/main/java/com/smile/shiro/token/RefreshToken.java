package com.smile.shiro.token;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author smile
 * @date 2019-06-03
 */
@ApiModel("刷新token")
@Data
public class RefreshToken {
    @ApiModelProperty("访问用token")
    @NotBlank
    private String accessToken;
    @ApiModelProperty("刷新用token")
    @NotBlank
    private String refreshToken;

}
