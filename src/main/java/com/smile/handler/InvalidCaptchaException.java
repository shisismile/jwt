package com.smile.handler;

/**
 * 验证码验证失败异常
 * @author smile
 */
public class InvalidCaptchaException extends RuntimeException {
    public InvalidCaptchaException() {
    }

    public InvalidCaptchaException(String message) {
        super(message);
    }

    public InvalidCaptchaException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidCaptchaException(Throwable cause) {
        super(cause);
    }

    public InvalidCaptchaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
