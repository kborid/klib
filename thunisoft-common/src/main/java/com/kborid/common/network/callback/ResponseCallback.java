package com.kborid.common.network.callback;

public interface ResponseCallback<T> {
    void failure(Throwable e);

    void success(T data);
}
