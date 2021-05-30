package com.prankit.contactmanager.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prankit.contactmanager.Data.DatabaseHandler;
import com.prankit.contactmanager.Model.Contact;
import com.prankit.contactmanager.R;
import com.prankit.contactmanager.activity.AddContactActivity;

import java.util.ArrayList;

public class PhoneContactAdapter extends RecyclerView.Adapter<PhoneContactAdapter.ViewHolder>{

    private Context context;
    private ArrayList<Contact> contactList;

    public PhoneContactAdapter(Context context, ArrayList<Contact> contactList) {
        this.context = context;
        this.contactList = contactList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_contact_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.name.setText(contactList.get(position).getName());
        holder.number.setText(contactList.get(position).getPhoneNumber());

    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView name, number;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.show_name);
            number = itemView.findViewById(R.id.show_mobile);
        }
    }
}
