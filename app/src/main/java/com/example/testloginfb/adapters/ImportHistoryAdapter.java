package com.example.testloginfb.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.testloginfb.R;
import com.example.testloginfb.models.Material;
import com.example.testloginfb.models.Transaction;

import java.util.List;

public class ImportHistoryAdapter extends RecyclerView.Adapter<ImportHistoryAdapter.MyViewHolder> {
    private  Context mContext;
    private List<Transaction> mList;

    @NonNull
    @Override
    public ImportHistoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_item_history, viewGroup, false);

        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull ImportHistoryAdapter.MyViewHolder myViewHolder, int i) {
        if(mList != null){
            myViewHolder.txtId.setText(mList.get(i).getId());
            myViewHolder.txtStaff.setText(mList.get(i).getStaff().getDetailName());
            myViewHolder.txtDate.setText("Ngày " + mList.get(i).getDateTime().getDate() + "lúc " + mList.get(i).getTime());
        }
    }

    @Override
    public int getItemCount() {
        return (mList !=null) ? mList.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtId, txtStaff, txtDate;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtId = (TextView) itemView.findViewById(R.id.nameMaterial);
            txtStaff = (TextView) itemView.findViewById(R.id.employeeId);
            txtDate = (TextView) itemView.findViewById(R.id.dateTime);
        }
    }
}
