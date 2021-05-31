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

//    private Button dateP;
//    private int mDay;
//    private int mMonth;
//    private int mYear;


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

//        dateP.setOnClickListener(v -> {
//            final Calendar c = Calendar.getInstance();
//            this.mYear = c.get(Calendar.YEAR);
//            this.mMonth = c.get(Calendar.MONTH);
//            this.mDay = c.get(Calendar.DAY_OF_MONTH);
//            showDialog(1);
//        });
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}