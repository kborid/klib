package com.kborid.common.network.func;

import com.orhanobut.logger.Logger;
import com.kborid.common.network.comm.CommResBean;

import io.reactivex.functions.Function;

/**
 * @description: 请求响应拦截方法类，判断请求返回是否成功，如果失败，抛出异常msg与异常code
 * @date: 2019/7/2
 * @author: duanwei
 * @email: duanwei@thunisoft.com
 */
public class ResponseFunc<T> implements Function<CommResBean<T>, T> {
    @Override
    public T apply(CommResBean<T> response) throws Exception {
        if (ApiException.SUCCESS != response.getCode() && ApiException.OK != response.getCode()) {
            throw new ApiException(response.getCode(), response.getMsg());
        }

        if (response.getData() == null) {
            Logger.t("ResponseFunc").d("response.getData() == null");
            return (T) response.getMsg();
        }
        return response.getData();
    }
}