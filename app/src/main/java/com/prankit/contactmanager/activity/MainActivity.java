package com.prankit.contactmanager.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.prankit.contactmanager.R;
import com.prankit.contactmanager.adapter.TabAdapter;

public class MainActivity extends AppCompatActivity {

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