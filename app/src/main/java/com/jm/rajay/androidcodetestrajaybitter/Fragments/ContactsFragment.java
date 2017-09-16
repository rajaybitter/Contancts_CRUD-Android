package com.jm.rajay.androidcodetestrajaybitter.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jm.rajay.androidcodetestrajaybitter.Models.Contact;
import com.jm.rajay.androidcodetestrajaybitter.Misc.MyContactRecyclerViewAdapter;
import com.jm.rajay.androidcodetestrajaybitter.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import io.realm.Realm;
import io.realm.RealmResults;


public class ContactsFragment extends Fragment {

    private ArrayList<Contact> contacts = new ArrayList<>();
    private RecyclerView contactsRecyclerView;
    private MyContactRecyclerViewAdapter adapter;

    public ContactsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //contacts = DatabaseHelper.getContactsFromDatabase();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);

        contactsRecyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
        contactsRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        //get the list of contacts here
        Realm realm =null;
        try {
           realm = Realm.getDefaultInstance();
            RealmResults<Contact> contactResults =realm.where(Contact.class).findAll();
            contacts = ((ArrayList<Contact>)(realm.copyFromRealm(contactResults)));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            realm.close();
        }

        Collections.sort(contacts, new Comparator<Contact>() {
            @Override
            public int compare(Contact o1, Contact o2) {
                int result = o1.getFirstName().compareTo(o2.getFirstName());
                int result2 = o1.getLastName().compareTo(o2.getLastName());

                if (result == 0){
                    return result2;
                }else{
                    return result;
                }
            }
        });
        adapter = new MyContactRecyclerViewAdapter(contacts, getContext(), this);
        contactsRecyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FloatingActionButton fab = (FloatingActionButton)view. findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new NewContactFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}
