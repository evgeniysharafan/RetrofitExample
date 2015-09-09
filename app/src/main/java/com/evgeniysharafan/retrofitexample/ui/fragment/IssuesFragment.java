package com.evgeniysharafan.retrofitexample.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.evgeniysharafan.retrofitexample.R;
import com.evgeniysharafan.retrofitexample.http.model.Issue;
import com.evgeniysharafan.retrofitexample.ui.adapter.IssueAdapter;
import com.evgeniysharafan.retrofitexample.util.lib.L;
import com.evgeniysharafan.retrofitexample.util.lib.Toasts;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import static retrofit.RetrofitError.Kind;

public class IssuesFragment extends RetrofitFragment implements OnRefreshListener {

    @InjectView(R.id.swipeRefresh)
    SwipeRefreshLayout refresh;
    @InjectView(R.id.recyclerView)
    RecyclerView recyclerView;

    private List<Issue> issues = new ArrayList<>();
    private IssueAdapter adapter;

    public static IssuesFragment newInstance() {
        return new IssuesFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_issues, container, false);
        ButterKnife.inject(this, view);
        initUI();

        return view;
    }

    private void initUI() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new IssueAdapter(issues);
        recyclerView.setAdapter(adapter);
        refresh.setOnRefreshListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (issues.isEmpty()) {
            fillIssuesOrSendRequest();
        }
    }

    private void fillIssuesOrSendRequest() {
        if (api.getIssuesResponse != null) {
            fillIssues(api.getIssuesResponse);
        } else {
            // you need to call requests after super.onResume() because we set callbacks there
            api.getIssues();
        }
    }

    @Override
    public void onRefresh() {
        api.getIssues();
    }

    private void stopRefresh() {
        if (refresh.isRefreshing()) {
            refresh.setRefreshing(false);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> void success(T resp, Response response) {
        // You may have several requests per a fragment, so you need to distinguish them.
        // There are two ways to do it:
        // 1. if(resp.getClass().equals(SomeResponse.class))
        // 2. response.getUrl().contains("your url")
        if (resp.getClass().equals(ArrayList.class)) {
            fillIssues((ArrayList<Issue>) resp);
            stopRefresh();
        } else {
            L.e("unhandled response = " + resp.getClass().getSimpleName() + ", url = " + response.getUrl());
        }
    }

    @Override
    public void failure(RetrofitError error) {
        super.failure(error);
        stopRefresh();

        if (error.getKind() == Kind.NETWORK) {
            Toasts.showLong("No connection");
        } else {
            Toasts.showLong("Some error happened");
        }
    }

    private void fillIssues(ArrayList<Issue> response) {
        issues.clear();
        issues.addAll(response);
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onStop() {
        stopRefresh();
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
