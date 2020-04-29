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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
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

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ShowDetailExchangeActivity extends BaseActivity implements AddNewTransactionView {

    private List<Detail> mExchangeDetails = new ArrayList<>();
    private static List<TransactionMaterialAmount> mDatasEc = new ArrayList<>();
    private Timestamp timestamp;
    private Toolbar mToolbar;
    private AddNewTransactionPresenter mPresenter;

    private Paint p = new Paint();
    private boolean bienphu = false;

    private static Dialog dialog;

    private AutoCompleteTextView mAcReceiveStore;
    private RequestAddTransaction mRequestAddTransaction;
    private ShowTransactionDataAdapter mDetailTransactionAdapter;
    private TextView txtDate;
    private Locale locale;
    private SimpleDateFormat simpleDateFormat;
    private StaffEntity mStaffEntity;
    private ImageView mImgReceiveStoreDropDown;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_exchange);


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        mPresenter = new AddNewTransactionPresenter(this, this, getApplication());
        Intent intent = getIntent();
        mRequestAddTransaction = (RequestAddTransaction) intent.getSerializableExtra("requestTransaction");
        mStaffEntity = (StaffEntity) intent.getSerializableExtra("staff");
        mExchangeDetails = (List<Detail>) intent.getSerializableExtra("exchangeSimpleStore");
        mDatasEc = (List<TransactionMaterialAmount>) intent.getSerializableExtra("dataEc");

        initialView();
        bienphu = true;

        dialog = new Dialog(this);
        mPresenter.hanleGetSimpleStore(mStaffEntity.getAuthToken());
        showProgressHUD();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initialView() {

        if (mRequestAddTransaction.getDetail() != null)
            mDetailTransactionAdapter = new ShowTransactionDataAdapter(mRequestAddTransaction.getDetail());
        else
            mDetailTransactionAdapter = new ShowTransactionDataAdapter(new ArrayList<TransactionMaterialAmount>());

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
                setResult(Activity.RESULT_FIRST_USER, data);*/
                finish();
            }
        });
        TextView txtHeader = findViewById(R.id.txt_header);
        txtHeader.setText(getNameType(mRequestAddTransaction.getTransactionTypeId()));
        mAcReceiveStore = findViewById(R.id.txt_store_receive);
        mAcReceiveStore.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(mExchangeDetails != null){
                    boolean change = false;
                    for (Detail detail:
                            mExchangeDetails) {
                        if(detail.getDetailName().equalsIgnoreCase(mAcReceiveStore.getText().toString())){
                            change = true;
                        }
                    }
                    if(!change){
                        mAcReceiveStore.setText("");
                    }
                }
            }
        });
        mImgReceiveStoreDropDown = findViewById(R.id.img_exchange_store_drop_down);
        mImgReceiveStoreDropDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAcReceiveStore.getAdapter() != null){
                    mAcReceiveStore.showDropDown();
                }
            }
        });

        //mAcReceiveStore.setText(getReceiveStore(mRequestAddTransaction.getExchangeStoreId()));
        TextView txtSendStore = findViewById(R.id.txt_store_send);
        txtSendStore.setText(mStaffEntity.getStore());
        txtDate = findViewById(R.id.txt_date);

        locale = new Locale("vi", "VN");
        simpleDateFormat = new SimpleDateFormat("h:mm a dd-MM-yyyy ", locale);
        timestamp = new Timestamp(System.currentTimeMillis() / 1000);
        txtDate.setText(simpleDateFormat.format(new Date(System.currentTimeMillis())));
        TextView txtStaff = findViewById(R.id.txt_staff);
        txtStaff.setText(mStaffEntity.getStaffName());

        RecyclerView recyclerView = findViewById(R.id.recyclerView_detail);
        mDetailTransactionAdapter.setmListener(new ShowTransactionDataAdapter.onItemClickListener() {

            @Override
            public void onChangeFocusAmount(int position) {
                List<TransactionMaterialAmount> amountList = mDetailTransactionAdapter.getData();
                TransactionMaterialAmount amount = amountList.get(position);
                for (TransactionMaterialAmount a:
                     mDatasEc) {
                    if(a.getMaterial().getDetailId().equalsIgnoreCase(amount.getMaterial().getDetailId())){
                        if(a.getMaterialAmount() < amount.getMaterialAmount()){
                            amount.setMaterialAmount(a.getMaterialAmount());
                            mDetailTransactionAdapter.setData(amountList);
                            Toast.makeText(getApplicationContext(),"Số lượng tối đa của "+a.getMaterial().getDetailName()+" là "
                                    + String.valueOf(a.getMaterialAmount()),Toast.LENGTH_LONG).show();
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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mDetailTransactionAdapter);
        Button btnAddItem = findViewById(R.id.btn_cancel);
        Button btnSubmit = findViewById(R.id.btn_submit_ex);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Detail detail :
                        mExchangeDetails) {
                    if (mAcReceiveStore.getText().toString().equalsIgnoreCase(detail.getDetailName())) {
                        mRequestAddTransaction.setExchangeStoreId(detail.getDetailId());
                    }
                }
                mRequestAddTransaction.setDetail(mDetailTransactionAdapter.getData());
                if(mExchangeDetails!=null){
                    boolean change = false;
                    for (Detail detail:
                            mExchangeDetails) {
                        if(detail.getDetailName().equalsIgnoreCase(mAcReceiveStore.getText().toString())){
                            change = true;
                            mRequestAddTransaction.setExchangeStoreId(detail.getDetailId());
                        }
                    }
                    if(!change){
                        mAcReceiveStore.setText("");
                        Toast.makeText(getApplicationContext(),"Kho không hợp lệ",Toast.LENGTH_SHORT).show();
                    }
                } else
                if (mRequestAddTransaction.getExchangeStoreId() == null) {
                    Toast.makeText(getApplicationContext(), "Chưa có kho cần chuyển đi", Toast.LENGTH_LONG).show();
                    return;
                }
                if (mRequestAddTransaction.getDetail().size() < 1) {
                    Toast.makeText(getApplicationContext(), "Chưa có món hàng nào để tạo đơn", Toast.LENGTH_LONG).show();
                    return;
                }
                timestamp = new Timestamp(System.currentTimeMillis() / 1000);
                mRequestAddTransaction.setTime(String.valueOf(timestamp.getTime() + 25200));
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
        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddExchangeActivity.class);
                intent.putExtra("type", "EC");
                intent.putExtra("staff", mStaffEntity);
                intent.putExtra("requestTransaction", mRequestAddTransaction);
                startActivityForResult(intent, 111);
                /*final Intent data = new Intent();
                data.putExtra("datas", (Serializable) mDetailTransactionAdapter.getData());
                setResult(Activity.RESULT_FIRST_USER, data);
                finish();*/
            }
        });
    }

    private String getReceiveStore(String exchangeStoreId) {
        for (Detail detail :
                mExchangeDetails) {
            if (detail.getDetailId().equalsIgnoreCase(exchangeStoreId)) {
                return detail.getDetailName();
            }
        }
        return null;
    }

    private String getNameType(String mType) {
        switch (mType) {
            case "EC":
                return "Đơn chuyển kho";
            default:
                return null;
        }
    }

    @Override
    public void updateScanMaterial(Material material) {

    }

    @Override
    public void showMessage(String message) {
        this.dismissProgressHUDNow();
        Toast.makeText(this, "Tạo đơn không thành công", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void doneAddTransaction() {

    }

    @Override
    public void done() {
        this.dismissProgressHUDNow();
    }

    @Override
    public void returnAddResult(boolean aBoolean) {
        if (aBoolean) {
            if(mRequestAddTransaction.getTransactionTypeId().equalsIgnoreCase("IM01")
                    || mRequestAddTransaction.getTransactionTypeId().equalsIgnoreCase("IMAF")){
                Toast.makeText(getApplicationContext(),"Nhập hàng thành công",Toast.LENGTH_SHORT).show();
            }else
            Toast.makeText(getApplicationContext(), "Tạo đơn thành công", Toast.LENGTH_LONG).show();
            mRequestAddTransaction.setDetail(new ArrayList<TransactionMaterialAmount>());
            mDetailTransactionAdapter.setData(mRequestAddTransaction.getDetail());
            txtDate.setText(simpleDateFormat.format(new Date(System.currentTimeMillis())));

        }
    }

    @Override
    public void loadMaterials(List<Material> materials) {

    }

    @Override
    public void returnTransactions(List<Transaction> transactions) {

    }

    @Override
    public void returnStoreMaterial(List<StoreMaterial> storeMaterials) {

    }

    @Override
    public void addExchangeStore(List<Detail> details) {
        mExchangeDetails = details;
        ArrayList<String> datas = new ArrayList<>();
        for (Detail detail :
                details) {
            if (!mRequestAddTransaction.getStoreId().equalsIgnoreCase(detail.getDetailId()))
                datas.add(detail.getDetailName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, datas);
        mAcReceiveStore.setAdapter(adapter);
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
