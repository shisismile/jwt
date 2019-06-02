package com.smile.service;

import com.alibaba.fastjson.JSONObject;
import com.google.code.kaptcha.Producer;
import com.smile.param.CaptchaResult;
import com.smile.param.SysCaptcha;
import lombok.AllArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author smile
 */
@Service
@AllArgsConstructor
public class CaptchaService {
    private final ValueOperations<String, String>  valueOperations;
    private final Producer producer;

    /**
     * 获取验证码
     * @param signature
     * @return
     * @throws IOException
     */
    public CaptchaResult getCaptcha(String signature) throws IOException {
        if(StringUtils.isBlank(signature)){
            throw new NullPointerException("签名为空!");
        }
        //生成文字验证码
        String code = producer.createText();
        SysCaptcha sysCaptcha=new SysCaptcha();
        sysCaptcha.setSignature(signature);
        sysCaptcha.setCode(code);
        sysCaptcha.setCreateTime(LocalDateTime.now());
        BufferedImage image = producer.createImage(code);
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", baos);
        byte[] bytes = baos.toByteArray();
        String base64String = Base64.encodeBase64String(bytes);
        sysCaptcha.setCodeImg(base64String);
        baos.close();
        valueOperations.set(signature, JSONObject.toJSONString(sysCaptcha), Duration.ofMinutes(5));
        CaptchaResult captchaResult=new CaptchaResult();
        captchaResult.setSignature(signature);
        captchaResult.setBase64Img(base64String);
        return captchaResult;
    }

    /**
     * 检查验证码
     * @param signature
     * @return
     */
    public Boolean check(String signature,String code){
        if(StringUtils.isBlank(signature)){
            throw new IllegalArgumentException("验证码图片签名!");
        }
        String json = valueOperations.get(signature);
        SysCaptcha sysCaptcha = JSONObject.parseObject(json, SysCaptcha.class);
        if(Objects.isNull(sysCaptcha)){
            throw new NullPointerException("验证码失效或者不存在!");
        }
        return Objects.equals(code,sysCaptcha.getCode());
    }
}
