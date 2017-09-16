package com.jm.rajay.androidcodetestrajaybitter.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by rajay on 9/8/17.
 */

public class Contact extends RealmObject implements Parcelable{
    @PrimaryKey
    @Required
    private String id;

    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private RealmList<Number> numbers;
    private RealmList<Email> emails;
    private RealmList<Address> addresses;

    public Contact(){}

    public Contact(String firstName, String lastName, String dateOfBirth, RealmList<Number> numbers, RealmList<Email> emails, RealmList<Address> addresses) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.numbers = numbers;
        this.emails = emails;
        this.addresses = addresses;
    }

    public Contact(String firstName, String lastName, String dateOfBirth, RealmList<Number> numbers, RealmList<Email> emails) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.numbers = numbers;
        this.emails = emails;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public RealmList<Number> getNumbers() {
        return numbers;
    }

    public void setNumbers(RealmList<Number> numbers) {
        this.numbers = numbers;
    }

    public RealmList<Email> getEmails() {
        return emails;
    }

    public void setEmails(RealmList<Email> emails) {
        this.emails = emails;
    }

    public RealmList<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(RealmList<Address> addresses) {
        this.addresses = addresses;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void addNewAddress(Address address){
        if (!this.addresses.contains(address)) {
            this.addresses.add(address);
        }
    }

    public void addNewNumber(Number number){
        if (!numbers.contains(number)) {
            this.numbers.add(number);
        }
    }

    public void addNewEmail(Email email){
        if (!emails.contains(email)) {
            this.emails.add(email);
        }
    }

    public void deleteAddress(Address address){
        if (addresses.contains(address)) {
            this.addresses.remove(address);
        }
    }

    public void deleteNumber(Number number){
        if (numbers.contains(number)) {
            this.numbers.remove(number);
        }
    }

    public void deleteEmail(Email email){
        if (emails.contains(email)) {
            this.emails.remove(email);
        }
    }

    public void editAddress(Address oldAddress, Address newAddress){
        if (addresses.contains(oldAddress)) {
            int position = addresses.indexOf(oldAddress);
            addresses.set(position, newAddress);
        }
    }

    public void editNumber(Number oldNumber, Number newNumber){
        if (numbers.contains(oldNumber)) {
            int position = numbers.indexOf(oldNumber);
            numbers.set(position, newNumber);
        }
    }

    public void editEmail(Email oldEmail, Email newEmail){
        if (emails.contains(oldEmail)) {
            int position = emails.indexOf(oldEmail);
            emails.set(position, newEmail);
        }
    }

    public boolean hasAddress(){
        if(addresses.size() == 0){
            return false;
        }
        return true;
    }

    protected Contact(Parcel in) {
        id = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        dateOfBirth = in.readString();
        numbers = (RealmList) in.readValue(RealmList.class.getClassLoader());
        emails = (RealmList) in.readValue(RealmList.class.getClassLoader());
        addresses = (RealmList) in.readValue(RealmList.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(dateOfBirth);
        dest.writeValue(numbers);
        dest.writeValue(emails);
        dest.writeValue(addresses);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Contact> CREATOR = new Parcelable.Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };
}
