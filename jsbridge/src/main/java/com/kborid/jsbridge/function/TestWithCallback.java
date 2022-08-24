package com.kborid.jsbridge.function;

import com.alibaba.fastjson.JSONObject;
import com.kborid.jsbridge.wvjb.WVJBResponseCallback;
import com.kborid.jsbridge.wvjb.handler.WVJBHandler;

import java.util.HashMap;

public class TestWithCallback implements WVJBHandler {

    private static final String TAG = TestWithCallback.class.getSimpleName();

    @Override
    public void request(Object data, WVJBResponseCallback callback) {
        if (callback != null) {
            HashMap<String, String> response = new HashMap<>(1);
            response.put("Test", "TestWithCallback data");
            callback.callback(JSONObject.toJSONString(response));
        }
    }
}
