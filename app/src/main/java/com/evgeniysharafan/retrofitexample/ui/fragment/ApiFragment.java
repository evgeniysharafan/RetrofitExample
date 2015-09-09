package com.evgeniysharafan.retrofitexample.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.evgeniysharafan.retrofitexample.http.ApiManager;
import com.evgeniysharafan.retrofitexample.http.RetrofitCallback;
import com.evgeniysharafan.retrofitexample.http.RetrofitCallback.ApiCallbacks;
import com.evgeniysharafan.retrofitexample.http.RetrofitCallback.CallbacksGetter;
import com.evgeniysharafan.retrofitexample.http.model.Issue;
import com.evgeniysharafan.retrofitexample.util.lib.Utils;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class ApiFragment extends Fragment implements CallbacksGetter {

    private ApiCallbacks callbacks;

    public ArrayList<Issue> getIssuesResponse;
    private AtomicBoolean isGetIssuesRunning = new AtomicBoolean();
    private static final String STATE_GET_ISSUES_RESPONSE = "state_get_issues_response";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        restoreResponses(savedInstanceState);
    }

    public void setCallbacks(ApiCallbacks callbacks) {
        this.callbacks = callbacks;
    }

    @Override
    public ApiCallbacks getApiCallbacks() {
        return callbacks;
    }

    private boolean needSend(AtomicBoolean isRunning) {
        return needSend(isRunning, "");
    }

    // a version of needSend which helps to distinguish requests using their url (e.g. for retry purposes)
    private boolean needSend(AtomicBoolean isRunning, String url) {
        return !isRunning.get() && hasInternetConnection(url);
    }

    /**
     * @param url helps to distinguish requests using their url in the failure(RetrofitError error)
     *            error.getUrl() (e.g. for retry purposes)
     */
    private boolean hasInternetConnection(String url) {
        if (!Utils.hasInternetConnection() && callbacks != null) {
            callbacks.failure(RetrofitError.networkError(url, new UnknownHostException()));
            return false;
        }

        return true;
    }

    public void getIssues() {
        if (needSend(isGetIssuesRunning)) {
            ApiManager.getIssues(new RetrofitCallback<ArrayList<Issue>>(this, isGetIssuesRunning) {
                @Override
                protected void ok(ArrayList<Issue> resp, Response response) {
                    getIssuesResponse = resp;
                }
            });
        }
    }

    private void restoreResponses(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            getIssuesResponse = savedInstanceState.getParcelableArrayList(STATE_GET_ISSUES_RESPONSE);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STATE_GET_ISSUES_RESPONSE, getIssuesResponse);
    }
}
