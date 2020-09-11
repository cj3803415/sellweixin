package com.weixin.enums;

import lombok.Getter;

@Getter
public enum OrderStatusEnum {
    NEW(0,"新订单"),
    FINISHED(1,"完成"),
    CANCEL(2,"已取消"),
    ;

    private  Integer code;

    private String message;

    OrderStatusEnum(Integer code, String message){
        this.code=code;
        this.message=message;

    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
