package com.jm.rajay.androidcodetestrajaybitter.Models;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;

/**
 * Created by rajay on 9/10/17.
 */

public class Email extends RealmObject implements Parcelable{

    private String email;

    public Email(){}

    public Email(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Email)) return false;

        Email email1 = (Email) o;

        return getEmail().equals(email1.getEmail());

    }

    @Override
    public int hashCode() {
        return getEmail().hashCode();
    }

    protected Email(Parcel in) {
        email = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(email);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Email> CREATOR = new Parcelable.Creator<Email>() {
        @Override
        public Email createFromParcel(Parcel in) {
            return new Email(in);
        }

        @Override
        public Email[] newArray(int size) {
            return new Email[size];
        }
    };
}
