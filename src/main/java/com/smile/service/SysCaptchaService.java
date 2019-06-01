package com.smile.service;


import com.google.code.kaptcha.Producer;
import com.smile.model.sys.SysCaptchaModel;
import com.smile.param.CaptchaResult;
import com.smile.repository.sys.SysCaptchaModelRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.bson.types.ObjectId;
import org.checkerframework.checker.units.qual.C;
import org.springframework.stereotype.Service;
import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.Objects;

@Service
@AllArgsConstructor
@Slf4j
public class SysCaptchaService {
    private final SysCaptchaModelRepository sysCaptchaModelRepository;
    private final Producer producer;

    public CaptchaResult getCaptcha() {
        CaptchaResult captchaResult=new CaptchaResult();
        //生成文字验证码
        String code = producer.createText();
        SysCaptchaModel sysCaptchaModel = new SysCaptchaModel();
        ObjectId objectId = ObjectId.get();
        sysCaptchaModel.setId(objectId);
        sysCaptchaModel.setCode(code);
        //5分钟后过期
        sysCaptchaModel.setExpireTime(DateUtils.addMinutes(new Date(), 5));
        sysCaptchaModelRepository.save(sysCaptchaModel);
        captchaResult.setImg(producer.createImage(code));
        captchaResult.setSignature(objectId.toHexString());
        return captchaResult;
    }

    public boolean check(String captcha, String signature) {
        SysCaptchaModel sysCaptchaModel = sysCaptchaModelRepository.findById(new ObjectId(signature)).orElseThrow(NullPointerException::new);
        if(StringUtils.isNotBlank(captcha)&&Objects.equals(sysCaptchaModel.getCode(),captcha)){
            return true;
        }
        return false;
    }
}
