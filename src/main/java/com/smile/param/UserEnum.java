package com.smile.param;


public enum  UserEnum {

    LOCK(-1),
    ACTIVE(0);

    private Integer code;

    UserEnum(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public static UserEnum valueOf(Integer code){
        switch (code){
            case -1:return LOCK;
            case 0:return ACTIVE;
            default:return null;
        }
    }
}
