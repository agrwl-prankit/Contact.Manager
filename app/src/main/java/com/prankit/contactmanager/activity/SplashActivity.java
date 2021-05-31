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

    private boolean call = false, contact = false, doCall = false;

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
        if (call && contact && doCall){
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void checkContactPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SplashActivity.this, new String[]{Manifest.permission.READ_CONTACTS}, 1);
        } else {
            contact = true;
            checkCallLogPermission();
        }
    }

    private void checkCallLogPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SplashActivity.this, new String[]{Manifest.permission.READ_CALL_LOG}, 2);
        } else {
            call = true;
            checkCallPermission();
        }
    }

    public void checkCallPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SplashActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 3);
        } else {
            doCall = true;
            goToMainActivity();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
                checkCallPermission();
            } else {
                Toast.makeText(SplashActivity.this, "Call Log Permission Denied", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
        if (requestCode == 3) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                doCall = true;
                goToMainActivity();
            } else {
                Toast.makeText(SplashActivity.this, "Call Log Permission Denied", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}