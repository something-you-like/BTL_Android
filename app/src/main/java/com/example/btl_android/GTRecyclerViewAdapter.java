package com.example.btl_android;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GTRecyclerViewAdapter extends RecyclerView.Adapter<GTRecyclerViewAdapter.MyViewHolder> {

    Context context;
    Activity activity;
    List<GTModel> gtlist;

    public GTRecyclerViewAdapter(Context context, Activity activity, List<GTModel> gtlist) {
        this.context = context;
        this.activity = activity;
        this.gtlist = gtlist;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_global_time, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textView.setText(gtlist.get(position).getArea());
        holder.textClock.setTimeZone(gtlist.get(position).getArea());
    }

    @Override
    public int getItemCount() {
        return gtlist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ConstraintLayout constraintLayout;
        TextClock textClock;
        TextView textView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textClock = itemView.findViewById(R.id.timezoneTime);
            textView = itemView.findViewById(R.id.timezoneArea);
            constraintLayout = itemView.findViewById(R.id.timezoneCard);
        }
    }

    public List<GTModel> getGtlist() {
        return gtlist;
    }

    public void removeTimezone(int position) {
        gtlist.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreTimezone(GTModel item, int position) {
        gtlist.add(position, item);
        notifyItemInserted(position);
    }

}
