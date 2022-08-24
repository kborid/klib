package com.kborid.common.network.func;

import io.reactivex.functions.Consumer;

public abstract class ErrorAction implements Consumer<Throwable> {

    @Override
    public void accept(Throwable throwable) throws Exception {
        call(ApiException.handleException(throwable));
    }

    protected abstract void call(ApiException e);
}