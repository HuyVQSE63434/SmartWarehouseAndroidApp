package com.example.testloginfb.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testloginfb.R;
import com.example.testloginfb.models.Detail;
import com.example.testloginfb.models.Material;
import com.example.testloginfb.models.RequestAddCheckStore;
import com.example.testloginfb.models.RequestAddTransaction;
import com.example.testloginfb.models.StoreMaterial;
import com.example.testloginfb.models.Transaction;
import com.example.testloginfb.models.TransactionMaterialAmount;
import com.example.testloginfb.presenters.AddNewTransactionPresenter;
import com.example.testloginfb.room.entities.StaffEntity;
import com.example.testloginfb.views.AddNewTransactionView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.webianks.library.scroll_choice.ScrollChoice;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.CollationElementIterator;
import java.util.ArrayList;
import java.util.List;

public class AddTransactionActivity extends BaseActivity implements AddNewTransactionView, View.OnClickListener {

    private Button mBtnAddNew, mBtnAddSubmit;
    private EditText mEdtAmount;
    private AutoCompleteTextView mEdtItem, mEdtExchangeStore;
    //private LinearLayout mBtnEdit;
    private String mExchangeStoreId = null;

    private String mType;
    private StaffEntity mStaffEntity;

    private static List<TransactionMaterialAmount> mDatas = new ArrayList<>();
    private static List<TransactionMaterialAmount> mDatasEc = new ArrayList<>();
    private static List<String> mDataScrolls = new ArrayList<>();
    private static ArrayList<String> mDataSearch = new ArrayList<>();
    private static List<Material> materialList = new ArrayList<>();
    private static List<Detail> mExchangeDetails = new ArrayList<>();
    //private ScrollChoice mScrollChoice;
    int i = 0;

    private TextView mTxtResult;

    private IntentIntegrator mQpScan;

    private AddNewTransactionPresenter mPresenter;

    private Toolbar mToolbar;

    private TransactionMaterialAmount mCurrentMaterialAmount;

