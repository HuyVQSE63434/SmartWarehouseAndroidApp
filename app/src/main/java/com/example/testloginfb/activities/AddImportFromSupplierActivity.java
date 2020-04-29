package com.example.testloginfb.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
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

public class AddImportFromSupplierActivity extends BaseActivity implements AddNewTransactionView, View.OnClickListener {

    private Button mBtnAddNew, mBtnAddSubmit;
    private EditText mEdtAmount;
    private AutoCompleteTextView mEdtItem, mEdtSupplier;
    //private LinearLayout mBtnEdit;
    private String mExchangeStoreId = null;
    private String mType;
    private StaffEntity mStaffEntity;
    private static List<TransactionMaterialAmount> mDatas = new ArrayList<>();
    private static ArrayList<String> mDataSearch = new ArrayList<>();
    private List<Material> mMaterialList;
    private List<Detail> mSuppliers = new ArrayList<>();
    private ArrayAdapter<String> supplierAdapter;
    private ArrayList<String> datas;
    //private ScrollChoice mScrollChoice;
    int i = 0;
    private TextView mTxtExName;
    private IntentIntegrator mQpScan;
    private AddNewTransactionPresenter mPresenter;
    private Toolbar mToolbar;
    private TransactionMaterialAmount mCurrentMaterialAmount;
    private FrameLayout mBtnScanCode;
    private ArrayAdapter<String> mMaterialAdapter;
    private int sum=0;
    private TextView mtxtSumAmount;
    private RequestAddTransaction requestAddTransaction;
    private ImageView mImgSupplierDropDown, mImgMaterialDropDown;
    private TextView mtxtMaterialUnit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_from_supplier);


        Intent intent = getIntent();
        mType = intent.getStringExtra("type");

        mStaffEntity = (StaffEntity) intent.getSerializableExtra("staff");
        requestAddTransaction = (RequestAddTransaction) intent.getSerializableExtra("requestTransaction");
        initialView();
        initialData();
    }

    private void initialData() {
        showProgressHUD();
        mPresenter = new AddNewTransactionPresenter(this, this, this.getApplication());
        //mPresenter.handleLoadMaterials();
        mPresenter.handleLoadSupplier(mStaffEntity.getAuthToken());
    }

    private void initialView() {
        mtxtSumAmount = findViewById(R.id.txt_sum_amount);
        if(requestAddTransaction.getDetail() != null){
            sum=requestAddTransaction.getDetail().size();
            mDatas = requestAddTransaction.getDetail();
        }
        else{
            sum = 0;
            mDatas = new ArrayList<>();
        }
        mtxtMaterialUnit = findViewById(R.id.txt_material_unit);
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
                String name = mMaterialAdapter.getItem(position);
                for (Material amount :
                        mMaterialList) {
                    if (amount.getName().equalsIgnoreCase(name)) {
                        mtxtMaterialUnit.setText(amount.getChange_unit());
                    }
                }
            }
        });
        mEdtItem.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                }boolean change = false;
                if(mMaterialList!=null)
                for (Material detail:
                        mMaterialList) {
                    if(detail.getName().equalsIgnoreCase(mEdtItem.getText().toString())){
                        change = true;
                    }
                }
                if(!change){
                    mEdtItem.setText("");
                    mtxtMaterialUnit.setText("");
                }
            }
        });

        mEdtSupplier = findViewById(R.id.edt_exchange_store);
        mEdtSupplier.setHint("chọn nhà cung cấp");

        mEdtSupplier.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showProgressHUD();
                String name = supplierAdapter.getItem(position);
                for (Detail supplier :
                        mSuppliers) {
                    if (supplier.getDetailName().equalsIgnoreCase(name)) {
                        showProgressHUD();
                        mPresenter.handleLoadMaterialsFromSupplier(supplier.getDetailId(),mStaffEntity.getAuthToken());
                    }
                }
            }
        });
        mEdtSupplier.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    if (mMaterialAdapter != null) {
                        mMaterialAdapter.clear();
                        mEdtItem.setAdapter(mMaterialAdapter);
                    }
                }
            }
        });
        mEdtSupplier.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                }boolean change = false;
                for (Detail detail:
                        mSuppliers) {
                    if(detail.getDetailName().equalsIgnoreCase(mEdtSupplier.getText().toString())){
                        change = true;
                    }
                }
                if(!change){
                    mEdtSupplier.setText("");
                }
            }
        });
        mImgSupplierDropDown = findViewById(R.id.img_supplier_drop_down);
        mImgSupplierDropDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mEdtSupplier.getAdapter() !=null)
                    mEdtSupplier.showDropDown();
            }
        });
        mImgMaterialDropDown = findViewById(R.id.img_material_drop_down);
        mImgMaterialDropDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mEdtItem.getAdapter() !=null){
                    mEdtItem.showDropDown();
                }
            }
        });
        //mScrollChoice = (ScrollChoice) findViewById(R.id.scroll_choice);
        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle("Tạo đơn nhập hàng cung ngoài");
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
                addItem(mType);
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
        requestAddTransaction.setDetail(mDatas);
        showDialogSubmit(requestAddTransaction);
    }

    private void showDialogSubmit(RequestAddTransaction requestAddTransaction) {

        Intent intent = new Intent();
        intent.putExtra("requestTransaction", (Serializable) requestAddTransaction);
        intent.putExtra("staff", mStaffEntity);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
    @SuppressLint("ResourceAsColor")
    private void addItem(String mType) {
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
                for (Material material :
                        mMaterialList) {
                    String item = mEdtItem.getText().toString();
                    if (material.getName().equalsIgnoreCase(item)) {
                        mCurrentMaterialAmount = new TransactionMaterialAmount(
                                new Detail(String.valueOf(material.id), material.name),
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
                        this.dismissProgressHUDNow();
                        submit(mType);
                        return;
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


    private void enableButton() {
        mBtnScanCode.setEnabled(true);
        mBtnAddSubmit.setEnabled(true);
        mBtnAddNew.setEnabled(true);
    }

    private void disableButton() {
        mBtnScanCode.setEnabled(false);
        mBtnAddSubmit.setEnabled(false);
    }

    @Override
    public void updateScanMaterial(Material material) {
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
        this.dismissProgressHUDNow();
    }

    @Override
    public void returnAddResult(boolean aBoolean) {
    }

    @Override
    public void loadMaterials(List<Material> materials) {
        mMaterialList = materials;
        mDataSearch = new ArrayList<>();
        for (Material material :
                materials) {
            mDataSearch.add(material.name);
        }
        mMaterialAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, mDataSearch);
        mEdtItem.setAdapter(mMaterialAdapter);
    }

    @Override
    public void returnTransactions(List<Transaction> transactions) {
    }

    @Override
    public void returnStoreMaterial(List<StoreMaterial> storeMaterials) {
    }

    @Override
    public void addExchangeStore(List<Detail> details) {
    }

    @Override
    public void addSuppliers(List<Detail> details) {
        mSuppliers = details;
        datas = new ArrayList<>();
        for (Detail detail :
                details) {
            if (!mStaffEntity.getStoreId().equalsIgnoreCase(detail.getDetailId()))
                datas.add(detail.getDetailName());
        }
        supplierAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, datas);
        mEdtSupplier.setAdapter(supplierAdapter);
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
                    for (Material material :
                            mMaterialList) {
                        if (material.getBarcode() == Long.parseLong(barCode)) {
                            mEdtItem.setText(material.getName());
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
                                    mEdtAmount.setText(edt.getText());
                                    addItem(mType);
                                    dialog.dismiss();
                                    dialog.dismiss();
                                }
                            });
                            dialog.show();
                        }
                    }
                    if (mEdtItem.getText().toString().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Hàng hóa không có", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Hàng hóa không có", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
