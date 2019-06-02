package com.smile.shiro.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.smile.model.sys.SysUser;
import com.smile.shiro.SecurityConsts;
import com.smile.shiro.token.JwtProperties;
import com.smile.shiro.token.TokenEnums;
import com.smile.utils.Bean2MapUtils;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * TODO
 *
 * @author shimingen
 * @date 2019/5/30 17:31
 */
@Component
public class JwtUtil {

   private JwtProperties jwtProperties;

    public JwtUtil(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    private static JwtUtil jwtUtil;

    @PostConstruct
    public void init() {
        jwtUtil = this;
        jwtUtil.jwtProperties = this.jwtProperties;
    }

    /**
     * 校验token是否正确
     * @param token
     * @return
     */
    public static boolean verify(String token) throws UnsupportedEncodingException {
        String secret = getClaim(token, SecurityConsts.ACCOUNT) + jwtUtil.jwtProperties.getBase64Secret();
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm)
                .build();
        verifier.verify(token);
        return true;
    }

    /**
     * 获得Token中的信息无需secret解密也能获得
     * @param token
     * @param claim
     * @return
     */
    public static String getClaim(String token, String claim) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(claim).asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 生成签名,5min后过期
     * @param account
     * @param currentTimeMillis
     * @return
     */
    public static String sign(String account, String currentTimeMillis) throws UnsupportedEncodingException {
        // 帐号加JWT私钥加密
        String secret = account + jwtUtil.jwtProperties.getBase64Secret();
        // 此处过期时间，单位：毫秒
        Date date = new Date(System.currentTimeMillis() + jwtUtil.jwtProperties.getExpirationSeconds()*60*1000L);
        Algorithm algorithm = Algorithm.HMAC256(secret);

        return JWT.create()
                .withClaim(SecurityConsts.ACCOUNT, account)
                .withClaim(SecurityConsts.CURRENT_TIME_MILLIS, currentTimeMillis)
                .withExpiresAt(date)
                .sign(algorithm);
    }


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

        Map<String, Object> claims = Bean2MapUtils.bean2Map(user);

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

        String payload = new String(Base64.getUrlDecoder().decode(jwt[1]));
        if(StringUtils.isEmpty(payload)){
            return null;
        }
        return JSONObject.parseObject(payload, SysUser.class);
    }

    private static Map<String, Object> bean2map(Object bean) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Map<String, Object> map = new HashMap<>(16);
        new BeanMap(bean).forEach((k, v) -> map.put(String.valueOf(k), v instanceof LocalDateTime ?formatter.format((LocalDateTime)v) :v));
        return map;
    }
}
