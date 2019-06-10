package com.wj.admin.utils;


import com.wj.admin.filter.ResponseMessage;

/**
 * 返回结果
 * @author thz
 */
public class ResultUtil {
    public static ResponseMessage success(int code, String msg, Object object) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setCode(code);
        responseMessage.setMsg(msg);
        responseMessage.setData(object);
        return responseMessage;
    }

    /**
     * 操作失败返回的消息
     *
     * @param code
     * @param msg
     * @return
     */
    public static ResponseMessage error(int code, String msg) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setCode(code);
        responseMessage.setMsg(msg);
        return responseMessage;
    }

}
