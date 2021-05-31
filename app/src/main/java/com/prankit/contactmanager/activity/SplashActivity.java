package com.prankit.contactmanager.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.prankit.contactmanager.R;

public class SplashActivity extends AppCompatActivity {

    private boolean call = false, contact = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {
            checkContactPermission();
        }, 2000);

    }

    private void goToMainActivity() {
        Log.i("splashPerm",call + " goto " + contact);
        if (call && contact){
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void checkCallLogPermission() {
        Log.i("splashPerm","call check");
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SplashActivity.this, new String[]{Manifest.permission.READ_CALL_LOG}, 2);
        } else {
            call = true;
            goToMainActivity();
        }
    }

    private void checkContactPermission() {
        Log.i("splashPerm","contact check");
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SplashActivity.this, new String[]{Manifest.permission.READ_CONTACTS}, 1);
        } else {
            contact = true;
            checkCallLogPermission();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.i("splashPerm", String.valueOf(requestCode));
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                contact = true;
                checkCallLogPermission();
            } else {
                Toast.makeText(SplashActivity.this, "Contact Permission Denied", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
        if (requestCode == 2) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                call = true;
                goToMainActivity();
            } else {
                Toast.makeText(SplashActivity.this, "Call Log Permission Denied", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}