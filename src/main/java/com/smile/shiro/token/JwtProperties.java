package com.smile.shiro.token;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * TODO
 *
 * @author shimingen
 * @date 2019/5/30 17:34
 */
@Component
@ConfigurationProperties(prefix = "token")
@Data
public class JwtProperties {
    /**
     * 授权者
     */
    private String iss;
    /**
     * 鉴权者
     */
    private String aud;
    /**
     * Base64编码后的密钥
     */
    private String base64Secret;
    /**
     * token 有效时间: 单位s
     */
    private int expirationSeconds;
}