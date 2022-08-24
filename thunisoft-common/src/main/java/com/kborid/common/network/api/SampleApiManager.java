package com.kborid.common.network.api;

import android.annotation.SuppressLint;

import com.kborid.common.network.OkHttpClientFactory;
import com.kborid.common.network.callback.ResponseCallback;
import com.kborid.common.network.comm.CommResBean;
import com.kborid.common.network.func.ResponseFunc;
import com.kborid.common.network.util.RxUtil;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public class SampleApiManager {

    private static final String baseUrl = "";

    @SuppressLint("NewApi")
    private static Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .client(OkHttpClientFactory.newOkHttpClient())
                .baseUrl(baseUrl)
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    private static Api api;

    private static Api getApi() {
        if (api == null) {
            api = getRetrofit().create(Api.class);
        }
        return api;
    }

    public static void testPost(Object params, final ResponseCallback<Object> responseCallback) {
        getApi().post(params)
                .map(new ResponseFunc<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(RxUtil.createDefaultSubscriber(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {

                    }
                }));
    }

    public static Observable<Object> testGet(String code) {
        return getApi().get(code)
                .compose(RxUtil.handleResult())
                .compose(RxUtil.rxSchedulerHelper());
    }

    /**
     * 请求接口地址
     */
    private interface Api {

        /**
         * post
         *
         * @param param
         * @return
         */
        @POST("api/v1/post")
        Observable<CommResBean<Object>> post(@Body Object param);

        /**
         * get
         *
         * @param code
         * @return
         */
        @GET("api/v1/get/{code}")
        Observable<CommResBean<Object>> get(@Path("code") String code);
    }
}
