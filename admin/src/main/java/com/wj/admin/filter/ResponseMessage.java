package com.wj.admin.filter;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseMessage<T>{

    private int code;
    private String message;
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

    public ResponseMessage(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseMessage(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

}