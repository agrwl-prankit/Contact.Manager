package com.prankit.contactmanager.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.prankit.contactmanager.Data.DatabaseHandler;
import com.prankit.contactmanager.Model.Contact;
import com.prankit.contactmanager.R;

public class AddContactActivity extends AppCompatActivity {

    private TextInputEditText input_name, input_number;
    private Button addButton, deleteButton;
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
        deleteButton = findViewById(R.id.delete_contact_button);

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
            deleteButton.setVisibility(View.VISIBLE);
        }

        addButton.setOnClickListener(v -> {
            Log.i("btnTxt", addButton.getText().toString());
            if (addButton.getText().toString().equals("Update")) updateContact(get_id, input_name.getText().toString(), input_number.getText().toString());
            else addContact(input_name.getText().toString(), input_number.getText().toString());
        });

        deleteButton.setOnClickListener(v -> {
            deleteContact(get_id, input_name.getText().toString(), input_number.getText().toString());
        });
    }

    private void deleteContact(String id, String name, String number) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(AddContactActivity.this);
        dialog.setIcon(android.R.drawable.ic_dialog_alert);
        dialog.setMessage("Do you want to delete this contact?");
        dialog.setPositiveButton("Yes", (dialogInterface, i) -> {
            Contact contact = new Contact(Integer.parseInt(id), name, number);
            db.deleteContact(contact);
            Toast.makeText(this, "Contact deleted successfully", Toast.LENGTH_SHORT).show();
            goToMainActivity();
        }).setNegativeButton("No", (dialogInterface, i) -> {});
        dialog.create(); dialog.show();
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
            goToMainActivity();
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
            goToMainActivity();
            Toast.makeText(this, "Contact added successfully", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        goToMainActivity();
    }

    public void goToMainActivity(){
        startActivity(new Intent(AddContactActivity.this, MainActivity.class));
        finish();
    }
}