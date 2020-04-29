package com.example.testloginfb.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.testloginfb.R;
import com.example.testloginfb.models.StoreMaterial;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RecycleViewInventoryAdapter extends RecyclerView.Adapter<RecycleViewInventoryAdapter.MyViewHolder> {
    private List<StoreMaterial> listMaterial=new ArrayList<>();
    private Context mContext;
    private OnDetailListener mOnDetailListener;
    private RecycleViewInventoryAdapter adapter;
    private final static int FADE_DURATION = 1000;

  /*  public RecycleAdapter(List<String> listMaterial,Context mContext){
        this.listMaterial = listMaterial;
        this.mContext=mContext;



    }
    */

    public RecycleViewInventoryAdapter(List<StoreMaterial> listMaterial, OnDetailListener onDetailListener) {
        this.listMaterial = listMaterial;
        this.mOnDetailListener = onDetailListener;

    }


    @Override

    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view =  LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_marterial_recycleview, viewGroup, false);
        MyViewHolder myviewholder = new MyViewHolder(view, mOnDetailListener);
        return myviewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, final int i) {
        viewHolder.textMaterial.setText(listMaterial.get(i).getMaterialName().getDetailName());
        viewHolder.txtAmount.setText(String.valueOf(listMaterial.get(i).getInventoryAmmount()) + " "+ listMaterial.get(i).getUnit());
        viewHolder.txtIdMaterial.setText(listMaterial.get(i).getMaterialName().getDetailId());

       setFadeAnimation(viewHolder.itemView);

    }

    @Override
    public int getItemCount() {
        return listMaterial.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textMaterial;
        LinearLayout parentLayout;
        OnDetailListener onDetailListener;
        TextView txtAmount;
        TextView txtIdMaterial;


        public MyViewHolder(View itemView, OnDetailListener onDetailListener) {

            super(itemView);
            textMaterial = itemView.findViewById(R.id.textMaterial);
            txtAmount = itemView.findViewById(R.id.material_quantity);
            txtIdMaterial = itemView.findViewById(R.id.txtIdIventory);
            parentLayout = itemView.findViewById(R.id.parent_layout);
            this.onDetailListener = onDetailListener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onDetailListener.onItemClick(getAdapterPosition());


        }
    }
    public void updateList(List<StoreMaterial> newList){
        listMaterial = new ArrayList<>();
        listMaterial.addAll(newList);
        notifyDataSetChanged();
    }


    public interface OnDetailListener{
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
