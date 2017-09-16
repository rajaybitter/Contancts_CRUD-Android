package com.jm.rajay.androidcodetestrajaybitter.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jm.rajay.androidcodetestrajaybitter.Models.Contact;
import com.jm.rajay.androidcodetestrajaybitter.Models.Email;
import com.jm.rajay.androidcodetestrajaybitter.Models.Number;
import com.jm.rajay.androidcodetestrajaybitter.Models.Address;
import com.jm.rajay.androidcodetestrajaybitter.R;

import io.realm.Realm;
import io.realm.exceptions.RealmException;

public class DetailedContactFragment extends Fragment {

    private Contact contact;
    private AppCompatTextView fullname;
    private AppCompatTextView dob;
    private AppCompatTextView numbers;
    private AppCompatTextView emails;
    private AppCompatTextView addresses;

    public DetailedContactFragment() {
        // Required empty public constructor
        contact = new Contact();
    }


    public static DetailedContactFragment newInstance(String param1) {
        DetailedContactFragment fragment = new DetailedContactFragment();
        Bundle args = new Bundle();
        args.putString("", param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Realm realm = null;
        try{
            Bundle bundle = getArguments();
            final String key = bundle.getString("id");
            realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    contact = realm.where(Contact.class).equalTo("id", key).findFirst();
                }
            });
        }catch (Exception e){
            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
        }finally {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detailed_contact, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fullname = (AppCompatTextView)getView().findViewById(R.id.contact_full_name);
        dob = (AppCompatTextView)getView().findViewById(R.id.contact_dob);
        numbers = (AppCompatTextView)getView().findViewById(R.id.contact_numbers);
        emails = (AppCompatTextView)getView().findViewById(R.id.contact_emails);
        addresses = (AppCompatTextView)getView().findViewById(R.id.contact_addresses);

        fullname.setText(contact.getFirstName()+" "+contact.getLastName());
        dob.setText(contact.getDateOfBirth());
        for(Number number: contact.getNumbers()){
            numbers.append(number.getNumber());
            numbers.append("\n");
        }
        for(Email email: contact.getEmails()){
            emails.append(email.getEmail());
            emails.append("\n");
        }
        if(contact.hasAddress()){
            for (Address address: contact.getAddresses()){
                addresses.append(address.getAddress());
                addresses.append("\n");
            }
        }

        AppCompatButton delete = (AppCompatButton)getView().findViewById(R.id.contact_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Realm realm = null;
                try{
                    realm = Realm.getDefaultInstance();
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            contact.deleteFromRealm();
                        }
                    });
                }catch (RealmException exception){
                    Log.e("Save contact", "Shit happened bruv");

                }finally {
                    realm.close();
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            }
        });

        AppCompatButton edit = (AppCompatButton)getView().findViewById(R.id.contact_edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("id", contact.getId());
                Fragment fragment = new EditContactFragment();
                fragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}
