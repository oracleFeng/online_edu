package com.online.constant;

import io.swagger.models.auth.In;
import lombok.Getter;

/**
 * @author oxj
 * @create 2020-04-03
 */

@Getter
public enum  ResultCodeEnum {

    SUCCESS(true,200,"成功"),

    UNKNOWN_REASON(false,20001,"未知错误");

    private Boolean success;

    private Integer code;

    private String message;

    private ResultCodeEnum(Boolean success, Integer code,String message){
        this.success=success;
        this.code=code;
        this.message=message;
    }
}
