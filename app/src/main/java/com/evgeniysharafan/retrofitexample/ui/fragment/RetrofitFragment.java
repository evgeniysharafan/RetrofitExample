package com.evgeniysharafan.retrofitexample.ui.fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.evgeniysharafan.retrofitexample.http.RetrofitCallback.ApiCallbacks;
import com.evgeniysharafan.utils.Fragments;
import com.evgeniysharafan.utils.L;

import retrofit.RetrofitError;

public abstract class RetrofitFragment extends Fragment implements ApiCallbacks {

    protected ApiFragment api;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        api = Fragments.getByTag(getFragmentManager(), ApiFragment.class);
        if (api == null) {
            api = new ApiFragment();
            Fragments.add(getFragmentManager(), api, null);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        api.setCallbacks(this);
    }

    @Override
    public void onPause() {
        api.setCallbacks(null);
        super.onPause();
    }

    /**
     * Override this method in your fragment which extends this one if you want some custom behaviour.
     * Call super if you want to log it.
     */
    @Override
    public void failure(RetrofitError error) {
        L.e("url = " + error.getUrl());
        L.e("kind = " + error.getKind());
        L.e(error);
    }
}
