package com.kborid.jsbridge.wvjb;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * js bridge通信message
 */
public class WVJBMessage {

    private static final String TAG = WVJBMessage.class.getSimpleName();

    private Object data = null;
    private String callbackId = null;
    private String handlerName = null;
    private String responseId = null;
    private Object responseData = null;

    private final static String CALLBACK_ID_STR = "callbackId";
    private final static String RESPONSE_ID_STR = "responseId";
    private final static String RESPONSE_DATA_STR = "responseData";
    private final static String DATA_STR = "data";
    private final static String HANDLER_NAME_STR = "handlerName";

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getCallbackId() {
        return callbackId;
    }

    public void setCallbackId(String callbackId) {
        this.callbackId = callbackId;
    }

    public String getHandlerName() {
        return handlerName;
    }

    public void setHandlerName(String handlerName) {
        this.handlerName = handlerName;
    }

    public String getResponseId() {
        return responseId;
    }

    public void setResponseId(String responseId) {
        this.responseId = responseId;
    }

    public Object getResponseData() {
        return responseData;
    }

    public void setResponseData(Object responseData) {
        this.responseData = responseData;
    }

    public static String message2JsonString(WVJBMessage message) {
        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put(CALLBACK_ID_STR, message.getCallbackId());
            jsonObject.put(DATA_STR, message.getData());
            jsonObject.put(HANDLER_NAME_STR, message.getHandlerName());
            jsonObject.put(RESPONSE_DATA_STR, message.getResponseData());
            jsonObject.put(RESPONSE_ID_STR, message.getResponseId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    public static WVJBMessage JsonString2Message(String jsonStr) {
        WVJBMessage message =  new WVJBMessage();
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            message.setHandlerName(jsonObject.has(HANDLER_NAME_STR) ? jsonObject.getString(HANDLER_NAME_STR):null);
            message.setCallbackId(jsonObject.has(CALLBACK_ID_STR) ? jsonObject.getString(CALLBACK_ID_STR):null);
            message.setResponseData(jsonObject.has(RESPONSE_DATA_STR) ? jsonObject.getString(RESPONSE_DATA_STR):null);
            message.setResponseId(jsonObject.has(RESPONSE_ID_STR) ? jsonObject.getString(RESPONSE_ID_STR):null);
            message.setData(jsonObject.has(DATA_STR) ? jsonObject.getString(DATA_STR):null);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return message;
    }
}
