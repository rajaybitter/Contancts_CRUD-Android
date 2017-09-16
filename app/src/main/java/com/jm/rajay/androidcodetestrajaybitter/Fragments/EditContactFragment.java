package com.jm.rajay.androidcodetestrajaybitter.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jm.rajay.androidcodetestrajaybitter.Models.Address;
import com.jm.rajay.androidcodetestrajaybitter.Models.Contact;
import com.jm.rajay.androidcodetestrajaybitter.Models.Email;
import com.jm.rajay.androidcodetestrajaybitter.Models.Number;
import com.jm.rajay.androidcodetestrajaybitter.R;

import java.util.ArrayList;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.exceptions.RealmException;


public class EditContactFragment extends Fragment {

    private Contact contact;

    private TextInputEditText fname;
    private TextInputEditText lname;
    private TextInputEditText dob;

    private LinearLayout numberGroup;
    private LinearLayout emailGroup;
    private LinearLayout addressGroup;

    private ArrayList<TextInputEditText> numberList;
    private ArrayList<TextInputEditText> emailList;
    private ArrayList<TextInputEditText> addressList;

    private RelativeLayout numbersLayout;
    private RelativeLayout emailsLayout;
    private RelativeLayout addressesLayout;
    private TextView error;

    public EditContactFragment() {
        // Required empty public constructor
    }

