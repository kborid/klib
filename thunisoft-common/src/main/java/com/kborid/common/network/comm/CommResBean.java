package com.kborid.common.network.comm;

/**
 * @description: 请求响应结构体基类
 * @date: 2019/7/2
 * @author: duanwei
 * @email: duanwei@thunisoft.com
 */
public class CommResBean<T> {
    private String msg;
    private int code;
    private T data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
