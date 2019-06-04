package com.smile.shiro.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.smile.model.sys.SysUser;
import com.smile.shiro.token.TokenEnums;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * TODO
 *
 * @author shimingen
 * @date 2019/5/30 17:31
 */

public class JwtUtil {

    public static boolean verify(String jsonWebToken, String base64Security) {
        return Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(base64Security))
                .isSigned(jsonWebToken);

    }

    public static String createAccessToken(SysUser user, String iss, String aud, long ttlMillis, String base64Security) {
        // 采用椭圆曲线加密算法, 提升加密速度
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //生成签名密钥
        SignatureAlgorithm signatureAlgorithm = getAlgorithm();
        Key signingKey = getKey(base64Security, signatureAlgorithm.getJcaName());

        Map<String, Object> claims = bean2map(user);

        //添加构成JWT的参数
        JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")
                .addClaims(claims)
                .setIssuer(iss)
                .setAudience(aud)
                .setSubject(user.getUsername())
                .signWith(signingKey, signatureAlgorithm);
        //添加Token过期时间
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp).setNotBefore(now);
        }
        //生成JWT
        return builder.compact();
    }

    public static String createRefreshToken(String userName, String iss, String aud, long ttlMillis, String base64Security) {

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //生成签名密钥
        SignatureAlgorithm signatureAlgorithm = getAlgorithm();
        Key signingKey = getKey(base64Security, signatureAlgorithm.getJcaName());

        //添加构成JWT的参数
        JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")
                .claim("scope", "REFRESH")
                .setIssuer(iss)
                .setAudience(aud)
                .setSubject(userName)
                .signWith(signingKey, signatureAlgorithm);
        //添加Token过期时间
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp).setNotBefore(now);
        }

        //生成JWT
        return builder.compact();
    }

    /**
     * 获取jwt 唯一身份识别码
     *
     * @return 登录用户信息
     */
    public static String createJti() {
        return UUID.randomUUID().toString();
    }

    public static SysUser verifyAndGetUser(String jwt, String base64Security) {
        if (verify(jwt, base64Security)){
            String userStr = new String(Base64.getUrlDecoder().decode(jwt.split("[.]")[1]));
            return JSON.parseObject(userStr, SysUser.class);
        }
        return null;
    }

    /**
     * 获取加密算法
     */
    private static SignatureAlgorithm getAlgorithm(){
        return SignatureAlgorithm.HS256;
    }

    private static Key getKey(String base64Security, String algorithm){
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(base64Security);
        return new SecretKeySpec(apiKeySecretBytes, algorithm);
    }

    public static SysUser checkAndGetUser(String authorization) {
        //为空不合法
        if (StringUtils.isBlank(authorization)) {
            return null;
        }
        // 格式不合法
        if (!authorization.startsWith(TokenEnums.HEADER_TYPE)){
            return null;
        }

        authorization = authorization.replaceFirst("Bearer ", "");

        String[] jwt = authorization.split("[.]");
        if (jwt.length < 3){
            return null;
        }

        String playload = new String(Base64.getUrlDecoder().decode(jwt[1]));
//        final Claims body = Jwts.parser().setSigningKey(base64Security).parseClaimsJws(authorization).getBody();
//        final String playload = JSONObject.toJSONString(body);
        if(StringUtils.isEmpty(playload)){
            return null;
        }
        return JSONObject.parseObject(playload, SysUser.class);
    }

    /**
     * 由于Bean嵌套的原因,所以才用Json转换嵌套bean,性能有影响
     * @param bean
     * @return
     */
    private static Map<String, Object> bean2map(Object bean) {
        String json = JSONObject.toJSONString(bean);
        return JSONObject.parseObject(json,Map.class);
    }
}
