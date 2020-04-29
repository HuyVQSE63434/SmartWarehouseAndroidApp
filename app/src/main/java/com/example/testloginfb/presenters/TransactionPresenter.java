package com.example.testloginfb.presenters;

import android.app.Application;
import android.content.Context;

import com.example.testloginfb.callbacks.CallbackData;
import com.example.testloginfb.helpers.CheckStoreHelper;
import com.example.testloginfb.helpers.Materialhelper;
import com.example.testloginfb.helpers.StaffHelper;
import com.example.testloginfb.helpers.TransactionHelper;
import com.example.testloginfb.models.CheckStore;
import com.example.testloginfb.models.Material;
import com.example.testloginfb.models.Transaction;
import com.example.testloginfb.room.entities.StaffEntity;
import com.example.testloginfb.views.TransactionView;

import java.util.List;
import java.util.Map;

public class TransactionPresenter {
    private TransactionView mView;
    private Context mContext;
    private TransactionHelper mHelper;
    private StaffHelper mStaffHelper;
    private CheckStoreHelper mCheckStoreHelper;

    public TransactionPresenter(TransactionView mView, Context mContext, Application application) {
        this.mView = mView;
        this.mContext = mContext;
        this.mHelper = new TransactionHelper(application);
        this.mStaffHelper = new StaffHelper(application);
        this.mCheckStoreHelper = new CheckStoreHelper(application);
    }

    public void handleLoadTransactionList(String storeId, Map<String,String> params,String auth){
        mHelper.getTransactionFromServer(storeId, params, new CallbackData<List<Transaction>>() {
            @Override
            public void onSuccess(List<Transaction> transactions) {
                if(!transactions.isEmpty()){
                    mView.loadImportDetail(transactions);
                    mView.done();
                }
                mView.done();
            }

            @Override
            public void onFail(String message) {
                mView.showMessage(message);
                mView.done();
            }
        },auth);
    }

    public void handleLoadLocalStaff(){
        mStaffHelper.getLocalStaff(new CallbackData<StaffEntity>() {
            @Override
            public void onSuccess(StaffEntity staffEntity) {
                mView.loadLocalStaff(staffEntity);
            }

            @Override
            public void onFail(String message) {

            }
        });
    }

    public void handleLoadCheckStore(String id, Map<String,String> params,String auth){
        mCheckStoreHelper.getCheckStoreFromServer(id, params, new CallbackData<List<CheckStore>>() {
            @Override
            public void onSuccess(List<CheckStore> checkStores) {
                mView.loadCheckStore(checkStores);
                mView.done();
            }

            @Override
            public void onFail(String message) {
                mView.done();
            }
        },auth);
    }
}
