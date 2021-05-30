package com.prankit.contactmanager.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
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
import com.prankit.contactmanager.adapter.TabAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

//    DatabaseHandler db;
//    private RecyclerView recyclerView;
//    private RecyclerView.LayoutManager layoutManager;
//    private RecyclerView.Adapter adapter;
//    private ArrayList<Contact> contactList = new ArrayList<>();
//    private DatabaseReference dbRef;
//    private String phoneId;
//    private boolean isDataExist = false;
//    private Button dateP;
//    private int mDay;
//    private int mMonth;
//    private int mYear;

    private static final int CONTACT_PERMISSION_CODE = 100;
    private static final int CALL_LOG_PERMISSION_CODE = 101;

    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Contact Manager");

        TabLayout tabLayout = findViewById(R.id.TabLayout);
        ViewPager viewPager = findViewById(R.id.ViewPager);
        tabLayout.addTab(tabLayout.newTab().setText("My Contacts"));
        tabLayout.addTab(tabLayout.newTab().setText("Phone Contacts"));
        tabLayout.addTab(tabLayout.newTab().setText("Call Log"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final TabAdapter adapter = new TabAdapter(this, getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

//        db = new DatabaseHandler(this);
//        dbRef = FirebaseDatabase.getInstance().getReference();
//        phoneId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
//
//        dateP = findViewById(R.id.datePicker);
//        recyclerView = findViewById(R.id.recycle_view);
//        recyclerView.setHasFixedSize(true);
//        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//

//
//        FloatingActionButton fab = findViewById(R.id.add_fab_button);
//        fab.setOnClickListener(v -> startActivity(new Intent(this, AddContactActivity.class)));
//        dateP.setOnClickListener(v -> {
//            final Calendar c = Calendar.getInstance();
//            this.mYear = c.get(Calendar.YEAR);
//            this.mMonth = c.get(Calendar.MONTH);
//            this.mDay = c.get(Calendar.DAY_OF_MONTH);
//            showDialog(1);
//        });
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
//                checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){
//            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, 1);
//        }

        //getCallDetails();

    }

//    @Override
//    protected Dialog onCreateDialog(int id) {
//        if (id == 1) {
//            DatePickerDialog datePickerDialog = new DatePickerDialog(this, datePickerListener, mYear, mMonth, mDay);
//            Calendar calendar = Calendar.getInstance();
//            calendar.add(Calendar.DATE, 0);
//            Date newDate = calendar.getTime();
//            datePickerDialog.getDatePicker().setMaxDate(newDate.getTime() - (newDate.getTime() % (24 * 60 * 60 * 1000)));
//            return datePickerDialog;
//        }
//        return super.onCreateDialog(id);
//    }
//
//    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
//        @Override
//        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//            dateP.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
//            getCallDetails(dayOfMonth, monthOfYear, year);
//        }
//    };
//
//    @RequiresApi(api = Build.VERSION_CODES.M)
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == 1) {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            } else {
//                Toast.makeText(this, "Please provide permission to write contacts", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        contactList.clear();
//        List<Contact> list = db.getAllContact();
//        if (!list.isEmpty()) {
//            for (Contact c : list) {
//                contactList.add(c);
//                adapter = new ShowContactAdapter(MainActivity.this, contactList);
//                recyclerView.setAdapter(adapter);
//                recyclerView.setLayoutManager(layoutManager);
//            }
//        } else {
//            adapter = new ShowContactAdapter(MainActivity.this, contactList);
//            recyclerView.setAdapter(adapter);
//            recyclerView.setLayoutManager(layoutManager);
//            Toast.makeText(this, "No contact found", Toast.LENGTH_SHORT).show();
//        }
//    }
//

//
//    public void getCallDetails(int day, int month, int year){
//        Cursor managedCursor = getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, null);
//        int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
//        int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
//        int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
//        int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
//        Log.i("callLogCount", ""+managedCursor.getCount());
//        while (managedCursor.moveToNext()){
//            String phNumber = managedCursor.getString( number );
//            String callType = managedCursor.getString( type );
//            Date callDayTime = new Date(Long.parseLong(managedCursor.getString( date )));
//            Log.i("callLog", callDayTime.toString());
//            Log.i("callLog 1", day+"/"+month+"/"+year);
//            Log.i("callLog 2", callDayTime.getDate() + "/" + callDayTime.getMonth() + "/" + callDayTime.toString().substring(callDayTime.toString().length() - 4));
//            //callDayTime.toString().substring(callDayTime.toString().length() - 4)
//            if (callDayTime.getDate() == day && callDayTime.getMonth() == month) {
//                String callDuration = managedCursor.getString(duration);
//                String dir = null;
//                int dircode = Integer.parseInt(callType);
//                switch (dircode) {
//                    case CallLog.Calls.OUTGOING_TYPE:
//                        dir = "OUTGOING";
//                        break;
//
//                    case CallLog.Calls.INCOMING_TYPE:
//                        dir = "INCOMING";
//                        break;
//
//                    case CallLog.Calls.MISSED_TYPE:
//                        dir = "MISSED";
//                        break;
//                }
//                Log.i("callLogList", phNumber + "-" + callDayTime);
//            }
//        }
//        managedCursor.close();
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}