package com.example.testloginfb.activities;

import android.app.Dialog;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testloginfb.R;
import com.example.testloginfb.adapters.NewsHorizontalAdapter;
import com.example.testloginfb.models.RequestAddTransaction;
import com.example.testloginfb.models.Staff;
import com.example.testloginfb.presenters.HomeScreenPresenter;
import com.example.testloginfb.room.entities.StaffEntity;
import com.example.testloginfb.views.HomeScreenView;

import java.io.Serializable;
import java.sql.Timestamp;


public class HomeScreenActivity extends BaseActivity implements HomeScreenView, View.OnClickListener,
        NavigationView.OnNavigationItemSelectedListener {
    private Toolbar mToolbar;
    //private Button mBtnHistory, mBtnTransaction, mBtnStock;
    private LinearLayout mLnImportFromStore, mLnImportOutSide, mLnImportAfterSale,
            mLnExportToSale, mLnExportToClear/*, mLnCheckStore*/, mLnExchange, mLnInventory, mHisImport, mHisExport;
    private ImageView mImgNoti;
    private LinearLayout mLnlStaff;
    private TextView mTxtStore, mTxtStaffName;
    private HomeScreenPresenter mHomeScreenPresenter;

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    Dialog dialog = null;
    private StaffEntity mstaffEntity;
    private static Staff mStaff;

    private NewsHorizontalAdapter mNewsHorizontalAdapter;
    private RecyclerView mRcvNewsDetail, mRcvTricksDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home_screen);

        initialView();
        initialData();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    private void initialData() {
        mHomeScreenPresenter = new HomeScreenPresenter(this, this, getApplication());
        if (mstaffEntity == null)
            mHomeScreenPresenter.handleLoadStaffFromRoom();
    }

    private void initialView() {
        dialog = new Dialog(HomeScreenActivity.this);
        mToolbar = findViewById(R.id.tb_home_screen);

        /*mBtnHistory = findViewById(R.id.btn_home_screen_history);
        mBtnTransaction = findViewById(R.id.btn_home_screen_transaction);
        mBtnStock = findViewById(R.id.btn_home_screen_stock);*/

        mLnExchange = findViewById(R.id.ln_exchange);
        mLnExchange.setOnClickListener(this);

        mLnImportFromStore = findViewById(R.id.ln_import_from_store);
        mLnImportFromStore.setOnClickListener(this);
        mLnImportOutSide = findViewById(R.id.ln_import_from_out_side);
        mLnImportOutSide.setOnClickListener(this);
        mLnImportAfterSale = findViewById(R.id.ln_import_after_sale);
        mLnImportAfterSale.setOnClickListener(this);

        mLnExportToSale = findViewById(R.id.ln_export_to_sale);
        mLnExportToSale.setOnClickListener(this);
        mLnExportToClear = findViewById(R.id.ln_export_to_clear);
        mLnExportToClear.setOnClickListener(this);

        /*mLnCheckStore = findViewById(R.id.ln_check_store);
        mLnCheckStore.setOnClickListener(this);
*/
        mLnInventory = findViewById(R.id.ln_inventory);
        mLnInventory.setOnClickListener(this);

        mImgNoti = findViewById(R.id.img_home_screen_notification);
        //mTxtStore = findViewById(R.id.txt_tb_main_store);
        mTxtStaffName = findViewById(R.id.txt_tb_main_employee);
        mLnlStaff = findViewById(R.id.lnl_home_screen_staff_name);
        mLnlStaff.setOnClickListener(this);

        mHisExport = findViewById(R.id.ln_his_export);
        mHisExport.setOnClickListener(this);
        mHisImport = findViewById(R.id.ln_his_import);
        mHisImport.setOnClickListener(this);

       /* mBtnTransaction.setOnClickListener(this);
        mBtnHistory.setOnClickListener(this);
        mBtnStock.setOnClickListener(this);*/
        mImgNoti.setOnClickListener(this);
        /*mBtnStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInventoryActivity();
            }
        });*/

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.nav_view);
        mNavigationView.inflateMenu(R.menu.drawer_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        mNavigationView.setNavigationItemSelectedListener(this);
        setSupportActionBar(mToolbar);
        /*ImageView menu = findViewById(R.id.btn_menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mDrawerLayout.isDrawerOpen(GravityCompat.START)){
                    mDrawerLayout.openDrawer(Gravity.LEFT);
                }else{
                    mDrawerLayout.closeDrawer(Gravity.LEFT);
                }
            }
        });*/
        mRcvNewsDetail = findViewById(R.id.news_recyclerView);
        mRcvTricksDetail = findViewById(R.id.trick_recyclerView);
        mNewsHorizontalAdapter = new NewsHorizontalAdapter(1);
        mNewsHorizontalAdapter.setmListener(new NewsHorizontalAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getApplicationContext(), ShowNewsActivity.class);
                intent.putExtra("item", (Serializable) mNewsHorizontalAdapter.getNewsData().get(position));
                startActivity(intent);
            }

            @Override
            public void onItemDelete(int position) {

            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        mRcvNewsDetail.setLayoutManager(linearLayoutManager);
        mRcvNewsDetail.setAdapter(mNewsHorizontalAdapter);
        final NewsHorizontalAdapter newsHorizontalAdapter = new NewsHorizontalAdapter(0);
        newsHorizontalAdapter.setmListener(new NewsHorizontalAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getApplicationContext(), ShowNewsActivity.class);
                intent.putExtra("item", (Serializable) newsHorizontalAdapter.getNewsData().get(position));
                startActivity(intent);
            }

            @Override
            public void onItemDelete(int position) {

            }
        });
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        mRcvTricksDetail.setLayoutManager(linearLayoutManager1);
        mRcvTricksDetail.setAdapter(newsHorizontalAdapter);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_log_out:
                showDialogConfirmLogout();
                break;
            case R.id.nav_import:
                goToDetailTransaction("import");
                break;
            case R.id.nav_export:
                dialog.setContentView(R.layout.transaction_popup);
                Button btnExport = dialog.findViewById(R.id.btnExport);
                Button btnExchangge = dialog.findViewById(R.id.btnExchange);
                btnExchangge.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goToDetailTransaction("exchange");
                    }
                });
                btnExport.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goToDetailTransaction("export");
                    }
                });
                dialog.show();
                break;
            /*case R.id.nav_checkStore:
                goToDetailTransaction("checkStore");
                break;*/
            case R.id.nav_inventory:
                openInventoryActivity();
                break;

        }
        mDrawerLayout.closeDrawer(Gravity.LEFT);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void openInventoryActivity() {
        Intent intent = new Intent(this, InventoryActivity.class);
        startActivity(intent);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /*case R.id.btn_home_screen_transaction:
                showAddDialog();
                break;
            case R.id.btn_home_screen_history:
                showHistoryDialog();
                break;*/
            case R.id.img_home_screen_notification:
                //todo: show notification
                break;
            case R.id.lnl_home_screen_staff_name:
                break;
            case R.id.ln_exchange:
                goToAddExchange("EC");
                break;
            case R.id.ln_import_from_store:
                goToAddTransaction("IM01");
                break;
            case R.id.ln_import_from_out_side:
                goToAddImpportEx("IM02");
                break;
            case R.id.ln_import_after_sale:
                goToAddTransaction("IMAF");
                break;
            case R.id.ln_export_to_sale:
                goToAddTransaction("EX01");
                break;
            case R.id.ln_export_to_clear:
                goToAddTransaction("EX02");
                break;
            /*case R.id.ln_check_store:
                goToAddTransaction("checkStore");
                break;*/
            case R.id.ln_inventory:
                openInventoryActivity();
                break;
            case R.id.ln_his_import:
                goToDetailTransaction("import");
                break;
            case R.id.ln_his_export:
                dialog.setContentView(R.layout.transaction_popup);
                Button btnExport = dialog.findViewById(R.id.btnExport);
                Button btnExchangge = dialog.findViewById(R.id.btnExchange);
                btnExchangge.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goToDetailTransaction("exchange");
                    }
                });
                btnExport.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goToDetailTransaction("export");
                    }
                });
                dialog.show();
                break;
        }
    }

    private void goToAddTransaction(String type) {
        /*Intent intent = new Intent(getApplicationContext(), AddTransactionActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("staff", mstaffEntity);
        startActivity(intent);*/

        RequestAddTransaction requestAddTransaction = new RequestAddTransaction();
        requestAddTransaction.setDetail(null);
        requestAddTransaction.setStatusId("HT");
        requestAddTransaction.setTransactionTypeId(type);
        requestAddTransaction.setStaffId(mstaffEntity.getStaffId().toString());

        Timestamp timestamp = new Timestamp(System.currentTimeMillis() / 1000);
        requestAddTransaction.setTime(timestamp.toString());
        requestAddTransaction.setStoreId(mstaffEntity.getStoreId());
        Intent intent = new Intent(this, ShowDetailTransactionActivity.class);
        intent.putExtra("requestTransaction", (Serializable) requestAddTransaction);
        intent.putExtra("staff", mstaffEntity);
        //intent.putExtra("exchangeSimpleStore", (Serializable) mExchangeDetails);
        //intent.putExtra("dataEc", (Serializable) mDatasEc);
        startActivity(intent);
    }

    private void goToAddExchange(String type) {
/*        Intent intent = new Intent(getApplicationContext(), AddExchangeActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("staff", mstaffEntity);
        startActivity(intent);*/

        RequestAddTransaction requestAddTransaction = new RequestAddTransaction();
        requestAddTransaction.setDetail(null);
        requestAddTransaction.setStatusId("ĐT");
        requestAddTransaction.setTransactionTypeId(type);
        requestAddTransaction.setStaffId(mstaffEntity.getStaffId().toString());

        Timestamp timestamp = new Timestamp(System.currentTimeMillis() / 1000);
        requestAddTransaction.setTime(timestamp.toString());
        requestAddTransaction.setStoreId(mstaffEntity.getStoreId());
        Intent intent = new Intent(this, ShowDetailExchangeActivity.class);
        intent.putExtra("requestTransaction", (Serializable) requestAddTransaction);
        intent.putExtra("staff", mstaffEntity);
        //intent.putExtra("exchangeSimpleStore", (Serializable) mExchangeDetails);
        //intent.putExtra("dataEc", (Serializable) mDatasEc);
        startActivity(intent);
    }

    private void goToAddImpportEx(String type) {
        /*Intent intent = new Intent(getApplicationContext(), AddImportFromSupplierActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("staff", mstaffEntity);
        startActivity(intent);
*/
        RequestAddTransaction requestAddTransaction = new RequestAddTransaction();
        requestAddTransaction.setDetail(null);
        requestAddTransaction.setStatusId("HT");
        requestAddTransaction.setTransactionTypeId(type);
        requestAddTransaction.setStaffId(mstaffEntity.getStaffId().toString());

        Timestamp timestamp = new Timestamp(System.currentTimeMillis() / 1000);
        requestAddTransaction.setTime(timestamp.toString());
        requestAddTransaction.setStoreId(mstaffEntity.getStoreId());
        Intent intent = new Intent(this, ShowDetailTransactionActivity.class);
        intent.putExtra("requestTransaction", (Serializable) requestAddTransaction);
        intent.putExtra("staff", mstaffEntity);
        //intent.putExtra("exchangeSimpleStore", (Serializable) mExchangeDetails);
        //intent.putExtra("dataEc", (Serializable) mDatasEc);
        startActivity(intent);
    }

    private void showHistoryDialog() {
        final Dialog dialog = new Dialog(HomeScreenActivity.this);
        dialog.setContentView(R.layout.dialog_choose_mode);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        LinearLayout lnReceipt = dialog.findViewById(R.id.lnl_dialog_receipt);
        lnReceipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        LinearLayout lnLinearLayout = dialog.findViewById(R.id.lnl_dialog_issue);
        lnLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        LinearLayout lnCheck = dialog.findViewById(R.id.lnl_dialog_check);
        lnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        dialog.show();
    }

    private void goToDetailTransaction(String type) {
        Intent intent = new Intent(getApplicationContext(), GetDetailTransactionActivity.class);
        intent.putExtra("type", type);
        startActivity(intent);

    }

    @Override
    public void loadLocalStaff(StaffEntity staffEntity) {
//        mTxtStore.setText(staffEntity.getStore() + ",");
        mstaffEntity = staffEntity;
        //mTxtStaffName.setText("Xin chào " + staffEntity.getPosition().toLowerCase() + " " + staffEntity.getStaffName() + ("!"));
        mTxtStaffName.setText("Trang Chủ");
        View lnHeader = mNavigationView.getHeaderView(0);
        TextView txtUserName = lnHeader.findViewById(R.id.txt_user_name);
        txtUserName.setText(staffEntity.getStaffName());
        TextView txtUserMail = lnHeader.findViewById(R.id.txt_user_gmail);
        txtUserMail.setText(staffEntity.getStaffMail());
        TextView txtUserPosition = lnHeader.findViewById(R.id.txt_user_position);
        txtUserPosition.setText(staffEntity.getPosition());
        TextView txtHeaderStore = findViewById(R.id.txt_header_store);
        txtHeaderStore.setText("Cửa hàng: " + staffEntity.getStore());
        TextView txtHeaderAddress = findViewById(R.id.txt_header_address);
        Intent intent = getIntent();
        mStaff = (Staff) intent.getSerializableExtra("staff");
        txtHeaderAddress.setText("Địa chỉ: " + mStaff.getStoreId().getStoreLocation());
        TextView txtHeaderStaff = findViewById(R.id.txt_header_staff);
        txtHeaderStaff.setText("Nhân viên: " + staffEntity.getStaffName());

    }

    @Override
    public void showDialogConfirmLogout() {
        final Dialog dialog = new Dialog(HomeScreenActivity.this);
        dialog.setContentView(R.layout.dialog_log_out);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button mBtnNo = dialog.findViewById(R.id.btn_dialog_back);
        Button mBtnYes = dialog.findViewById(R.id.btn_dialog_continue);
        mBtnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        mBtnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHomeScreenPresenter.removeDeviceToken(mStaff.getStaffId().toString(),mstaffEntity.getAuthToken());
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void goToMainActivity() {
        MainActivity.moveToMainActivity(HomeScreenActivity.this);
    }
}
