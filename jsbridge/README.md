# H5与Native交互框架

# 一、H5侧

## 1、定义Js初始化注册事件监听，固定写法
```
function connectWebViewJavascriptBridge(callback) {
    if (window.WebViewJavascriptBridge) {
        callback(WebViewJavascriptBridge)
    } else {
        document.addEventListener(
            'WebViewJavascriptBridgeReady'
            , function() {
                callback(WebViewJavascriptBridge)
            },
            false
        );
    }
}
```

## 2、bridge初始化
```
// 注册回调函数，第一次连接时调用 初始化函数，只需要初始化一次
connectWebViewJavascriptBridge(function(bridge) {

    bridge.init(function(message, responseCallback) {
       var data = { 'Javascript Responds':'Wee!' }
       responseCallback(data)
       })
       
})
```
## 3、调用方式
### a、Js注册方法（供Native调用）
```
//注册方法列表 ，方法注入给native调用

bridge.registerHandler('addActionMethodsToNative', function(data, responseCallback) {
    var responseData = addActionMethodsToNative test;
    responseCallback(JSON.stringify(responseData))
})

bridge.registerHandler( ...
    ...
    ...

addActionMethodsToNative：注入到native方法名，即native调用Js的方法名；
function(data,  responseCallback){}：native调用Js方法后，Js的处理，data为传给Js方法的参数，responseCallback为调用之后Js的回调处理
```

### b、Js调用Native
```
//Js通过方法名调用Native暴露出来的方法
bridge.callHandler('TestWithCallback', data, function(response) {
    alert(response);
    log('TestWithCallback', response)
    })

bridge.callHandler(...
    ...
    ...

TestWithCallback：为Native提供的方法名
data：为要传给Native的参数数据，
function(response){}：为Js自身回调闭包，当异步调用完Native方法之后，Native的回调处理；
```

# 二、Native侧
## 1、Js脚本注入
```
//原生WebView每次加载H5页面时，都要注入一段Js脚本
//Js文件位置在原生工程目录assets/WebViewJavascriptBridge.js

//在WebView的方法onPageFinished中执行如下代码：
try {
    if (mNativeJS == null) {
        InputStream is = webView.getContext().getAssets().open(JS_FILE_NAME);
        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();
        mNativeJS = new String(buffer);
    }
    executeJavascript(mNativeJS);
} catch (IOException e) {
    e.printStackTrace();
}

```
## 2、WebViewClient初始化
```
//继承依赖包中的WVJBWebViewClient，设置WebViewClient
private void initWebView() {
    ...
    webView.setWebViewClient(new MyWebViewClient(webView));
    ...
}
...
private class MyWebViewClient extends WVJBWebViewClient {
    MyWebViewClient(WebView webView) {
        super(webView);
        new SampleRegisterHandler(this, webView.getContext()).init();
    }
}
```
## 3、调用方式
### a、Native注册方法（供Js调用）
```
/**
     * 初始化，注册处理程序
     */
public void init() {
    mWVJBWebViewClient.registerHandler("TestWithCallback", new TestWithCallback());
    mWVJBWebViewClient.registerHandler("TestWithoutCallback", new TestWithoutCallback(mContext));
}
```
### b、Native调用Js方法
```
private class MyWebViewClient extends WVJBWebViewClient {
    MyWebViewClient(WebView webView) {
        super(webView);
        new SampleRegisterHandler(this, webView.getContext()).init();
    }
    ...
    ...
    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        //在WVJBWebViewClient中调用如下三个重载方法
        callHandler("addActionMethodsToNative");
        callHandler("addActionMethodsToNative", data);
        callHandler("addActionMethodsToNative", data, responseCallback);
    }
    ...
    ...
}
```


