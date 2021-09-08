package com.epro.lvall.gsontestbean;

import java.io.Serializable;

/**
 * 接口请求返回bean
 * Created by hxq on 2019/12/23.
 */
public class BaseResponse<T> implements Serializable {
    /**
     * 返回状态码
     */
    private String success;
    private String reason;
    private String message;
    /**
     * 消息内容
     */
    private T result;
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return success;
    }

    public void setCode(String success) {
        this.success = success;
    }

    public T getData() {
        return result;
    }

    public void setData(T result) {
        this.result = result;
    }
}
