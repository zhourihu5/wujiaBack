package com.wj.api.controller;

public class ErrorResult {

    public int code;
    public String message;

    public ErrorResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
