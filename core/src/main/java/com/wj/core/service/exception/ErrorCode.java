package com.wj.core.service.exception;

public enum ErrorCode {

    ENTITY_NULL(1101, 400),
    BAD_REQUEST(400, 400),
    UNAUTHORIZED(401, 401),
    FORBIDDEN(403, 403),
    INTERNAL_SERVER_ERROR(500, 500);

    public int code;
    public int httpStatus;

    ErrorCode(int code, int httpStatus) {
        this.code = code;
        this.httpStatus = httpStatus;
    }

}
