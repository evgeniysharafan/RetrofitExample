package com.evgeniysharafan.retrofitexample.http;


import com.evgeniysharafan.retrofitexample.R;
import com.evgeniysharafan.retrofitexample.http.model.Issue;
import com.evgeniysharafan.utils.L;
import com.evgeniysharafan.utils.Res;
import com.evgeniysharafan.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

public class ApiManager {

    private static final String ENDPOINT = Res.getString(R.string.base_url);
    private static ApiInterface apiInterface;

    static {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssz")
                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                .setConverter(new GsonConverter(gson))
                .setLogLevel(Utils.isDebug() ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
                .setLog(new RestAdapter.Log() {
                    @Override
                    public void log(String message) {
                        if (message != null && (message.startsWith("{") || message.startsWith("["))) {
                            L.v(message);
                        } else {
                            L.d(message);
                        }
                    }
                })
                .build();

        apiInterface = restAdapter.create(ApiInterface.class);
    }

    public static void getIssues(Callback<ArrayList<Issue>> callback) {
        apiInterface.getIssues(callback);
    }
}
