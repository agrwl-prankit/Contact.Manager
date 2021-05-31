package com.prankit.contactmanager.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.CallLog;
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
import com.prankit.contactmanager.Model.CallLogModel;
import com.prankit.contactmanager.R;
import com.prankit.contactmanager.adapter.CallLogAdapter;

import java.util.ArrayList;
import java.util.Date;

public class CallLogFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private ArrayList<CallLogModel> contactList = new ArrayList<>();
    private DatabaseReference dbRef;
    private String phoneId;

    public CallLogFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_call_log, container, false);
        dbRef = FirebaseDatabase.getInstance().getReference();
        phoneId = Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        recyclerView = view.findViewById(R.id.call_log_recycle_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(phoneId).child("Call Log").exists())
                    dbRef.child(phoneId).child("Phone Contact").removeValue();
                //updateContactToServer();
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && getContext().checkSelfPermission(Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CALL_LOG}, 1);
        }
        getCallDetails();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                Toast.makeText(getContext(), "Please provide permission to read call log", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void getCallDetails() {
        contactList.clear();
        Cursor managedCursor = getContext().getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, null);
        int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
        while (managedCursor.moveToNext()) {
            String callType = managedCursor.getString(type);
//            if (callDayTime.getDate() == day && callDayTime.getMonth() == month) {
            String myType = null;
            int dircode = Integer.parseInt(callType);
            switch (dircode) {
                case CallLog.Calls.OUTGOING_TYPE:
                    myType = "OUTGOING";
                    break;

                case CallLog.Calls.INCOMING_TYPE:
                    myType = "INCOMING";
                    break;

                case CallLog.Calls.MISSED_TYPE:
                    myType = "MISSED";
                    break;
            }
            String callNum = managedCursor.getString(number);
            String callDuration = managedCursor.getString(duration);
            Date callDayTime = new Date(Long.parseLong(managedCursor.getString(date)));
            String callTime = callDayTime.toString().substring(11,17);
//            String callDate = String.valueOf(callDayTime.getDate());
//            String callMonth = String.valueOf(callDayTime.getMonth() + 1);
//            String callYear = callDayTime.toString().substring(callDayTime.toString().length() - 4);
            CallLogModel logModel = new CallLogModel(callNum, callTime, callDuration, myType);
            contactList.add(logModel);
            adapter = new CallLogAdapter(getActivity(), contactList);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(layoutManager);
        }
        managedCursor.close();
    }
}