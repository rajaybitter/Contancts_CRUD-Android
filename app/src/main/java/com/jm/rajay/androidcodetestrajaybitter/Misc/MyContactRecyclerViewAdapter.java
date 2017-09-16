package com.jm.rajay.androidcodetestrajaybitter.Misc;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jm.rajay.androidcodetestrajaybitter.Activities.ContactsActivity;
import com.jm.rajay.androidcodetestrajaybitter.Fragments.ContactsFragment;
import com.jm.rajay.androidcodetestrajaybitter.Fragments.DetailedContactFragment;
import com.jm.rajay.androidcodetestrajaybitter.Models.Contact;
import com.jm.rajay.androidcodetestrajaybitter.R;

import java.util.ArrayList;

public class MyContactRecyclerViewAdapter extends RecyclerView.Adapter<MyContactRecyclerViewAdapter.Holder> {

    private final ArrayList<Contact> contacts;
    private Context context;
    private ContactsFragment fragment;

    public MyContactRecyclerViewAdapter(ArrayList<Contact> items, Context context, ContactsFragment fragment) {
        this.contacts = items;
        this.context = context;
        this.fragment = fragment;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(final Holder holder, int position) {
        final Contact contact = contacts.get(position);
        holder.name.setText(contact.getFirstName() + " " + contact.getLastName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailedContactFragment newFragment = new DetailedContactFragment();
                Bundle args = new Bundle();
                args.putString("id", contact.getId());
                newFragment.setArguments(args);
                FragmentTransaction transaction = fragment.getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        public final TextView name;

        public Holder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.contact_name);
        }
    }
}
