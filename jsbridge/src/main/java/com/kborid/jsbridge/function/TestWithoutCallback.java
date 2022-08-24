package com.kborid.jsbridge.function;

import android.content.Context;
import android.widget.Toast;

import com.kborid.jsbridge.wvjb.WVJBResponseCallback;
import com.kborid.jsbridge.wvjb.handler.WVJBHandler;

public class TestWithoutCallback implements WVJBHandler {

    private Context context;

    public TestWithoutCallback(Context context) {
        this.context = context;
    }

    @Override
    public void request(Object data, WVJBResponseCallback callback) {
        Toast.makeText(context, "TestWithoutCallback", Toast.LENGTH_SHORT).show();
    }
}
