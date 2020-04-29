package com.example.testloginfb.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testloginfb.R;
import com.example.testloginfb.models.Detail;
import com.example.testloginfb.models.Material;
import com.example.testloginfb.models.RequestAddTransaction;
import com.example.testloginfb.models.StoreMaterial;
import com.example.testloginfb.models.Transaction;
import com.example.testloginfb.models.TransactionMaterialAmount;
import com.example.testloginfb.presenters.AddNewTransactionPresenter;
import com.example.testloginfb.room.entities.StaffEntity;
import com.example.testloginfb.views.AddNewTransactionView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AddExchangeActivity extends BaseActivity implements AddNewTransactionView, View.OnClickListener {

    private Button mBtnAddNew, mBtnAddSubmit;
    private EditText mEdtAmount;
    private AutoCompleteTextView mEdtItem /*mEdtExchangeStore*/;
    //private LinearLayout mBtnEdit;
    private String mExchangeStoreId = null;

    private String mType;
    private StaffEntity mStaffEntity;

    private static List<TransactionMaterialAmount> mDatas = new ArrayList<>();
    private static List<TransactionMaterialAmount> mDatasEc = new ArrayList<>();
    //private static List<String> mDataScrolls = new ArrayList<>();
    private static ArrayList<String> mDataSearch = new ArrayList<>();
    private static List<Material> materialList = new ArrayList<>();
    private static List<Detail> mExchangeDetails = new ArrayList<>();
    //private ScrollChoice mScrollChoice;
    int i = 0;

    private TextView mtxtSumAmount;
    private int sum=0;

    private IntentIntegrator mQpScan;

    private AddNewTransactionPresenter mPresenter;

    private Toolbar mToolbar;

    private TransactionMaterialAmount mCurrentMaterialAmount;

    private FrameLayout mBtnScanCode;
    private RequestAddTransaction mRequestTransaction;
    private ImageView mImgMaterialDropDown;
    private ArrayAdapter<String> mAdapter;
    private TextView mtxtMaterialUnit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exchange_store);


        Intent intent = getIntent();
        mType = intent.getStringExtra("type");

        mStaffEntity = (StaffEntity) intent.getSerializableExtra("staff");
        mRequestTransaction = (RequestAddTransaction) intent.getSerializableExtra("requestTransaction");
        initialView();
        initialData();
    }

    private void initialData() {
        showProgressHUD();
        mPresenter = new AddNewTransactionPresenter(this, getApplicationContext(), this.getApplication());
        mPresenter.handleLoadMaterialsByStore(mStaffEntity.getStoreId(),mStaffEntity.getAuthToken());
        //mPresenter.hanleGetSimpleStore();
    }

    private void initialView() {
        /*mBtnEdit = findViewById(R.id.btn_plus_edit);
        mBtnEdit.setOnClickListener(this);
        if (mDataScrolls.size() == 0) mBtnEdit.setEnabled(false);
        */
        mtxtSumAmount = findViewById(R.id.txt_sum_amount);
        if(mRequestTransaction.getDetail() != null){
            sum=mRequestTransaction.getDetail().size();
            mDatas = mRequestTransaction.getDetail();
        }
        else{
            sum = 0;
            mDatas = new ArrayList<>();
        }
        mtxtMaterialUnit = findViewById(R.id.txt_exchange_material_unit);
        mtxtSumAmount.setText("Số hàng hiện tại: "+sum);
        mBtnAddNew = findViewById(R.id.btn_add_item);
        mBtnAddNew.setOnClickListener(this);
        mBtnScanCode = findViewById(R.id.btn_scan_code);
        mBtnScanCode.setOnClickListener(this);
        //mBtnAddSubmit = findViewById(R.id.btn_add_submit);
        //mBtnAddSubmit.setOnClickListener(this);

        mEdtAmount = findViewById(R.id.edt_plus_amount);
        mEdtItem = findViewById(R.id.edt_plus_item);
        mEdtItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = mAdapter.getItem(position);
                for (TransactionMaterialAmount amount :
                        mDatasEc) {
                    if (amount.getMaterial().getDetailName().equalsIgnoreCase(name)) {
                        mtxtMaterialUnit.setText(amount.getUnit());
                    }
                }
            }
        });
        mEdtItem.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                }boolean change = false;
                for (TransactionMaterialAmount detail:
                        mDatasEc) {
                    if(detail.getMaterial().getDetailName().equalsIgnoreCase(mEdtItem.getText().toString())){
                        change = true;
                    }
                }
                if(!change){
                    mEdtItem.setText("");
                    mtxtMaterialUnit.setText("");
                }
            }
        });

        mImgMaterialDropDown = findViewById(R.id.img_material_drop_down);
        mImgMaterialDropDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mEdtItem.getAdapter()!=null){
                    mEdtItem.showDropDown();
                }
            }
        });

        //mEdtExchangeStore = findViewById(R.id.edt_exchange_store);

        //mScrollChoice = (ScrollChoice) findViewById(R.id.scroll_choice);

        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle("Tạo đơn chuyển kho");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mQpScan = new IntentIntegrator(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /*case R.id.btn_plus_edit:
                edit();
                break;
            */
            case R.id.btn_add_item:
                addItem();
                break;
            /*case R.id.btn_add_submit:
                submit(mType);
                break;*/
            case R.id.btn_scan_code:
                mQpScan.initiateScan();
                break;
        }
    }

    private void submit(String mType) {
        mRequestTransaction.setDetail(mDatas);
        showDialogSubmit(mRequestTransaction);
    }

    private void showDialogSubmit(RequestAddTransaction requestAddTransaction) {
        Intent intent = new Intent();
        intent.putExtra("requestTransaction", (Serializable) requestAddTransaction);
        intent.putExtra("staff", mStaffEntity);
        intent.putExtra("exchangeSimpleStore", (Serializable) mExchangeDetails);
        intent.putExtra("dataEc", (Serializable) mDatasEc);
        setResult(Activity.RESULT_OK,intent);
        finish();

    }

    @SuppressLint("ResourceAsColor")
    private void addItem() {
        for (TransactionMaterialAmount amount :
                mDatas) {
            if (amount.getMaterial().getDetailName().equalsIgnoreCase(mEdtItem.getText().toString())){
                Toast.makeText(getApplicationContext(), "Hàng đã có trong đơn hàng", Toast.LENGTH_SHORT).show();
                this.dismissProgressHUDNow();
                return;
            }

        }
        if (!mEdtItem.getText().toString().isEmpty()) {
            if (!mEdtAmount.getText().toString().isEmpty()) {
                this.showProgressHUD();
                for (TransactionMaterialAmount amount :
                        mDatasEc) {
                    String item = mEdtItem.getText().toString();
                    if (amount.getMaterial().getDetailName().equalsIgnoreCase(item)) {
                        Long edtamount = Long.parseLong(mEdtAmount.getText().toString());
                        if (amount.getMaterialAmount() >= edtamount) {
                            mCurrentMaterialAmount = new TransactionMaterialAmount(
                                    new Detail(String.valueOf(amount.getMaterial().getDetailId()),
                                            amount.getMaterial().getDetailName()),
                                    Long.parseLong(mEdtAmount.getText().toString()),mtxtMaterialUnit.getText().toString());
                            mDatas.add(mCurrentMaterialAmount);
                            //mDataScrolls.add(mCurrentMaterialAmount.getMaterial().getDetailName());
                            //mScrollChoice.addItems(mDataScrolls, mScrollChoice.getScrollBarSize() - 1);
                            mEdtItem.setText("");
                            mEdtAmount.setText("");
                            //mBtnEdit.setEnabled(true);
                            mCurrentMaterialAmount = null;
                            sum+=1;
                            mtxtSumAmount.setText("Số hàng hiện tại: "+sum);
                            Toast.makeText(this,"Trong đơn hàng đã có " + String.valueOf(mDatas.size()) + " sản phẩm", Toast.LENGTH_LONG).show();
                            this.dismissProgressHUDNow();
                            submit(mType);
                            return;
                        } else {
                            Toast.makeText(this, "Số lượng cần dưới " + amount.getMaterialAmount(), Toast.LENGTH_LONG).show();
                            this.dismissProgressHUDNow();
                            return;
                        }
                    }
                }
                Toast.makeText(getApplicationContext(),"hàng không tồn tại",Toast.LENGTH_LONG).show();
            } else {
                dismissProgressHUDNow();
                Toast.makeText(this, "Xin nhập số lượng lại", Toast.LENGTH_LONG).show();
                this.dismissProgressHUDNow();
                return;
            }
        } else {
            Toast.makeText(getApplicationContext(), "Xin nhập hoặc quét mã", Toast.LENGTH_SHORT).show();
            this.dismissProgressHUDNow();
            return;
        }
        this.dismissProgressHUDNow();
    }

    private boolean checkAmount() {
        for (TransactionMaterialAmount amount :
                mDatasEc) {
            if (mEdtItem.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Xin chọn tên hàng hóa", Toast.LENGTH_SHORT).show();
                return false;
            }
            String item = mEdtItem.getText().toString();
            if (amount.getMaterial().getDetailName().equalsIgnoreCase(item)) {
                Long edtamount = Long.parseLong(mEdtAmount.getText().toString());
                if (amount.getMaterialAmount() < edtamount) {
                    Toast.makeText(this, "Số lượng cần bằng hoặc dưới " + amount.getMaterialAmount(), Toast.LENGTH_LONG).show();
                    return false;
                } else return true;
            }
        }
        Toast.makeText(getApplicationContext(), "Hàng hóa không phù hợp", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void updateScanMaterial(Material material) {
        mCurrentMaterialAmount = new TransactionMaterialAmount(new Detail(String.valueOf(material.id), material.name), 0,material.changeUnit);
        mEdtItem.setText(material.name);
        if (mEdtAmount.getText() != null) {
            mBtnAddNew.callOnClick();
        } else {
            Toast.makeText(this, "Xin nhập số lượng", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void doneAddTransaction() {
        mDatas.clear();
        mCurrentMaterialAmount = null;
        sum=0;
        mtxtSumAmount.setText("Số hàng hiện tại: "+sum);
        //mDataScrolls.clear();
    }

    @Override
    public void done() {
        dismissProgressHUDNow();
    }

    @Override
    public void returnAddResult(boolean aBoolean) {

        Toast.makeText(getApplicationContext(), "submit done!", Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Add process is successful", Toast.LENGTH_LONG).show();
    }

    @Override
    public void loadMaterials(List<Material> materials) {
        materialList = materials;
        mDataSearch = new ArrayList<>();
        for (Material material :
                materials) {
            mDataSearch.add(material.name);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, mDataSearch);
        mEdtItem.setAdapter(adapter);
    }

    @Override
    public void returnTransactions(List<Transaction> transactions) {
    }

    @Override
    public void returnStoreMaterial(List<StoreMaterial> storeMaterials) {
        mDatasEc= new ArrayList<>();
        mDataSearch = new ArrayList<>();
        for (StoreMaterial storeMaterial :
                storeMaterials) {
            TransactionMaterialAmount amount = new TransactionMaterialAmount(new Detail(storeMaterial.getMaterialName().getDetailId(),
                    storeMaterial.getMaterialName().getDetailName()), storeMaterial.getInventoryAmmount(),storeMaterial.getUnit());
            mDatasEc.add(amount);
            mDataSearch.add(amount.getMaterial().getDetailName());
        }
        mAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, mDataSearch);
        mEdtItem.setAdapter(mAdapter);
    }

    @Override
    public void addExchangeStore(List<Detail> details) {
    }

    @Override
    public void addSuppliers(List<Detail> details) {
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Không có kết quả", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                try {
                    String content = result.getContents();
                    String barCode = content.replace("\n", "");
                    for (TransactionMaterialAmount material :
                            mDatasEc) {
                        if (material.getMaterial().getDetailId().equalsIgnoreCase(barCode)) {
                            mEdtItem.setText(material.getMaterial().getDetailName());
                            final Dialog dialog = new Dialog(this);
                            dialog.setContentView(R.layout.popup_add_amount);
                            final EditText edt = dialog.findViewById(R.id.edt_amount);
                            Button btnCancel = dialog.findViewById(R.id.btn_cancel);
                            Button btnOk = dialog.findViewById(R.id.btn_ok);
                            btnCancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mEdtItem.setText("");
                                    dialog.dismiss();
                                    dialog.dismiss();
                                }
                            });
                            btnOk.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                    dialog.dismiss();
                                    mEdtAmount.setText(edt.getText());
                                    addItem();
                                }
                            });
                            dialog.show();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
