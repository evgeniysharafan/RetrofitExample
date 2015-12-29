package com.evgeniysharafan.retrofitexample.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.evgeniysharafan.retrofitexample.R;
import com.evgeniysharafan.retrofitexample.ui.fragment.IssuesFragment;
import com.evgeniysharafan.utils.Fragments;

public class IssuesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_fragment);

        if (savedInstanceState == null) {
            Fragments.replace(getSupportFragmentManager(), R.id.content, IssuesFragment.newInstance(), null);
        }
    }

}
