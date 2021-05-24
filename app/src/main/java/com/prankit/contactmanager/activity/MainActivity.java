package com.prankit.contactmanager.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.prankit.contactmanager.Data.DatabaseHandler;
import com.prankit.contactmanager.Model.Contact;
import com.prankit.contactmanager.R;
import com.prankit.contactmanager.adapter.ShowContactAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    DatabaseHandler db;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private ArrayList<Contact> contactList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Contact Manager");

        db = new DatabaseHandler(this);

        recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        FloatingActionButton fab = findViewById(R.id.add_fab_button);
        fab.setOnClickListener(v -> startActivity(new Intent(this, AddContactActivity.class)));

    }

    @Override
    protected void onStart() {
        super.onStart();

        contactList.clear();
        List<Contact> list = db.getAllContact();
        if (!list.isEmpty()) {
            for (Contact c : list) {
                contactList.add(c);
                adapter = new ShowContactAdapter(MainActivity.this, contactList);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(layoutManager);
            }
        } else Toast.makeText(this, "No contact found", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}