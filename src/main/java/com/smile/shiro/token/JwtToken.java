package com.smile.shiro.token;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * TODO
 *
 * @author shimingen
 * @date 2019/5/30 17:30
 */
@ApiModel(description = "Token")
@Data
public class JwtToken implements AuthenticationToken {
    @ApiModelProperty("访问用token")
    private String accessToken;
    @ApiModelProperty("刷新用token")
    private String refreshToken;
    @ApiModelProperty("token类型")
    private String tokenType;
    @ApiModelProperty("过期时间")
    private long expiresIn;

    public JwtToken(String accessToken, String refreshToken, String tokenType, long expiresIn) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
    }

    public JwtToken(String token) {
        this.accessToken = token;
    }

    @Override
    public Object getPrincipal() {
        return accessToken;
    }

    @Override
    public Object getCredentials() {
        return accessToken;
    }
}
