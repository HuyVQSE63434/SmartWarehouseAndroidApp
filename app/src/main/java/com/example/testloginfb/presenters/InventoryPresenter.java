package com.example.testloginfb.presenters;

import android.app.Application;
import android.content.Context;

import com.example.testloginfb.callbacks.CallbackData;
import com.example.testloginfb.helpers.InventoryHelper;
import com.example.testloginfb.helpers.StaffHelper;
import com.example.testloginfb.helpers.TransactionHelper;
import com.example.testloginfb.models.StoreMaterial;
import com.example.testloginfb.room.entities.StaffEntity;
import com.example.testloginfb.views.ImportHistoryView;
import com.example.testloginfb.views.InventoryView;

import java.util.List;
import java.util.Map;

public class InventoryPresenter {
    private InventoryView mView;
    private Context mContext;
    private InventoryHelper mHelper;
    private StaffHelper mStaffHelper;

    public InventoryPresenter(InventoryView mView, Context mContext, Application application) {
        this.mView = mView;
        this.mContext = mContext;
        this.mHelper = new InventoryHelper(application);
        this.mStaffHelper = new StaffHelper(application);
    }
    public void handleLoadInventory(String storeId,String auth) {
        mHelper.getInventoryFromServer(storeId, new CallbackData<List<StoreMaterial>>() {
            @Override
            public void onSuccess(List<StoreMaterial> storeMaterials) {
                if(! storeMaterials.isEmpty()){
                    mView.loadInventory(storeMaterials);
                    mView.done();
                }
                mView.done();
            }

            @Override
            public void onFail(String message) {
                mView.done();
            }


        },auth);


    }

    public void HandleLoadLocalStaff() {
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



}
