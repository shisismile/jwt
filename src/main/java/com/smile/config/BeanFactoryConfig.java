package com.smile.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;

/**
 * TODO
 *
 * @author smile
 * @date 2019/5/29 08:44
 */
@Profile({"dev", "test"})
@Configuration
public class BeanFactoryConfig {


    /***
     * Resttemplate的实现
     * @return
     */
    @Bean("OKHttp3")
    public RestTemplate OKHttp3RestTemplate(){
        final OkHttp3ClientHttpRequestFactory okHttp3ClientHttpRequestFactory = new OkHttp3ClientHttpRequestFactory();
        RestTemplate restTemplate = new RestTemplate(okHttp3ClientHttpRequestFactory);
        return restTemplate;
    }



}
