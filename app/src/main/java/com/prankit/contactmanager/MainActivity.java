package com.prankit.contactmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.prankit.contactmanager.Data.DatabaseHandler;
import com.prankit.contactmanager.Model.Contact;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHandler db = new DatabaseHandler(this);

        // get total value
        // Int total = String.ValueOf(db.getContactCount());

        // insert
        //db.addContacts(new Contact("name","phone number"));

        // get 1 contact
        /*
        Contact c = new Contact(1);
        String log = " Name: " +  c.getName() " Phone: " + c.getPhoneNumber();
         */

        // get all contacts
        /*
        List<Contact> contactList = db.getAllContact();
        for (Contact c : contactList){
            String log = "Id: " + c.getId() + " Name: " +  c.getName() " Phone: " + c.getPhoneNumber();
        }
         */

        // update contact
        /*
        int c = db.updateContact();
         */
    }
}