package com.prankit.contactmanager.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.prankit.contactmanager.Data.DatabaseHandler;
import com.prankit.contactmanager.Model.Contact;
import com.prankit.contactmanager.R;

public class AddContactActivity extends AppCompatActivity {

    private TextInputEditText input_name, input_number;
    private Button addButton;
    private DatabaseHandler db;
    private String get_name;
    private String get_id;
    private String get_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        db = new DatabaseHandler(this);
        input_name = findViewById(R.id.input_name);
        input_number = findViewById(R.id.input_number);
        addButton = findViewById(R.id.add_contact_button);

        Toolbar toolbar = findViewById(R.id.add_contact_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Contact");

        if (getIntent().hasExtra("id")){
            getSupportActionBar().setTitle("Update Contact");
            addButton.setText("Update");
            get_id = getIntent().getStringExtra("id");
            Log.i("ididid", "" + get_id);
            get_name = getIntent().getStringExtra("name");
            get_number = getIntent().getStringExtra("number");
            input_name.setText(get_name);
            input_number.setText(get_number);
        }

        addButton.setOnClickListener(v -> {
            Log.i("btnTxt", addButton.getText().toString());
            if (addButton.getText().toString().equals("Update")) updateContact(get_id, input_name.getText().toString(), input_number.getText().toString());
            else addContact(input_name.getText().toString(), input_number.getText().toString());
        });
    }

    private void updateContact(String id, String name, String number) {
        if (name.equals("") && number.equals("")) {
            input_name.setError("Required");
            input_number.setError("Required");
        } else if (name.equals("")) input_name.setError("Required");
        else if (number.equals("")) input_number.setError("Required");
        else {
            Contact contact = new Contact(Integer.parseInt(id), name, number);
            db.updateContact(contact);
            Toast.makeText(this, "Contact update successfully", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void addContact(String name, String number) {
        if (name.equals("") && number.equals("")) {
            input_name.setError("Required");
            input_number.setError("Required");
        } else if (name.equals("")) input_name.setError("Required");
        else if (number.equals("")) input_number.setError("Required");
        else {
            Contact addContact = new Contact(name, number);
            db.addContacts(addContact);
            Toast.makeText(this, "Contact added successfully", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}