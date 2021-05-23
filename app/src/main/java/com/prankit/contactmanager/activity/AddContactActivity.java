package com.prankit.contactmanager.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.prankit.contactmanager.Data.DatabaseHandler;
import com.prankit.contactmanager.Model.Contact;
import com.prankit.contactmanager.R;

public class AddContactActivity extends AppCompatActivity {

    private TextInputEditText input_name, input_number;
    private Button addButton;
    private DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        Toolbar toolbar = findViewById(R.id.add_contact_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Contact");

        db = new DatabaseHandler(this);

        input_name = findViewById(R.id.input_name);
        input_number = findViewById(R.id.input_number);
        addButton = findViewById(R.id.add_contact_button);

        addButton.setOnClickListener(v -> addContact(input_name.getText().toString(), input_number.getText().toString()));
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
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}