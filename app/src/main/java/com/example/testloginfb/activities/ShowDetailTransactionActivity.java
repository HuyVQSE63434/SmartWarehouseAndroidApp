package com.example.testloginfb.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testloginfb.R;
import com.example.testloginfb.adapters.ShowTransactionDataAdapter;
import com.example.testloginfb.models.Detail;
import com.example.testloginfb.models.Material;
import com.example.testloginfb.models.RequestAddTransaction;
import com.example.testloginfb.models.StoreMaterial;
import com.example.testloginfb.models.Transaction;
import com.example.testloginfb.models.TransactionMaterialAmount;
import com.example.testloginfb.presenters.AddNewTransactionPresenter;
import com.example.testloginfb.room.entities.StaffEntity;
import com.example.testloginfb.views.AddNewTransactionView;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ShowDetailTransactionActivity extends BaseActivity implements AddNewTransactionView {
    private Timestamp mTimestamp;
    private Toolbar mToolbar;
    private AddNewTransactionPresenter mPresenter;

    private Paint p = new Paint();
    private List<TransactionMaterialAmount> mDatasEc = new ArrayList<>();
    private Dialog dialog;
    private RequestAddTransaction mRequestAddTransaction;
    private ShowTransactionDataAdapter mDetailTransactionAdapter;
    private TextView mTxtDate;
    private Locale mLocale;
    private SimpleDateFormat mSimpleDateFormat;
    private StaffEntity mStaffEntity;
    private TextView mExchangeStore;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_transaction);


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Intent intent = getIntent();
        mRequestAddTransaction = (RequestAddTransaction) intent.getSerializableExtra("requestTransaction");
        mStaffEntity = (StaffEntity) intent.getSerializableExtra("staff");
        mDatasEc = (List<TransactionMaterialAmount>) intent.getSerializableExtra("dataEc");

        mPresenter = new AddNewTransactionPresenter(this, this, getApplication());
        initialView(mStaffEntity);
        initialData();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initialView(final StaffEntity mStaffEntity) {

        mExchangeStore = findViewById(R.id.txt_exchange_store);
        if (mRequestAddTransaction.getDetail() != null)
            mDetailTransactionAdapter = new ShowTransactionDataAdapter(mRequestAddTransaction.getDetail());
        else
            mDetailTransactionAdapter = new ShowTransactionDataAdapter(new ArrayList<TransactionMaterialAmount>());
        dialog = new Dialog(this);
        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle("Chi tiết đơn hàng");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*final Intent data = new Intent();
                data.putExtra("datas", (Serializable) mDetailTransactionAdapter.getData());
                setResult(Activity.RESULT_FIRST_USER, data);
                */
                finish();
            }
        });
        TextView txtHeader = findViewById(R.id.txt_header);
        txtHeader.setText(getNameType(mRequestAddTransaction.getTransactionTypeId()));
        TextView txtSendStore = findViewById(R.id.txt_store_send);
        txtSendStore.setText(mStaffEntity.getStore());
        mTxtDate = findViewById(R.id.txt_date);

        mLocale = new Locale("vi", "VN");
        mSimpleDateFormat = new SimpleDateFormat("h:mm a dd-MM-yyyy ", mLocale);
        mTimestamp = new Timestamp(System.currentTimeMillis() / 1000);
        mTxtDate.setText(mSimpleDateFormat.format(new Date(System.currentTimeMillis())));
        TextView txtStaff = findViewById(R.id.txt_staff);
        txtStaff.setText(mStaffEntity.getStaffName());
        RecyclerView recyclerView = findViewById(R.id.recyclerView_detail);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mDetailTransactionAdapter);
        Button btnCancel = findViewById(R.id.btn_cancel);
        Button btnSubmit = findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRequestAddTransaction.setDetail(mDetailTransactionAdapter.getData());
                if (mRequestAddTransaction.getDetail().size() < 1) {
                    Toast.makeText(getApplicationContext(), "Chưa có món hàng nào để tạo đơn", Toast.LENGTH_LONG).show();
                    return;
                }
                mTimestamp = new Timestamp(System.currentTimeMillis() / 1000);
                mRequestAddTransaction.setTime(String.valueOf(mTimestamp.getTime() + 25200));
                List<TransactionMaterialAmount> tempData = mDetailTransactionAdapter.getData();
                if (mDatasEc != null) {
                    for (TransactionMaterialAmount transactionMaterialAmount :
                            tempData) {
                        for (TransactionMaterialAmount amount :
                                mDatasEc) {
                            if (transactionMaterialAmount.getMaterial().getDetailId().equalsIgnoreCase(amount.getMaterial().getDetailId())) {
                                if (transactionMaterialAmount.getMaterialAmount() > amount.getMaterialAmount()) {
                                    transactionMaterialAmount.setMaterialAmount(amount.getMaterialAmount());
                                    mDetailTransactionAdapter.setData(tempData);
                                    dialog.setContentView(R.layout.yes_no_question_popup);
                                    TextView text = dialog.findViewById(R.id.txt_content);
                                    text.setText("Món hàng " + transactionMaterialAmount.getMaterial().getDetailName()+
                                            " có số lượng tối đa là "+ transactionMaterialAmount.getMaterialAmount()+", xin vui lòng kiểm tra lại");
                                    Button btnCancel = dialog.findViewById(R.id.btn_cancel);
                                    btnCancel.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog.dismiss();
                                        }
                                    });
                                    Button btnOk = dialog.findViewById(R.id.btn_ok);
                                    btnOk.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog.dismiss();
                                        }
                                    });
                                    dialog.show();
                                    return;
                                }
                            }
                        }
                    }
                }
                mRequestAddTransaction.setDetail(mDetailTransactionAdapter.getData());
                mPresenter.handleAddTrannsaction(mRequestAddTransaction, mStaffEntity.getAuthToken());
            }
        });
        mDetailTransactionAdapter.setmListener(new ShowTransactionDataAdapter.onItemClickListener() {

            @Override
            public void onChangeFocusAmount(int position) {
                List<TransactionMaterialAmount> amountList = mDetailTransactionAdapter.getData();
                TransactionMaterialAmount amount = amountList.get(position);
                for (TransactionMaterialAmount a :
                        mDatasEc) {
                    if (a.getMaterial().getDetailId().equalsIgnoreCase(amount.getMaterial().getDetailId())) {
                        if (a.getMaterialAmount() < amount.getMaterialAmount()) {
                            amount.setMaterialAmount(a.getMaterialAmount());
                            mDetailTransactionAdapter.setData(amountList);
                            Toast.makeText(getApplicationContext(), "Số lượng tối đa của " + a.getMaterial().getDetailName() + " là "
                                    + String.valueOf(a.getMaterialAmount()), Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }

            @Override
            public void onItemDelete(int position) {
                mDetailTransactionAdapter.deleteItem(position);
                Toast.makeText(getApplicationContext(), "Xóa thành công", Toast.LENGTH_LONG).show();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mRequestAddTransaction.getTransactionTypeId().equalsIgnoreCase("IM02")) {
                    Intent intent = new Intent(getApplicationContext(), AddImportFromSupplierActivity.class);
                    intent.putExtra("type", mRequestAddTransaction.getTransactionTypeId());
                    intent.putExtra("staff", mStaffEntity);
                    intent.putExtra("requestTransaction", mRequestAddTransaction);
                    startActivityForResult(intent, 111);
                } else {
                    Intent intent = new Intent(getApplicationContext(), AddTransactionActivity.class);
                    intent.putExtra("type", mRequestAddTransaction.getTransactionTypeId());
                    intent.putExtra("staff", mStaffEntity);
                    intent.putExtra("requestTransaction", mRequestAddTransaction);
                    intent.putExtra("mDatasEc", (Serializable) mDatasEc);
                    startActivityForResult(intent, 111);
                }
/*
                final Intent data = new Intent();
                data.putExtra("datas", (Serializable) mDetailTransactionAdapter.getData());
                setResult(Activity.RESULT_FIRST_USER, data);
                finish();*/
            }
        });
    }

    private void initialData() {
        if (mRequestAddTransaction.getTransactionTypeId().equalsIgnoreCase("IM01") && mDatasEc == null) {
            showProgressHUD();
            mPresenter.handleLoadTransactionEC(mStaffEntity.getStoreId(), mStaffEntity.getAuthToken());
        }
    }


    private String getNameType(String mType) {
        switch (mType) {
            case "IM01":
                return "Đơn nhập từ chuyển kho";
            case "IM02":
                return "Đơn nhập hàng";
            case "IMAF":
                return "Đơn nhập hàng sau bán";
            case "EX01":
                return "Đơn xuất hàng cho bán";
            case "EX02":
                return "Đơn xuất hàng hỏng mốc";
            case "EC":
                return "Đơn chuyển kho";
            case "checkStore":
                return "Đơn kiểm kho";
            default:
                return null;
        }
    }

    @Override
    public void updateScanMaterial(Material material) {

    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void doneAddTransaction() {

    }

    @Override
    public void done() {
        dismissProgressHUDNow();
    }

    @Override
    public void returnAddResult(boolean aBoolean) {
        if (aBoolean) {
            if (mRequestAddTransaction.getTransactionTypeId().equalsIgnoreCase("IM01")
                    || mRequestAddTransaction.getTransactionTypeId().equalsIgnoreCase("IMAF")) {
                Toast.makeText(getApplicationContext(), "Nhập hàng thành công", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(getApplicationContext(), "Tạo đơn thành công", Toast.LENGTH_LONG).show();
            mRequestAddTransaction.setDetail(new ArrayList<TransactionMaterialAmount>());
            mDetailTransactionAdapter.setData(mRequestAddTransaction.getDetail());
            mTxtDate.setText(mSimpleDateFormat.format(new Date(System.currentTimeMillis())));
            if (mRequestAddTransaction.getTransactionTypeId().equalsIgnoreCase("IM01") && mDatasEc == null) {
                showProgressHUD();
                mPresenter.handleLoadTransactionEC(mStaffEntity.getStoreId(), mStaffEntity.getAuthToken());
            }
        }
    }

    @Override
    public void loadMaterials(List<Material> materials) {

    }

    @Override
    public void returnTransactions(List<Transaction> transactions) {
        dismissProgressHUDNow();
        if (transactions.isEmpty()) {
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.yes_no_question_popup);
            TextView text = dialog.findViewById(R.id.txt_content);
            text.setText("Không có hóa đơn nhập hàng nào vào kho bạn");
            Button btnCancel = dialog.findViewById(R.id.btn_cancel);
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            Button btnOk = dialog.findViewById(R.id.btn_ok);
            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            dialog.show();
        } else {
            mDatasEc = new ArrayList<>();
            mRequestAddTransaction.setExchangeStoreId(transactions.get(0).getStore().getDetailId());
            for (Transaction tran :
                    transactions) {
                List<TransactionMaterialAmount> amounts = tran.getDetail();

                for (TransactionMaterialAmount amount :
                        amounts) {
                    boolean temp = false;
                    for (TransactionMaterialAmount amount1 :
                            mDatasEc) {
                        if (amount1.getMaterial().getDetailName().equalsIgnoreCase(amount.getMaterial().getDetailName())) {
                            amount1.setMaterialAmount(amount1.getMaterialAmount() + amount.getMaterialAmount());
                            temp = true;
                        }
                    }
                    if (!temp) {
                        mDatasEc.add(amount);
                    }
                }
            }
            mExchangeStore.setText("Từ kho: " + transactions.get(0).getStore().getDetailName());
        }
    }

    @Override
    public void returnStoreMaterial(List<StoreMaterial> storeMaterials) {

    }

    @Override
    public void addExchangeStore(List<Detail> details) {

    }

    @Override
    public void addSuppliers(List<Detail> details) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 111) {
            if (resultCode == Activity.RESULT_OK) {
                mRequestAddTransaction = (RequestAddTransaction) data.getSerializableExtra("requestTransaction");
                mDatasEc = (List<TransactionMaterialAmount>) data.getSerializableExtra("dataEc");
                mDetailTransactionAdapter.setData(mRequestAddTransaction.getDetail());
            }
        }
    }
}
