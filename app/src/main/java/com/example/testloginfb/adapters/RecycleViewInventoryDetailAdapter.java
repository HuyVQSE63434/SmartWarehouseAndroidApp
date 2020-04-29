package com.example.testloginfb.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.CardView;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.testloginfb.R;
import com.example.testloginfb.models.MaterialTransaction;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RecycleViewInventoryDetailAdapter extends RecyclerView.Adapter<RecycleViewInventoryDetailAdapter.MyViewHolder> {
    private List<MaterialTransaction> listMaterial = new ArrayList<>();
    private Context mContext;
    private OnDetailListener mOnDetailListener;
    private RecycleViewInventoryDetailAdapter adapter;
    private final static int FADE_DURATION = 1000;
    private int previousExpandedPosition = -1;
    private int mExpandedPosition = -1;
    private RecyclerView r1;
    ViewGroup viewGroup;
  /*  public RecycleAdapter(List<String> listMaterial,Context mContext){
        this.listMaterial = listMaterial;
        this.mContext=mContext;



    }
    */

    public RecycleViewInventoryDetailAdapter(List<MaterialTransaction> listMaterial, OnDetailListener onDetailListener) {
        this.listMaterial = listMaterial;
        this.mOnDetailListener = onDetailListener;


    }


    @Override

    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_detail_inventory, viewGroup, false);
        MyViewHolder myviewholder = new MyViewHolder(view, mOnDetailListener);

        return myviewholder;
    }


    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder viewHolder, final int i) {
        viewHolder.textTransaction.setText(listMaterial.get(i).getTransactionType().getDetailName());
        String date = DateFormat.getDateInstance(DateFormat.FULL).format(listMaterial.get(i).getDateTime());
        String time = DateFormat.getTimeInstance().format(listMaterial.get(i).getDateTime());
        viewHolder.textDateTime.setText("Ngày: "+date+"\nGiờ:"+time);
        viewHolder.staff_transaction.setText(listMaterial.get(i).getStaff().getDetailName());
        if(listMaterial.get(i).getExchangeStore() !=null){
            viewHolder.store_name.setText(listMaterial.get(i).getExchangeStore().getDetailName());
        }
        else{
            viewHolder.store_name.setText("");
        }

        viewHolder.store_material_amount.setText(String.valueOf(listMaterial.get(i).getInventoryAmmount())+ " "+ listMaterial.get(i).getUnit());
        viewHolder.store_material_status.setText(listMaterial.get(i).getStatus().getDetailName());
        setFadeAnimation(viewHolder.itemView);


        final boolean isExpanded = i == mExpandedPosition;
        viewHolder.txtViewExpanse.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        viewHolder.itemView.setActivated(isExpanded);
        if (isExpanded)
            previousExpandedPosition = i;
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExpandedPosition = isExpanded ? -1 : i;
                notifyItemChanged(previousExpandedPosition);
                notifyItemChanged(i);
            }
        });
        if(i%1 == 0){
            viewHolder.parentLayout.setBackgroundResource(R.drawable.background_list_item);

        }


    }

    @Override
    public int getItemCount() {
        return listMaterial.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textTransaction;
        CardView parentLayout;
        OnDetailListener onDetailListener;
        TextView textDateTime;
        LinearLayout txtViewExpanse;
        TextView staff_transaction;
        TextView store_name;
        TextView store_material_amount;
        TextView store_material_status;


        public MyViewHolder(View itemView, OnDetailListener onDetailListener) {

            super(itemView);
            textTransaction = itemView.findViewById(R.id.transactionType);
            textDateTime = itemView.findViewById(R.id.DateTimeDetail);
            parentLayout = itemView.findViewById(R.id.parent_layout_detail);
            txtViewExpanse = itemView.findViewById(R.id.txtDetailexpand);
            staff_transaction = itemView.findViewById(R.id.staff_transaction);
            store_name = itemView.findViewById(R.id.store_name);
            store_material_amount = itemView.findViewById(R.id.store_material_amount);
            store_material_status = itemView.findViewById(R.id.store_material_status);


            this.onDetailListener = onDetailListener;
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            onDetailListener.onItemClick(getAdapterPosition());

        }
    }

    public void updateList(List<MaterialTransaction> newList) {
        listMaterial = new ArrayList<>();
        listMaterial.addAll(newList);
        notifyDataSetChanged();
    }


    public interface OnDetailListener {
        void onItemClick(int position);
    }

    private void setScaleAnimation(View view) {
        ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }

    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }

    private int lastPosition = -1;

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            anim.setDuration(new Random().nextInt(501));//to make duration random number between [0,501)
            viewToAnimate.startAnimation(anim);
            lastPosition = position;
        }
    }
}
