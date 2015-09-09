package com.evgeniysharafan.retrofitexample.http;

import com.evgeniysharafan.retrofitexample.http.model.Issue;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.http.GET;

public interface ApiInterface {

    @GET("/repos/square/retrofit/issues")
    void getIssues(Callback<ArrayList<Issue>> callback);

}
