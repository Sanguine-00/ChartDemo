package com.mobcb.base.http.api;


import com.mobcb.base.http.config.ServerUrlConfig;

/**
 * @author : Shiyaozu
 * @version : [V1.0.0]
 * @date : 2017/7/5 0005
 * @desc : [验证码图片地址]
 */

public interface CaptchaService {
    String CAPTCHA_URL = ServerUrlConfig.HTTP_BASE_URL +"api/v3/management/captcha/image?captchaId=%1$s&rnd=%2$s";
}
