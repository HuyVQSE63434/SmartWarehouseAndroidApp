package com.example.testloginfb.adapters;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.testloginfb.R;
import com.example.testloginfb.models.TransactionMaterialAmount;

import java.util.ArrayList;
import java.util.List;

public class ShowNotificationDataAdapter extends RecyclerView.Adapter<ShowNotificationDataAdapter.MyViewHolder> {


    private List<TransactionMaterialAmount> data = new ArrayList<>();

    public ShowNotificationDataAdapter(List<TransactionMaterialAmount> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ShowNotificationDataAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.item_marterial_recycleview, viewGroup, false);
        view.setBackgroundColor(Color.alpha(R.color.light_yellow));
        MyViewHolder recyclerViewHolder = new MyViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShowNotificationDataAdapter.MyViewHolder myViewHolder, int i) {
        TransactionMaterialAmount amount = data.get(i);
        myViewHolder.mTxtIdInventory.setText(amount.getMaterial().getDetailId());
        myViewHolder.mTxtTextMaterial.setText(amount.getMaterial().getDetailName());
        myViewHolder.mTxtMaterialQuantity.setText(String.valueOf(amount.getMaterialAmount())+" "+amount.getUnit());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView mTxtIdInventory, mTxtTextMaterial;
        private TextView mTxtMaterialQuantity;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mTxtIdInventory = itemView.findViewById(R.id.txtIdIventory);
            mTxtTextMaterial = itemView.findViewById(R.id.textMaterial);
            mTxtMaterialQuantity = itemView.findViewById(R.id.material_quantity);
        }
    }
}
