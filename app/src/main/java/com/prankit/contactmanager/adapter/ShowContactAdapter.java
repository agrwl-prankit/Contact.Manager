package com.prankit.contactmanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prankit.contactmanager.Model.Contact;
import com.prankit.contactmanager.R;

import java.util.ArrayList;

public class ShowContactAdapter extends RecyclerView.Adapter<ShowContactAdapter.ViewHolder>{

    private Context context;
    private ArrayList<Contact> contactList;

    public ShowContactAdapter(Context context, ArrayList<Contact> contactList) {
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
        holder.itemView.setTag(contactList.get(position).getId());

        holder.name.setText(contactList.get(position).getName());
        holder.number.setText(contactList.get(position).getPhoneNumber());
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView name, number;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.show_name);
            number = itemView.findViewById(R.id.show_mobile);
        }
    }
}