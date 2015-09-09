package com.evgeniysharafan.retrofitexample.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.evgeniysharafan.retrofitexample.R;
import com.evgeniysharafan.retrofitexample.http.model.Issue;
import com.evgeniysharafan.retrofitexample.util.AppUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class IssueAdapter extends RecyclerView.Adapter<IssueAdapter.ViewHolder> {

    private final List<Issue> issues;
    private Context context;

    public IssueAdapter(List<Issue> issues) {
        this.issues = issues;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null) {
            context = parent.getContext();
        }

        View v = LayoutInflater.from(context).inflate(R.layout.row_issue, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Issue issue = issues.get(position);

        AppUtils.loadImage(issue.user.avatar, holder.icon, true);

        holder.title.setText(issue.title);
        holder.body.setText(issue.body);

        holder.time.setText(String.valueOf(DateUtils.getRelativeTimeSpanString(issue.created.getTime(),
                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS, DateUtils.FORMAT_ABBREV_MONTH)));
    }

    @Override
    public int getItemCount() {
        return issues.size();
    }

    public List<Issue> getIssues() {
        return issues;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.icon)
        ImageButton icon;
        @InjectView(R.id.title)
        TextView title;
        @InjectView(R.id.body)
        TextView body;
        @InjectView(R.id.time)
        TextView time;

        ViewHolder(View v) {
            super(v);
            ButterKnife.inject(this, v);
        }
    }
}
