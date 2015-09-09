package com.evgeniysharafan.retrofitexample.http;


import java.util.concurrent.atomic.AtomicBoolean;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public abstract class RetrofitCallback<T> implements Callback<T> {

    public interface ApiCallbacks {
        <T> void success(T resp, Response response);

        void failure(RetrofitError error);
    }

    public interface CallbacksGetter {
        ApiCallbacks getApiCallbacks();
    }

    private final CallbacksGetter callbacksGetter;
    private final AtomicBoolean isRequestRunning;

    protected RetrofitCallback(CallbacksGetter callbacksGetter, AtomicBoolean isRequestRunning) {
        this.callbacksGetter = callbacksGetter;
        this.isRequestRunning = isRequestRunning;
        setRequestRunning(true);
    }

    @Override
    public void success(T resp, Response response) {
        setRequestRunning(false);

        ok(resp, response);

        if (callbacksGetter.getApiCallbacks() != null) {
            callbacksGetter.getApiCallbacks().success(resp, response);
        }
    }

    /**
     * Save the resp in this method
     */
    protected abstract void ok(T resp, Response response);

    @Override
    public void failure(RetrofitError error) {
        setRequestRunning(false);

        if (callbacksGetter.getApiCallbacks() != null) {
            callbacksGetter.getApiCallbacks().failure(error);
        }
    }

    private void setRequestRunning(boolean isRunning) {
        if (isRequestRunning != null) {
            isRequestRunning.set(isRunning);
        }
    }
}
