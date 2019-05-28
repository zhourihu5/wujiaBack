package com.api.Filter;

import lombok.Data;

@Data
public class ResponseMessage<T>{
//    自定义异常类型
    private int code;
    private String msg;
    private T data;

    public ResponseMessage() {
    }

    public ResponseMessage(int code, String msg, T data) {
//        super(msg);
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}