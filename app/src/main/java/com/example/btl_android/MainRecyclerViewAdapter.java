package com.example.btl_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.MyViewHolder> {
    private final MainRecyclerViewInterface recyclerViewInterface;
    Context context;
    ArrayList<MainCardModel> models;

    public MainRecyclerViewAdapter(Context context, ArrayList<MainCardModel> models,
                                   MainRecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.models = models;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public MainRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Giving a look to our rows
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_main, parent, false);
        return new MainRecyclerViewAdapter.MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MainRecyclerViewAdapter.MyViewHolder holder, int position) {
        // Assigning values to the views based on the posotion
        holder.textView.setText(models.get(position).getFunctions());
        holder.imageView.setImageResource(models.get(position).getIcons());
    }

    @Override
    public int getItemCount() {
        // Total number you want to display
        return models.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // Grabbing the views from the recycler_view_main layout file
        ImageView imageView;
        TextView textView;

        public MyViewHolder(@NonNull View itemView, MainRecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recyclerViewInterface != null) {
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION) {
                            recyclerViewInterface.onItemClick(pos);
                            
                        }
                    }
                }
            });
        }
    }
}
