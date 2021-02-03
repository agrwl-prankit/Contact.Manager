package com.prankit.contactmanager.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.prankit.contactmanager.Model.Contact;
import com.prankit.contactmanager.Util.Util;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    public DatabaseHandler(Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    // create table
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_CONTACT_TABLE = "CREATE TABLE " + Util.TABLE_NAME + "("
                + Util.KEY_ID + " INTEGER PRIMARY KEY," + Util.KEY_NAME + " TEXT,"
                + Util.KEY_PHONE_NUMBER + " TEXT)";

        db.execSQL(CREATE_CONTACT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // first delete previous table
        db.execSQL("DROP TABLE IF EXISTS " + Util.TABLE_NAME);

        // create table again
        onCreate(db);
    }

    // CRUD Operations - CREATE, READ, UPDATE, TABLE

    public void addContacts(Contact contact){
        // make the database allow to write
        SQLiteDatabase db = this.getWritableDatabase();
        // put the data into ContentValue object
        ContentValues values = new ContentValues();
        values.put(Util.KEY_NAME, contact.getName());
        values.put(Util.KEY_PHONE_NUMBER, contact.getPhoneNumber());
        // insert to row
        db.insert(Util.TABLE_NAME, null, values);
        // now close the database
        db.close();
    }

    public Contact getContact(int id){
        // make the database allow to read
        SQLiteDatabase db = this.getReadableDatabase();
        // looping on each row through cursor
        // pass table_name, string array of all athe item that we want to retrieve
        Cursor cursor = db.query(Util.TABLE_NAME, new String[]{Util.KEY_ID, Util.KEY_NAME, Util.KEY_PHONE_NUMBER},
                Util.KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        // pass cursor data to contact object
        Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        return contact;
    }

    public List<Contact> getAllContact(){
        // make the database allow to read
        SQLiteDatabase db = this.getReadableDatabase();
        List<Contact> contactList = new ArrayList<>();
        // select all contacts
        String selectAll = "SELECT * FROM " + Util.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectAll, null);
        // loop through all contacts
        if (cursor.moveToFirst()){
            do {
                Contact contact = new Contact();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhoneNumber(cursor.getString(2));
                // add contact object to our list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        return contactList;
    }

    public int updateContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Util.KEY_NAME, contact.getName());
        values.put(Util.KEY_PHONE_NUMBER, contact.getPhoneNumber());
        // update in database and return the primary key
        return db.update(Util.TABLE_NAME, values, Util.KEY_ID + "=?",
                new String[]{String.valueOf(contact.getId())});
    }

}
