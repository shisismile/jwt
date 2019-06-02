package com.smile.param;

/**
 *
 * @author smile
 * @date 2019-06-02
 */
public enum ResultStatusEnum {
    /**
     * 请求状态
     */
    OK(200, "OK"),
    INVALID_CAPCHA(400, "验证码有误"),
    INVALID_USER(400, "未知用户"),
    AUTHENTICATE_FAILED(401, "未授权"),
    INVALID_PASSWORD(401, "用户名或密码不正确"),
    INVALID_TOKEN(401, "token 无效"),
    EXPIRED_TOKEN(401, "token 已过期"),
    INTER_ERROR(500, "服务器内部错误"),
    LOCKED_USER(401, "账户已被冻结");
    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    ResultStatusEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
