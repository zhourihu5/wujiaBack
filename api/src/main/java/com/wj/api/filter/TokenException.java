package com.wj.api.filter;

import lombok.Data;

@Data
public class TokenException extends Exception {
//    自定义异常类型
    private int code;
    private String msg;

    public TokenException() {
    }

    public TokenException(int code, String msg) {
//        super(msg);
        this.code = code;
        this.msg = msg;
    }
}