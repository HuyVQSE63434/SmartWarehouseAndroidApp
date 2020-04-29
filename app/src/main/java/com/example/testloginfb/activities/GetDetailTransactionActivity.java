package com.example.testloginfb.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.testloginfb.R;
import com.example.testloginfb.adapters.RecyclerViewCheckStoreAdapter;
import com.example.testloginfb.adapters.RecyclerViewTransactionAdapter;
import com.example.testloginfb.configs.ConfigTransaction;
import com.example.testloginfb.models.CheckStore;
import com.example.testloginfb.models.RequestAddTransaction;
import com.example.testloginfb.models.Transaction;
import com.example.testloginfb.presenters.TransactionPresenter;
import com.example.testloginfb.room.entities.StaffEntity;
import com.example.testloginfb.views.TransactionView;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetDetailTransactionActivity extends BaseActivity implements TransactionView, View.OnClickListener {

    private RecyclerView mRcvImportDetail;
    private List<Transaction> mListTransactions = new ArrayList<>();

    private static List<CheckStore> checkStores = new ArrayList<>();
    private Toolbar mToolbar;
    private List<String> types = new ArrayList<>();
    private RecyclerViewTransactionAdapter mRecyclerViewTransactionAdapter;
    private RecyclerViewCheckStoreAdapter mRecyclerViewCheckStoreAdapter;
    private Button mBtnAdd;
    private TransactionPresenter mPresenter;
    private String mType;
    private static StaffEntity mStaffEntity;
    private boolean checkStore = false;

    private static Dialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_detail);
        initialView();
        initialData();
        Intent intent = getIntent();
        mType = intent.getStringExtra("type");
        mListTransactions = new ArrayList<>();

        if (mType != null) {
            switch (mType) {
                case "import":
                    types.addAll(Arrays.asList(ConfigTransaction.imports));
                    mToolbar.setTitle("Nhập hàng");
                    break;
                case "export":
                    types.addAll(Arrays.asList(ConfigTransaction.exports));
                    mToolbar.setTitle("Xuất hàng");
                    break;
                case "exchange":
                    types.addAll(Arrays.asList(ConfigTransaction.exchange));
                    mToolbar.setTitle("Chuyển kho");
                    break;
                case "checkStore":
                    checkStore = true;
                    mToolbar.setTitle("Kiểm kho");
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initialData();
    }

    private void initialData() {
        showProgressHUD();
        mPresenter = new TransactionPresenter(this, this, this.getApplication());
        if (checkStore == true) {
            checkStores = new ArrayList<>();
            mRecyclerViewCheckStoreAdapter = new RecyclerViewCheckStoreAdapter(checkStores);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
            mRcvImportDetail.setLayoutManager(linearLayoutManager);
            mRcvImportDetail.setAdapter(mRecyclerViewCheckStoreAdapter);
            showProgressHUD();
        } else {
            mRecyclerViewTransactionAdapter = new RecyclerViewTransactionAdapter(mListTransactions);
            mRecyclerViewTransactionAdapter.setmListener(new RecyclerViewTransactionAdapter.onItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Intent intent = new Intent(getApplicationContext(), ShowNotificationDataActivity.class);
                    intent.putExtra("data", (Serializable) mRecyclerViewTransactionAdapter.getDatas().get(position));
                    startActivity(intent);
                }
            });
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
            mRcvImportDetail.setLayoutManager(linearLayoutManager);
            mRcvImportDetail.setAdapter(mRecyclerViewTransactionAdapter);
        }
        if (mStaffEntity == null) {
            mPresenter.handleLoadLocalStaff();
        } else {
            if (mListTransactions.isEmpty())
                for (String t :
                        types) {
                    Map<String, String> params = new HashMap<>();
                    params.put("typeId", t);
                    mPresenter.handleLoadTransactionList(mStaffEntity.getStoreId(), params, mStaffEntity.getAuthToken());
                }
            else {
                this.dismissProgressHUDNow();
            }
        }

    }

    private void initialView() {
        mBtnAdd = findViewById(R.id.btnAdd);
        mBtnAdd.setOnClickListener(this);
        mRcvImportDetail = findViewById(R.id.recyclerView);
        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle("Import detail");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mDialog = new Dialog(GetDetailTransactionActivity.this);

    }

    @Override
    public void loadImportDetail(List<Transaction> transactions) {
        this.mListTransactions.addAll(transactions);
        mRecyclerViewTransactionAdapter.updateData(mListTransactions);
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void done() {
        dismissProgressHUDNow();
    }

    @Override
    public void loadLocalStaff(StaffEntity staffEntity) {
        this.mStaffEntity = staffEntity;
        if (checkStore) {
            Map<String, String> params = new HashMap<>();
            mPresenter.handleLoadCheckStore(mStaffEntity.getStoreId(), params, mStaffEntity.getAuthToken());
        } else {
            for (String t :
                    types) {
                Map<String, String> params = new HashMap<>();
                params.put("typeId", t);
                mPresenter.handleLoadTransactionList(mStaffEntity.getStoreId(), params, mStaffEntity.getAuthToken());
            }
        }
    }

    @Override
    public void loadCheckStore(List<CheckStore> checkStores) {
        this.checkStores.addAll(checkStores);
        mRecyclerViewCheckStoreAdapter.updateData(checkStores);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAdd:
                if (checkStore) {
                    goToDetailTransaction("checkStore");
                } else if (mType.equalsIgnoreCase("import")) {
                    mDialog.setContentView(R.layout.transaction_import_type_popup);
                    Button btnImportFromStore = mDialog.findViewById(R.id.btn_import_from_store);
                    Button btnImportFromOtherSupplier = mDialog.findViewById(R.id.btn_import_from_other_supplier);
                    Button btnImportAfterSale = mDialog.findViewById(R.id.btn_import_after_sale);
                    btnImportFromStore.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            goToAddTransaction("IM01");
                        }
                    });
                    btnImportFromOtherSupplier.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            goToAddImpportEx("IM02");
                        }
                    });
                    btnImportAfterSale.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            goToAddTransaction("IMAF");
                        }
                    });
                    mDialog.show();
                } else if (mType.equalsIgnoreCase("export")) {
                    Dialog dialog = new Dialog(GetDetailTransactionActivity.this);
                    dialog.setContentView(R.layout.transaction_export_type_popup);
                    Button btnExportForSale = dialog.findViewById(R.id.btn_export_for_sale);
                    Button btnExportforClear = dialog.findViewById(R.id.btn_export_for_clear);
                    btnExportForSale.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            goToAddTransaction("EX01");
                        }
                    });
                    btnExportforClear.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            goToAddTransaction("EX02");
                        }
                    });
                    dialog.show();
                } else {
                    goToAddExchange("EC");
                }
                break;
        }
    }

    private void goToDetailTransaction(String type) {
        Intent intent = new Intent(getApplicationContext(), AddTransactionActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("staff", mStaffEntity);
        startActivity(intent);
    }

    private void goToAddTransaction(String type) {

        RequestAddTransaction requestAddTransaction = new RequestAddTransaction();
        requestAddTransaction.setDetail(null);
        requestAddTransaction.setStatusId("HT");
        requestAddTransaction.setTransactionTypeId(type);
        requestAddTransaction.setStaffId(mStaffEntity.getStaffId().toString());

        Timestamp timestamp = new Timestamp(System.currentTimeMillis() / 1000);
        requestAddTransaction.setTime(timestamp.toString());
        requestAddTransaction.setStoreId(mStaffEntity.getStoreId());
        Intent intent = new Intent(this, ShowDetailTransactionActivity.class);
        intent.putExtra("requestTransaction", (Serializable) requestAddTransaction);
        intent.putExtra("staff", mStaffEntity);
        //intent.putExtra("exchangeSimpleStore", (Serializable) mExchangeDetails);
        //intent.putExtra("dataEc", (Serializable) mDatasEc);
        startActivity(intent);
    }

    private void goToAddExchange(String type) {
        /*Intent intent = new Intent(getApplicationContext(), AddExchangeActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("staff", mStaffEntity);
        startActivity(intent);*/

        RequestAddTransaction requestAddTransaction = new RequestAddTransaction();
        requestAddTransaction.setDetail(null);
        requestAddTransaction.setStatusId("ĐT");
        requestAddTransaction.setTransactionTypeId(type);
        requestAddTransaction.setStaffId(mStaffEntity.getStaffId().toString());

        Timestamp timestamp = new Timestamp(System.currentTimeMillis() / 1000);
        requestAddTransaction.setTime(timestamp.toString());
        requestAddTransaction.setStoreId(mStaffEntity.getStoreId());
        Intent intent = new Intent(this, ShowDetailExchangeActivity.class);
        intent.putExtra("requestTransaction", (Serializable) requestAddTransaction);
        intent.putExtra("staff", mStaffEntity);
        //intent.putExtra("exchangeSimpleStore", (Serializable) mExchangeDetails);
        //intent.putExtra("dataEc", (Serializable) mDatasEc);
        startActivity(intent);
    }

    private void goToAddImpportEx(String type) {
        /*Intent intent = new Intent(getApplicationContext(), AddImportFromSupplierActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("staff", mStaffEntity);
        startActivity(intent);*/

        RequestAddTransaction requestAddTransaction = new RequestAddTransaction();
        requestAddTransaction.setDetail(null);
        requestAddTransaction.setStatusId("HT");
        requestAddTransaction.setTransactionTypeId(type);
        requestAddTransaction.setStaffId(mStaffEntity.getStaffId().toString());

        Timestamp timestamp = new Timestamp(System.currentTimeMillis() / 1000);
        requestAddTransaction.setTime(timestamp.toString());
        requestAddTransaction.setStoreId(mStaffEntity.getStoreId());
        Intent intent = new Intent(this, ShowDetailTransactionActivity.class);
        intent.putExtra("requestTransaction", (Serializable) requestAddTransaction);
        intent.putExtra("staff", mStaffEntity);
        //intent.putExtra("exchangeSimpleStore", (Serializable) mExchangeDetails);
        //intent.putExtra("dataEc", (Serializable) mDatasEc);
        startActivity(intent);
    }
}
