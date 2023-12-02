package com.example.expensetracker;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TransectionAdapter extends RecyclerView.Adapter<TransectionAdapter.MyViewHolder>{

    Context context;

    public TransectionAdapter(Context context, ArrayList<TransectionModel> transectionModelArrayList) {
        this.context = context;
        this.transectionModelArrayList = transectionModelArrayList;
    }

    ArrayList<TransectionModel> transectionModelArrayList;
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_recycler_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TransectionModel model = transectionModelArrayList.get(position);
        holder.desc.setText(model.getDesc());
        holder.amount.setText(model.getAmount());
        holder.date.setText(model.getDate());
        String prt = model.getType();
        if(prt.equals("Expense")){
            holder.priority.setBackgroundResource(R.drawable.red_circular_shape);
            holder.amount.setTextColor(Color.parseColor("#ff0000"));
        }
        else{
            holder.amount.setTextColor(Color.parseColor("#55fa5d"));
            holder.priority.setBackgroundResource(R.drawable.green_circular_shape);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toUpdatePage = new Intent(context,updateActivity.class);
                toUpdatePage.putExtra("id",model.getId());
                toUpdatePage.putExtra("amount",model.getAmount());
                toUpdatePage.putExtra("desc",model.getDesc());
                toUpdatePage.putExtra("type",model.getType());
                context.startActivity(toUpdatePage);
            }
        });
    }

    @Override
    public int getItemCount() {
        return transectionModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView desc,amount,date;
        View priority;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            desc = itemView.findViewById(R.id.desc_history);
            amount = itemView.findViewById(R.id.amount_history);
            date = itemView.findViewById(R.id.date_history);
            priority = itemView.findViewById(R.id.priority_history);
        }
    }
}
