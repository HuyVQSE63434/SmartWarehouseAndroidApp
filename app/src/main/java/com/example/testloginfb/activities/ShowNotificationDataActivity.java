package com.example.testloginfb.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.testloginfb.R;
import com.example.testloginfb.adapters.ShowNotificationDataAdapter;
import com.example.testloginfb.adapters.ShowTransactionDataAdapter;
import com.example.testloginfb.models.Transaction;
import com.example.testloginfb.models.TransactionMaterialAmount;

import java.text.DateFormat;
import java.util.List;

public class ShowNotificationDataActivity extends BaseActivity {

    private TextView mSalesHeaderTextView,mTxtInventoryDetail,mTxtIdDetail,mTxtDetailAmount;
    private RecyclerView mRecycleViewDetail;
    private ShowNotificationDataAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail2);

        Intent intent = getIntent();
        Transaction notiTransaction = (Transaction) intent.getSerializableExtra("data");
        initialView(notiTransaction);
        initialData(notiTransaction.getDetail());
    }

    private void initialData(List<TransactionMaterialAmount> data) {
        mAdapter = new ShowNotificationDataAdapter(data);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecycleViewDetail.setLayoutManager(linearLayoutManager);
        mRecycleViewDetail.setAdapter(mAdapter);
    }

    private void initialView(Transaction tran) {

        mSalesHeaderTextView = findViewById(R.id.salesHeaderTextView);
        String date = DateFormat.getDateInstance(DateFormat.FULL).format(tran.getDateTime());
        String time = DateFormat.getTimeInstance().format(tran.getDateTime());
        mSalesHeaderTextView.setText(date+"\nGiờ: "+ time);
        mTxtInventoryDetail = findViewById(R.id.txtInventoryDetail);
        if(tran.getExchangeStore() != null){
            mSalesHeaderTextView.setText(date+"\nGiờ: "+ time+"\nTừ: "+ tran.getExchangeStore().getDetailName());
        }
        mTxtInventoryDetail.setText(tran.getTransactionType().getDetailName());
        mTxtIdDetail = findViewById(R.id.txtIdDetail);
        mTxtIdDetail.setText(String.valueOf(tran.getId()));
        mTxtDetailAmount = findViewById(R.id.txtDetailAmount);
        mTxtDetailAmount.setText(tran.getDetail().size() + "\nsản phẩm");
        mRecycleViewDetail = findViewById(R.id.recycleViewDetail);
        ImageView imageView = findViewById(R.id.img_back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
