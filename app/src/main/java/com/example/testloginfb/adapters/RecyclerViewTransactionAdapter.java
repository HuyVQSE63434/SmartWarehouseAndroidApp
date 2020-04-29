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
import com.example.testloginfb.models.Transaction;

import java.text.DateFormat;
import java.util.List;

public class RecyclerViewTransactionAdapter extends RecyclerView.Adapter<RecyclerViewTransactionAdapter.RecyclerViewHolder> {

    private List<Transaction> datas;

    onItemClickListener mListener;

    public RecyclerViewTransactionAdapter(List<Transaction> datas){
        this.datas = datas;
    }

    public interface onItemClickListener{
        void onItemClick(int position);
    }

    public onItemClickListener getmListener() {
        return mListener;
    }

    public void setmListener(onItemClickListener mListener) {
        this.mListener = mListener;
    }

    public List<Transaction> getDatas() {
        return datas;
    }

    public void setDatas(List<Transaction> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.item,viewGroup,false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view, mListener);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder recyclerViewHolder, int i) {

        Transaction tran = datas.get(i);
        recyclerViewHolder.txtId.setText("Id: " + tran.getId());
        recyclerViewHolder.txtType.setText("Loại: "+ tran.getTransactionType().getDetailName());
        String date = DateFormat.getDateInstance(DateFormat.FULL).format(tran.getDateTime());
        recyclerViewHolder.txtDate.setText("Ngày: "+ date);
        String time = DateFormat.getTimeInstance().format(tran.getDateTime());
        recyclerViewHolder.txtTime.setText("Giờ: "+ time);
        if(tran.getExchangeStore() != null){
            recyclerViewHolder.txtExchangeStore.setText("Chuyển từ kho: " + tran.getExchangeStore().getDetailName());
        }
        recyclerViewHolder.txtStaffName.setText("Nhân viên: "+tran.getStaff().getDetailName());
        recyclerViewHolder.txtStatus.setText("Tình trạng: "+tran.getStatus().getDetailName());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void updateData(List<Transaction> listTransactions) {
        this.datas = listTransactions;
        notifyDataSetChanged();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout;
        TextView txtDate,txtTime,txtType,txtId,txtStaffName,txtStatus,txtExchangeStore;
        public RecyclerViewHolder(@NonNull View itemView,final onItemClickListener listener) {
            super(itemView);
            layout = itemView.findViewById(R.id.linearLayoutItem);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtTime = itemView.findViewById(R.id.txtTime);
            txtType = itemView.findViewById(R.id.txtType);
            txtId = itemView.findViewById(R.id.txtId);
            txtStaffName = itemView.findViewById(R.id.txtStaffName);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            txtExchangeStore = itemView.findViewById(R.id.txtExchangeStore);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        listener.onItemClick(getAdapterPosition());
                    }
                }
            });
        }

    }
}