    public static EditContactFragment newInstance() {
        EditContactFragment fragment = new EditContactFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        numberList = new ArrayList<>();
        emailList = new ArrayList<>();
        addressList = new ArrayList<>();
        final String key = getArguments().getString("id");
        Realm realm = null;
        try{
            realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    contact = realm.where(Contact.class).equalTo("id", key).findFirst();
                }
            });
        }catch (Exception E){
            Toast.makeText(getActivity(), "Ooops something went wrong!", Toast.LENGTH_LONG).show();
        }finally {
            realm.close();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_contact, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fname = (TextInputEditText)getView().findViewById(R.id.first_name_edit);
        lname = (TextInputEditText)getView().findViewById(R.id.last_name_edit);
        dob = (TextInputEditText)getView().findViewById(R.id.dob_edit);

        numberGroup = (LinearLayout)getView().findViewById(R.id.number_edit_container);
        emailGroup = (LinearLayout)getView().findViewById(R.id.email_edit_container);
        addressGroup = (LinearLayout)getView().findViewById(R.id.address_edit_container);

        numbersLayout = (RelativeLayout)getView().findViewById(R.id.number_container);
        emailsLayout = (RelativeLayout)getView().findViewById(R.id.emails_container);
        addressesLayout = (RelativeLayout)getView().findViewById(R.id.addresses_container);

        error = (TextView)getView().findViewById(R.id.edit_error);

        fname.setText(contact.getFirstName());
        lname.setText(contact.getLastName());
        dob.setText(contact.getDateOfBirth());

        for(Number number: contact.getNumbers()){
            TextInputEditText temp = new TextInputEditText(getActivity());

            temp.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            temp.setHint(R.string.phone_number);
            numberGroup.addView(temp);
            temp.setText(number.getNumber());
            numberList.add(temp);
        }

        for(Email email: contact.getEmails()){
            TextInputEditText temp = new TextInputEditText(getActivity());

            temp.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            temp.setHint(R.string.email);
            emailGroup.addView(temp);
            temp.setText(email.getEmail());
            emailList.add(temp);
        }

        emailsLayout.requestLayout();

        if (contact.hasAddress()) {
            for(Address address: contact.getAddresses()){
                TextInputEditText temp = new TextInputEditText(getActivity());

                temp.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                temp.setHint(R.string.address);
                addressGroup.addView(temp);
                temp.setText(address.getAddress());
                addressList.add(temp);
            }
        }else{
            TextInputEditText temp = new TextInputEditText(getActivity());

            temp.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            temp.setHint(R.string.address);
            addressGroup.addView(temp);
            addressList.add(temp);
        }

        FloatingActionButton fab = (FloatingActionButton)getView().findViewById(R.id.edit_contact_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateInputs()){
                    storeInputs();
                }
            }
        });

        Button numberButton = (Button)getView().findViewById(R.id.add_number);
        numberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputEditText temp = new TextInputEditText(getActivity());

                temp.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                temp.setHint(R.string.phone_number);
                numberGroup.addView(temp);
                numberList.add(temp);
            }
        });

        Button emailButton = (Button)getView().findViewById(R.id.add_email);
        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputEditText temp = new TextInputEditText(getActivity());

                temp.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                temp.setHint(R.string.email);
                emailGroup.addView(temp);
                emailList.add(temp);
            }
        });

        Button addressButton = (Button)getView().findViewById(R.id.add_address);
        addressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputEditText temp = new TextInputEditText(getActivity());

                temp.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                temp.setHint(R.string.address);
                addressGroup.addView(temp);
                addressList.add(temp);
            }
        });
    }

    public boolean validateInputs() {
        Boolean bool = false;
        if (fname.getText().toString().matches(".*\\d+.*")) {
            error.setText("First name cannot contain any digits!");
            fname.setTextColor(getResources().getColor(R.color.colorAccent));
            return false;
        }
        if (fname.getText().toString().equals("")) {
            error.setText("Please enter first name!");
            fname.setTextColor(getResources().getColor(R.color.colorAccent));
            return false;
        }
        if (!fname.getText().toString().matches(".*[a-zA-Z]+.*")) {
            error.setText("Please enter a valid first name!");
            fname.setTextColor(getResources().getColor(R.color.colorAccent));
            return false;
        }
        if (lname.getText().toString().matches(".*\\d+.*")) {
            error.setText("Last name cannot contain any digits!");
            lname.setTextColor(getResources().getColor(R.color.colorAccent));
            return false;
        }
        if (lname.getText().toString().equals("")) {
            error.setText("Please enter first name!");
            lname.setTextColor(getResources().getColor(R.color.colorAccent));
            return false;
        }
        if (!lname.getText().toString().matches(".*[a-zA-Z]+.*")) {
            error.setText("Please enter a valid last name!");
            lname.setTextColor(getResources().getColor(R.color.colorAccent));
            return false;
        }
        if (dob.getText().toString().isEmpty()) {
            error.setText("Please enter a date of birth!");
            dob.setTextColor(getResources().getColor(R.color.colorAccent));
            return false;
        }

        for (TextInputEditText number : numberList) {
            if(!number.getText().toString().isEmpty()){
                return true;
            }
        }
        if(!bool){
            error.setText("Please enter a phone number");
            return false;
        }
        bool = false;
        for (TextInputEditText email : emailList) {
            if(!email.getText().toString().isEmpty()){
                return true;
            }
        }
        if(!bool){
            error.setText("Please enter an email address");
            return false;
        }
        return true;
    }

    public void storeInputs(){

        Realm realm = null;
        try{
            realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RealmList<Number> numbers = new RealmList<>();
                    RealmList<Email> emails = new RealmList<>();
                    RealmList<Address> addresses = new RealmList<>();

                    contact.setFirstName(fname.getText().toString());
                    contact.setLastName(lname.getText().toString());
                    contact.setDateOfBirth(dob.getText().toString());

                    for(TextInputEditText number : numberList){
                        if(!number.getText().toString().isEmpty()){
                            String numberString = number.getText().toString();
                            numbers.add(realm.copyToRealm(new Number(numberString)));
                        }
                    }
                    contact.setNumbers(numbers);

                    for(TextInputEditText email : emailList){
                        if(!email.getText().toString().isEmpty()){
                            String emailString = email.getText().toString();
                            emails.add(realm.copyToRealm(new Email(emailString)));
                        }
                    }
                    contact.setEmails(emails);

                    for(TextInputEditText address : addressList){
                        if(!address.getText().toString().isEmpty()){
                            String addressString = address.getText().toString();
                            addresses.add(realm.copyToRealm(new Address(addressString)));
                        }
                    }
                    contact.setAddresses(addresses);
                }
            });
        }catch (RealmException exception){
            Log.e("Save contact", "Shit happened bruv");

        }finally {
            realm.close();
            getActivity().getSupportFragmentManager().popBackStack();
        }
    }
}