    private FrameLayout mBtnScanCode;
    private int sum=0;
    private TextView mtxtSumAmount;
    private RequestAddTransaction mRequestTransaction;
    private Intent intent;
    private ImageView mImgMaterialDropdown;
    private TextView mtxtMaterialUnit;
    private ArrayAdapter<String> mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);


        intent = getIntent();
        mType = intent.getStringExtra("type");

        mStaffEntity = (StaffEntity) intent.getSerializableExtra("staff");

        mRequestTransaction = (RequestAddTransaction) intent.getSerializableExtra("requestTransaction");
        initialView();
        initialData();
    }


    private void setToolbarTitleAndLimitMaterial(String mType) {
        switch (mType) {
            case "IM01":
                mToolbar.setTitle("Tạo đơn nhập hàng từ kho khác");
                mDataSearch = new ArrayList<>();
                //mPresenter.handleLoadTransactionEC(mStaffEntity.getStoreId(),mStaffEntity.getAuthToken());
                mDatasEc = (List<TransactionMaterialAmount>) intent.getSerializableExtra("mDatasEc");
                for (TransactionMaterialAmount amount:
                     mDatasEc) {
                    mDataSearch.add(amount.getMaterial().getDetailName());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, mDataSearch);
                mEdtItem.setAdapter(adapter);
                dismissProgressHUDNow();
                break;
            case "IM02":
                mToolbar.setTitle("Tạo đơn nhập hàng từ nhà cung cấp");
                mPresenter.handleLoadMaterials(mStaffEntity.getAuthToken());
                break;
            case "IMAF":
                mToolbar.setTitle("Tạo đơn nhập hàng sau bán");
                //mPresenter.handleLoadtransaction(mStaffEntity.getStoreId(), "EX01");
                mPresenter.handleLoadMaterialsByStore(mStaffEntity.getStoreId(),mStaffEntity.getAuthToken());
                break;
            case "EX01":
                mToolbar.setTitle("Tạo đơn xuất hàng cho bán");
                mPresenter.handleLoadMaterialsByStore(mStaffEntity.getStoreId(),mStaffEntity.getAuthToken());
                break;
            case "EX02":
                mToolbar.setTitle("Tạo đơn xuất hàng hỏng mốc");
                mPresenter.handleLoadMaterialsByStore(mStaffEntity.getStoreId(),mStaffEntity.getAuthToken());
                break;
            case "EC":
                mToolbar.setTitle("Tạo đơn chuyển kho");
                mPresenter.handleLoadMaterialsByStore(mStaffEntity.getStoreId(),mStaffEntity.getAuthToken());
                break;
            case "checkStore":
                mToolbar.setTitle("Tạo đơn kiểm kho");
                mPresenter.handleLoadMaterialsByStore(mStaffEntity.getStoreId(),mStaffEntity.getAuthToken());
                break;
        }
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
                String name = (String) mEdtItem.getAdapter().getItem(position);
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
        mImgMaterialDropdown = findViewById(R.id.img_material_drop_down);
        mImgMaterialDropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mEdtItem.getAdapter() !=null){
                    mEdtItem.showDropDown();
                }
            }
        });
        mEdtExchangeStore = findViewById(R.id.edt_exchange_store);

        //mScrollChoice = (ScrollChoice) findViewById(R.id.scroll_choice);

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

        mQpScan = new IntentIntegrator(this);
    }

    private void initialData() {
        showProgressHUD();
        mPresenter = new AddNewTransactionPresenter(this, getApplicationContext(), this.getApplication());
        setToolbarTitleAndLimitMaterial(mType);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /*case R.id.btn_plus_edit:
                edit();
                break;*/
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

    @RequiresApi(api = Build.VERSION_CODES.O)
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
                            mDataScrolls.add(mCurrentMaterialAmount.getMaterial().getDetailName());
                            //mScrollChoice.addItems(mDataScrolls, mScrollChoice.getScrollBarSize() - 1);
                            mEdtItem.setText("");
                            mEdtAmount.setText("");
                            //mBtnEdit.setEnabled(true);
                            mCurrentMaterialAmount = null;
                            sum+=1;
                            mtxtSumAmount.setText("Số hàng hiện tại: "+sum);
                            //Toast.makeText(this, "Trong đơn hàng đã có " + String.valueOf(mDatas.size()) + " sản phẩm", Toast.LENGTH_LONG).show();
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void submit(String mType) {
        this.showProgressHUD();
            mRequestTransaction.setDetail(mDatas);
            showDialogSubmit(mRequestTransaction);
            //mPresenter.handleAddTrannsaction(requestAddTransaction);

            /*RequestAddCheckStore addCheckStore = new RequestAddCheckStore();
            addCheckStore.setDetail(mDatas);
            addCheckStore.setStaffId(mStaffEntity.getStaffId().toString());

            Timestamp timestamp = new Timestamp(System.currentTimeMillis() / 1000);
            addCheckStore.setTime(timestamp.getTime());
            addCheckStore.setStoreId(mStaffEntity.getStoreId());
            mPresenter.handleAddCheckStore(mStaffEntity.getStoreId(), addCheckStore);
        */

            dismissProgressHUDNow();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void showDialogSubmit(RequestAddTransaction requestAddTransaction) {

        Intent intent = new Intent();
        intent.putExtra("requestTransaction", (Serializable) requestAddTransaction);
        intent.putExtra("staff", mStaffEntity);
        intent.putExtra("dataEc", (Serializable) mDatasEc);
        setResult(Activity.RESULT_OK,intent);
        finish();

        /*if (requestAddTransaction.getDetail().size() < 1) {
            Toast.makeText(this, "Chưa có món hàng nào để tạo đơn", Toast.LENGTH_LONG).show();
            return;
        }
        Intent intent = new Intent(this, ShowDetailTransactionActivity.class);
        intent.putExtra("requestTransaction", (Serializable) requestAddTransaction);
        intent.putExtra("staff", mStaffEntity);
        intent.putExtra("dataEc", (Serializable) mDatasEc);
        startActivityForResult(intent,111);
*/
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

    private boolean checkAmount() {
        for (TransactionMaterialAmount amount :
                mDatasEc) {
            String item = mEdtItem.getText().toString();
            if (amount.getMaterial().getDetailName().equalsIgnoreCase(item)) {
                Long edtamount = Long.parseLong(mEdtAmount.getText().toString());
                if (amount.getMaterialAmount() < edtamount) {
                    Toast.makeText(this, "Số lượng cần bằng hoặc dưới " + amount.getMaterialAmount(), Toast.LENGTH_LONG).show();
                    return false;
                }
            }
        }
        return true;
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
        mDataScrolls.clear();
    }

    @Override
    public void done() {
        this.dismissProgressHUDNow();
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
        if (mType.equalsIgnoreCase("IM01")) {
            if (transactions == null) {
                dismissProgressHUDNow();
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
                mExchangeStoreId = transactions.get(0).getExchangeStore().getDetailId();
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
                            mDataSearch.add(amount.getMaterial().getDetailName());
                        }
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, mDataSearch);
                mEdtItem.setAdapter(adapter);
            }
        } else {
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
                        mDataSearch.add(amount.getMaterial().getDetailName());
                    }
                }
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, mDataSearch);
            mEdtItem.setAdapter(adapter);
        }
    }

    @Override
    public void returnStoreMaterial(List<StoreMaterial> storeMaterials) {
        mDatasEc.clear();
        mDataSearch.clear();
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

        if (requestCode == 111) {
            if (resultCode == Activity.RESULT_OK) {

                Toast.makeText(getApplicationContext(), "Tạo đơn thành công", Toast.LENGTH_LONG).show();
                doneAddTransaction();
                return;
            }
            if (resultCode == Activity.RESULT_FIRST_USER) {
                List<TransactionMaterialAmount> newData = (List<TransactionMaterialAmount>) data.getSerializableExtra("datas");
                if (newData != null) {
                    mDatas = newData;
                    sum=mDatas.size();
                    mtxtSumAmount.setText("Số hàng hiện tại: "+sum);
                    return;
                }
            }
        }
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
                                @RequiresApi(api = Build.VERSION_CODES.O)
                                @Override
                                public void onClick(View v) {
                                    mEdtAmount.setText(edt.getText());
                                    addItem();
                                    dialog.dismiss();
                                    dialog.dismiss();
                                }
                            });
                            dialog.show();
                            return;
                        }
                    }
                    Toast.makeText(getApplicationContext(),"Hàng hóa không có",Toast.LENGTH_SHORT).show();
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
