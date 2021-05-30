package com.prankit.contactmanager.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prankit.contactmanager.Data.DatabaseHandler;
import com.prankit.contactmanager.Model.Contact;
import com.prankit.contactmanager.R;
import com.prankit.contactmanager.adapter.PhoneContactAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PhoneContactFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private ArrayList<Contact> contactList = new ArrayList<>();
    private DatabaseReference dbRef;
    private String phoneId;

    public PhoneContactFragment() {
        // Required empty public constructor
    }

    @SuppressLint("HardwareIds")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_phone_contact, container, false);
        dbRef = FirebaseDatabase.getInstance().getReference();
        phoneId = Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        recyclerView = view.findViewById(R.id.phone_contact_recycle_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(phoneId).child("Phone Contact").exists()) dbRef.child(phoneId).child("Phone Contact").removeValue();
                updateContactToServer();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && getContext().checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, 1);
        }
        getContact();
    }

        @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                Toast.makeText(getContext(), "Please provide permission to read contacts", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void updateContactToServer() {
        // pass all phone book to cursor
        Cursor cursor = getContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, null);
        // fetch all the contacts from cursor
        while (cursor.moveToNext()) {
            // pass data into string from cursor
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String num = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            // add data into array list
            Map<String, Object> map = new HashMap<>();
            map.put("name", name);
            map.put("number", num);
            DatabaseReference messageKeyRef = dbRef.child(phoneId).child("Phone Contact").push();
            String messagePushId = messageKeyRef.getKey();
            try {
                dbRef.child(phoneId).child("Phone Contact").child(messagePushId).updateChildren(map);
            } catch (Exception e) {
                Log.i("firebase exception", e.getMessage());
            }

        }
    }

    private void getContact() {
        contactList.clear();
        // pass all phone book to cursor
        Cursor cursor = getContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, null);
        // fetch all the contacts from cursor
        while (cursor.moveToNext()) {
            // pass data into string from cursor
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String num = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            // add data into array list
            Contact contact = new Contact(name, num);
            contactList.add(contact);
            adapter = new PhoneContactAdapter(getActivity(), contactList);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(layoutManager);
        }
    }
}