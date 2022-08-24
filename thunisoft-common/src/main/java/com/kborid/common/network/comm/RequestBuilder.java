package com.kborid.common.network.comm;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;

/**
 * 构建请求
 *
 * @author kborid
 */
public class RequestBuilder {

    private HashMap<String, Object> data;

    private RequestBuilder() {
        data = new HashMap<>();
    }

    public static RequestBuilder create() {
        return new RequestBuilder();
    }

    public <T> RequestBuilder addParam(String key, T value) {
        data.put(key, value);
        return this;
    }

    public <T> RequestBuilder addParam(HashMap<String, T> params) {
        for (String key : params.keySet()) {
            addParam(key, params.get(key));
        }
        return this;
    }


    public HashMap<String, Object> build() {
        return data;
    }

    public String toJson() {
        return JSON.toJSONString(data);
    }
}
