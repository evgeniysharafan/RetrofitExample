package com.evgeniysharafan.retrofitexample.util;

import android.app.Application;

import com.evgeniysharafan.retrofitexample.BuildConfig;
import com.evgeniysharafan.retrofitexample.util.lib.Utils;


public final class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Utils.init(this, BuildConfig.DEBUG);
    }

}
