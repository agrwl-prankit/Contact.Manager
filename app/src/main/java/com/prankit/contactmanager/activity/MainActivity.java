package com.prankit.contactmanager.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prankit.contactmanager.Data.DatabaseHandler;
import com.prankit.contactmanager.Model.Contact;
import com.prankit.contactmanager.R;
import com.prankit.contactmanager.adapter.ShowContactAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    DatabaseHandler db;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private ArrayList<Contact> contactList = new ArrayList<>();
    private DatabaseReference dbRef;
    private String phoneId;
    private boolean isDataExist = false;

    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Contact Manager");

        db = new DatabaseHandler(this);
        dbRef = FirebaseDatabase.getInstance().getReference();
        phoneId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                isDataExist = snapshot.child(phoneId).exists();
                if (!isDataExist) getContact();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FloatingActionButton fab = findViewById(R.id.add_fab_button);
        fab.setOnClickListener(v -> startActivity(new Intent(this, AddContactActivity.class)));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, 1);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                Toast.makeText(this, "Please provide permission to write contacts", Toast.LENGTH_SHORT).show();
            }
        }
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
        } else {
            adapter = new ShowContactAdapter(MainActivity.this, contactList);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(layoutManager);
            Toast.makeText(this, "No contact found", Toast.LENGTH_SHORT).show();
        }
    }

    private void getContact() {
        contactList.clear();
        // pass all phone book to cursor
        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, null);
        // fetch all the contacts from cursor
        while (cursor.moveToNext()){
            // pass data into string from cursor
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String num = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            // add data into array list
            Map<String, Object> map = new HashMap<>();
            map.put("name", name);
            map.put("number", num);
            DatabaseReference messageKeyRef = dbRef.child(phoneId).push();
            String messagePushId = messageKeyRef.getKey();
            try {
                dbRef.child(phoneId).child(messagePushId).updateChildren(map);
            } catch (Exception e) {
               Log.i("firebase exception", e.getMessage());
            }

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}