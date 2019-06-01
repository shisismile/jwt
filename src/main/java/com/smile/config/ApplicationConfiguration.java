package com.smile.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 自定义的配置, 用以代替@Value 实现统一管理,以及热更新
 *
 * @author smile
 * @date 5/24/2018
 */
@ConfigurationProperties(prefix = "application.config")
@Configuration
public class ApplicationConfiguration {

    /**
     * 不需要登陆的 path patterns
     */
    private String[] securityPermit;

    public String[] getSecurityPermit() {
        return securityPermit;
    }

    public void setSecurityPermit(String[] securityPermit) {
        this.securityPermit = securityPermit;
    }
}
