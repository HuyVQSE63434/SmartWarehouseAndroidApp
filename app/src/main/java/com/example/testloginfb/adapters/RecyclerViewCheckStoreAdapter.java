package com.example.testloginfb.adapters;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.testloginfb.R;
import com.example.testloginfb.models.CheckStore;
import com.example.testloginfb.models.Transaction;

import java.util.List;

public class RecyclerViewCheckStoreAdapter extends RecyclerView.Adapter<RecyclerViewCheckStoreAdapter.RecyclerViewHolder> {

    private List<CheckStore> datas;

    public RecyclerViewCheckStoreAdapter(List<CheckStore> datas) {
        this.datas = datas;

    }

    public List<CheckStore> getDatas() {
        return datas;
    }

    public void setDatas(List<CheckStore> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.item, viewGroup, false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder recyclerViewHolder, int i) {

        CheckStore tran = datas.get(i);
        recyclerViewHolder.txtId.setText("Id: " + tran.getId());
        recyclerViewHolder.txtTime.setText("Time: " + tran.getDateTime().toString());
        recyclerViewHolder.txtStaffName.setText("Staff: " + tran.getSimpleStaff().getDetailName());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void updateData(List<CheckStore> listTransactions) {
        this.datas = listTransactions;
        notifyDataSetChanged();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout;
        TextView txtTime, txtType, txtId, txtStaffName, txtStatus, txtExchangeStore;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.linearLayoutItem);
            txtTime = itemView.findViewById(R.id.txtTime);
            txtType = itemView.findViewById(R.id.txtType);
            txtId = itemView.findViewById(R.id.txtId);
            txtStaffName = itemView.findViewById(R.id.txtStaffName);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            txtExchangeStore = itemView.findViewById(R.id.txtExchangeStore);
        }

    }
}
