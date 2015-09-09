package com.evgeniysharafan.retrofitexample.http.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Issue implements Parcelable {

    public String title;
    public String body;
    public User user;
    @SerializedName("created_at")
    public Date created;

    public Issue() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.body);
        dest.writeParcelable(this.user, 0);
        dest.writeLong(created != null ? created.getTime() : -1);
    }

    protected Issue(Parcel in) {
        this.title = in.readString();
        this.body = in.readString();
        this.user = in.readParcelable(User.class.getClassLoader());
        long tmpCreated = in.readLong();
        this.created = tmpCreated == -1 ? null : new Date(tmpCreated);
    }

    public static final Creator<Issue> CREATOR = new Creator<Issue>() {
        public Issue createFromParcel(Parcel source) {
            return new Issue(source);
        }

        public Issue[] newArray(int size) {
            return new Issue[size];
        }
    };
}
