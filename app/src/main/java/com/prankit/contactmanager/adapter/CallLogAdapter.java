package com.prankit.contactmanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prankit.contactmanager.Model.CallLogModel;
import com.prankit.contactmanager.R;

import java.util.ArrayList;

public class CallLogAdapter extends RecyclerView.Adapter<CallLogAdapter.ViewHolder> {

    private Context context;
    private ArrayList<CallLogModel> callLogArrayList;

    public CallLogAdapter(Context context, ArrayList<CallLogModel> callLogArrayList) {
        this.context = context;
        this.callLogArrayList = callLogArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_contact_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.show_name.setVisibility(View.GONE);
        holder.show_num.setVisibility(View.GONE);

        holder.name.setText(callLogArrayList.get(position).getNumber());
        holder.number.setText(callLogArrayList.get(position).getTime() + " " +
                callLogArrayList.get(position).getType() + " " + callLogArrayList.get(position).getDate()+"/"+
                callLogArrayList.get(position).getMonth()+"/"+callLogArrayList.get(position).getYear());
    }

    @Override
    public int getItemCount() {
        return callLogArrayList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView name, number, show_name, show_num;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.show_name);
            number = itemView.findViewById(R.id.show_mobile);
            show_name = itemView.findViewById(R.id.show_name_textView);
            show_num = itemView.findViewById(R.id.show_number_textView);
        }
    }
}
