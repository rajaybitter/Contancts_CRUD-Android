package com.jm.rajay.androidcodetestrajaybitter.Models;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;

/**
 * Created by rajay on 9/9/17.
 */

public class Number extends RealmObject implements Parcelable{

    private String number;

    public Number(){

    }

    public Number(String address) {
        this.number = address;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Number)) return false;

        Number number = (Number) o;

        return getNumber().equals(number.getNumber());

    }

    @Override
    public int hashCode() {
        return getNumber().hashCode();
    }

    protected Number(Parcel in) {
        number = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(number);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Number> CREATOR = new Parcelable.Creator<Number>() {
        @Override
        public Number createFromParcel(Parcel in) {
            return new Number(in);
        }

        @Override
        public Number[] newArray(int size) {
            return new Number[size];
        }
    };
}
