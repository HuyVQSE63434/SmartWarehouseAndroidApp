package com.example.testloginfb.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;


import com.example.testloginfb.R;
import com.example.testloginfb.adapters.ImportHistoryAdapter;

public class ImportHistoryActivity extends  BaseActivity{
    private Toolbar mToolbar;
    private LinearLayout mLnlStarDate, mLnlEndDate;
    private RecyclerView mRcv;
    private ImportHistoryAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_history);
        initialView();
    }

    private void intinialData(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRcv.setLayoutManager(layoutManager);
        mRcv.setAdapter(mAdapter);

    }

    private void initialView() {
        mToolbar = findViewById(R.id.tb_import_history);
        mLnlStarDate = findViewById(R.id.lnl_import_history_start_date);
        mLnlEndDate = findViewById(R.id.lnl_import_history_end_date);
        mRcv = findViewById(R.id.rcv_import_history);
        setSupportActionBar(mToolbar);
        getActionBar().setElevation(0);
        getSupportActionBar().setTitle("LỊCH SỬ NHẬP HÀNG");
        mToolbar.setNavigationIcon(R.mipmap.ic_add);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
