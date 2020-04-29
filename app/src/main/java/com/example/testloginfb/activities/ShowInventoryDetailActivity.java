package com.example.testloginfb.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.testloginfb.R;
import com.example.testloginfb.adapters.RecycleViewInventoryAdapter;
import com.example.testloginfb.adapters.RecycleViewInventoryDetailAdapter;
import com.example.testloginfb.models.Material;
import com.example.testloginfb.models.MaterialTransaction;
import com.example.testloginfb.models.StoreMaterial;
import com.example.testloginfb.presenters.InventoryDetailPresenter;
import com.example.testloginfb.room.entities.StaffEntity;
import com.example.testloginfb.views.InventoryDetailView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShowInventoryDetailActivity extends BaseActivity implements RecycleViewInventoryDetailAdapter.OnDetailListener, InventoryDetailView {
    private static final String TAG = "showDetailActivity";
    private RecyclerView recyclerView;

    private RecyclerView.LayoutManager layoutManager;
    private List<MaterialTransaction> listStoreMaterials = new ArrayList<>();
    private RecycleViewInventoryDetailAdapter recycleAdapter;
    private StaffEntity msStaffEntity;
    private InventoryDetailPresenter mInventoryPresenter;



    @Override
    protected void onResume() {
        super.onResume();

        initialData();
    }
    private void initialData() {
        listStoreMaterials = new ArrayList<>();

        mInventoryPresenter = new InventoryDetailPresenter(this, this, this.getApplication());
        mInventoryPresenter.HandleLoadLocalStaff();
        showProgressHUD();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail2);
//        getSupportActionBar().hide();
        //get intent
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String mTypeId =bundle.getString("id_material");
        String mTypeName =bundle.getString("detail_name");
        Long mTypeAmount =bundle.getLong("detail_amount");
        String mUnit = bundle.getString("unit");
        TextView textViewID = findViewById(R.id.txtIdDetail);
        TextView textViewName = findViewById(R.id.txtInventoryDetail);
        TextView textViewAmount = findViewById(R.id.txtDetailAmount);
        textViewID.setText(mTypeId);
        textViewName.setText(mTypeName);
        textViewAmount.setText(mTypeAmount.toString()+ " "+ mUnit);
        recyclerView = findViewById(R.id.recycleViewDetail);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        ImageView imageView = findViewById(R.id.img_back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recycleAdapter = new RecycleViewInventoryDetailAdapter(listStoreMaterials, this);
        recyclerView.setAdapter(recycleAdapter);

    }


    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void loadDetailInventory(List<MaterialTransaction> MaterialTransaction) {
        this.listStoreMaterials.addAll(MaterialTransaction);
        recycleAdapter.updateList(listStoreMaterials);
    }

    @Override
    public void done() {
        dismissProgressHUDNow();
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void loadLocalStaff(StaffEntity staffEntity) {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String mTypeId =bundle.getString("id_material");
        this.msStaffEntity = staffEntity;
        mInventoryPresenter.handleLoadInventoryDetail(msStaffEntity.getStoreId(),mTypeId,msStaffEntity.getAuthToken());

    }




}
