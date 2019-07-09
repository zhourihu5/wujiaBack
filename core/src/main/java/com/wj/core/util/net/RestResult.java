package com.wj.core.util.net;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by sun on 2018/10/29.
 */
public class RestResult<T> {

    private int status;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    /**
     * 成功时候的调用
     */
    public static <T> RestResult<T> ok(T data) {
        return new RestResult(200, "SUCCESS", data);
    }

    /**
     * 成功时候的调用
     */
    public static <T> RestResult<T> ok() {
        return new RestResult(200, "SUCCESS");
    }

    public RestResult() {}

    public RestResult(T data) {
        this.data = data;
    }

    public RestResult(int status, String msg) {
        this.status = status;
        this.message = msg;
    }

    public RestResult(int status, String msg, T t) {
        this.status = status;
        this.message = msg;
        this.data = t;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String msg) {
        this.message = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
