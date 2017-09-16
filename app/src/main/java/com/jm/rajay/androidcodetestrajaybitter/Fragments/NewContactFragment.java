package com.jm.rajay.androidcodetestrajaybitter.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class NewContactFragment extends Fragment {

    TextInputEditText firstname;
    TextInputEditText lastname;
    TextInputEditText dob;
    TextInputEditText number;
    TextInputEditText email;
    TextInputEditText address;
    TextView error;

    public NewContactFragment() {
        // Required empty public constructor
    }

    public static NewContactFragment newInstance(String param1) {
        NewContactFragment fragment = new NewContactFragment();
        Bundle args = new Bundle();
        args.putString("", param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_contact, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        firstname = (TextInputEditText) getView().findViewById(R.id.first_name_input);
        lastname = (TextInputEditText) getView().findViewById(R.id.last_name_input);
        dob = (TextInputEditText) getView().findViewById(R.id.dob_input);
        number = (TextInputEditText) getView().findViewById(R.id.phone_number_input);
        email = (TextInputEditText) getView().findViewById(R.id.email_input);
        address = (TextInputEditText) getView().findViewById(R.id.address_input);
        error = (TextView)getView().findViewById(R.id.error_message);
        FloatingActionButton fab = (FloatingActionButton)getView().findViewById(R.id.new_contact_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validInputs()){
                    final Contact contact;
                    RealmList<Number> numberArray = new RealmList<Number>();
                    RealmList<Email> emailArray = new RealmList<Email>();
                    RealmList<Address> addressArray = new RealmList<Address>();
                    if(address.getText().toString().isEmpty()){
                        numberArray.add(new Number(number.getText().toString()));
                        emailArray.add(new Email(email.getText().toString()));
                        contact = new Contact(firstname.getText().toString(),
                                lastname.getText().toString(),
                                dob.getText().toString(),
                                numberArray,
                                emailArray);
                    }else{
                        numberArray.add(new Number(number.getText().toString()));
                        emailArray.add(new Email(email.getText().toString()));
                        addressArray.add(new Address(address.getText().toString()));
                        contact = new Contact(firstname.getText().toString(),
                                lastname.getText().toString(),
                                dob.getText().toString(),
                                numberArray,
                                emailArray,
                                addressArray);
                    }
                    Realm realm = null;
                    try{
                        realm = Realm.getDefaultInstance();
                        contact.setId(UUID.randomUUID().toString());
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                realm.copyToRealmOrUpdate(contact);
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
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public boolean validInputs(){
        if(firstname.getText().toString().matches(".*\\d+.*")){
            error.setText("First name cannot contain any digits!");
            return false;
        }
        if(firstname.getText().toString().equals("")){
            error.setText("Please enter first name!");
            return false;
        }
        if(!firstname.getText().toString().matches(".*[a-zA-Z]+.*")){
            error.setText("Please enter a valid first name!");
            return false;
        }
        if(lastname.getText().toString().matches(".*\\d+.*")){
            error.setText("Last name cannot contain any digits!");
            return false;
        }
        if(lastname.getText().toString().equals("")){
            error.setText("Please enter first name!");
            return false;
        }
        if(!lastname.getText().toString().matches(".*[a-zA-Z]+.*")){
            error.setText("Please enter a valid last name!");
            return false;
        }
        if(dob.getText().toString().isEmpty()){
            error.setText("Please enter a date of birth!");
            return false;
        }
        if(email.getText().toString().isEmpty()){
            error.setText("Please enter an email address");
            return false;
        }
        if(number.getText().toString().isEmpty()){
            error.setText("Please enter a phone number");
            return false;
        }
        return true;
    }
}
