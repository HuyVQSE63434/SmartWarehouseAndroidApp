package com.example.testloginfb.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
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

public class ShowTransactionDataAdapter extends RecyclerView.Adapter<ShowTransactionDataAdapter.MyViewHolder> {


    public ShowTransactionDataAdapter() {
    }

    public void deleteItem(int position) {
        if(data.size() >0){
            data.remove(position);
            notifyDataSetChanged();
        }
    }

    public interface onItemClickListener{
        void onChangeFocusAmount(int position);
        void onItemDelete(int position);
    }
    onItemClickListener mListener;
    static List<TransactionMaterialAmount> data = new ArrayList<>();

    public onItemClickListener getmListener() {
        return mListener;
    }

    public void setmListener(onItemClickListener mListener) {
        this.mListener = mListener;
    }

    public ShowTransactionDataAdapter(List<TransactionMaterialAmount> data) {
        this.data = data;
    }

    public List<TransactionMaterialAmount> deleteTransactionAmount(int position) {
        data.remove(position);
        notifyDataSetChanged();
        return data;
    }

    public List<TransactionMaterialAmount> getData() {
        return data;
    }

    @NonNull
    @Override
    public ShowTransactionDataAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.item_detail_material, viewGroup, false);
        MyViewHolder recyclerViewHolder = new MyViewHolder(view,mListener);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShowTransactionDataAdapter.MyViewHolder myViewHolder, int i) {

        TransactionMaterialAmount amount = data.get(i);
        myViewHolder.mTxtIdInventory.setText(amount.getMaterial().getDetailId());
        myViewHolder.mTxtTextMaterial.setText(amount.getMaterial().getDetailName());
        myViewHolder.mTxtMaterialQuantity.setText(String.valueOf(amount.getMaterialAmount()));
    }

    public void setData(List<TransactionMaterialAmount> data) {
        ShowTransactionDataAdapter.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView mTxtIdInventory, mTxtTextMaterial;
        private EditText mTxtMaterialQuantity;
        private ImageView mImageView;

        public MyViewHolder(@NonNull View itemView, final onItemClickListener listener) {
            super(itemView);
            mTxtIdInventory = itemView.findViewById(R.id.txtIdIventory);
            mTxtTextMaterial = itemView.findViewById(R.id.textMaterial);
            mTxtMaterialQuantity = itemView.findViewById(R.id.material_quantity);
            mTxtMaterialQuantity.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }
                @Override
                public void afterTextChanged(Editable s) {
                    if(listener!=null && !s.toString().isEmpty()){
                        data.get(getAdapterPosition()).setMaterialAmount(Long.parseLong(s.toString()));
                    }
                }
            });

            mTxtMaterialQuantity.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(!hasFocus){
                        if(listener !=null){
                            try{
                                if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                                    data.get(getAdapterPosition()).setMaterialAmount(Long.parseLong(mTxtMaterialQuantity.getText().toString()));
                                    listener.onChangeFocusAmount(getAdapterPosition());
                                }
                            }catch (Exception e){
                                System.out.println(e.getMessage());
                            }
                        }
                    }
                }
            });

            mImageView =(ImageView) itemView.findViewById(R.id.img_delete_transaction);
            mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemDelete(position);
                        }
                    }
                }
            });
        }
    }
}
