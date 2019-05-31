package com.wj.api.filter;

import lombok.Data;

@Data
public class ResponseMessage<T>{

    private int code;
    private String msg;
    private T data;

    public ResponseMessage() {
    }

    /**
     * 成功时候的调用
     */
    public static <T> ResponseMessage<T> ok(T data) {
        return new ResponseMessage(200, "SUCCESS", data);
    }

    /**
     * 成功时候的调用
     */
    public static <T> ResponseMessage<T> ok() {
        return new ResponseMessage(200, "SUCCESS");
    }

    public ResponseMessage(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResponseMessage(int code, String msg, T data) {
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