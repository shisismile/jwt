package com.smile.shiro;

/**
 * TODO
 *
 * @author shimingen
 * @date 2019/5/30 17:29
 */
public interface SecurityConsts {
     String LOGIN_SALT = "SMILE_USER";

    //request请求头属性
     String REQUEST_AUTH_HEADER="Authorization";
    //JWT-account
    String ACCOUNT = "account";
    //Shiro redis 前缀
    String PREFIX_SHIRO_CACHE = "storyweb-bp:cache:";

    //redis-key-前缀-shiro:refresh_token
    String PREFIX_SHIRO_REFRESH_TOKEN = "storyweb-bp:refresh_token:";

    //JWT-currentTimeMillis
    String CURRENT_TIME_MILLIS = "currentTimeMillis";
}
