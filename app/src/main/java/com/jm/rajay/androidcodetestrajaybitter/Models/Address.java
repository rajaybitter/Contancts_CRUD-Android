package com.jm.rajay.androidcodetestrajaybitter.Models;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;

/**
 * Created by rajay on 9/10/17.
 */

public class Address extends RealmObject implements Parcelable{
    private String address;

    public Address(){}

    public Address(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;

        Address address1 = (Address) o;

        return getAddress().equals(address1.getAddress());

    }

    @Override
    public int hashCode() {
        return getAddress().hashCode();
    }

    protected Address(Parcel in) {
        address = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(address);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Address> CREATOR = new Parcelable.Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel in) {
            return new Address(in);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };
}
