package com.prankit.contactmanager.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.prankit.contactmanager.Data.DatabaseHandler;
import com.prankit.contactmanager.Model.Contact;
import com.prankit.contactmanager.R;
import com.prankit.contactmanager.activity.AddContactActivity;
import com.prankit.contactmanager.adapter.ShowContactAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyContactFragment extends Fragment {

    private DatabaseHandler db;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private ArrayList<Contact> contactList = new ArrayList<>();

    public MyContactFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_contact, container, false);
        db = new DatabaseHandler(getContext());
        recyclerView = view.findViewById(R.id.my_contact_recycle_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        FloatingActionButton fab = view.findViewById(R.id.add_fab_button);
        fab.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), AddContactActivity.class));
            getActivity().finish();
        });
        getContact();
        return view;
    }

    public void getContact(){
        contactList.clear();
        List<Contact> list = db.getAllContact();
        if (!list.isEmpty()) {
            for (Contact c : list) {
                contactList.add(c);
                adapter = new ShowContactAdapter(getContext(), contactList);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(layoutManager);
            }
        } else {
            adapter = new ShowContactAdapter(getContext(), contactList);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(layoutManager);
        }
    }

}