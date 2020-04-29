package com.example.testloginfb.presenters;

import android.app.Application;
import android.content.Context;

import com.example.testloginfb.callbacks.CallbackData;
import com.example.testloginfb.helpers.InventoryDetailHelper;
import com.example.testloginfb.helpers.InventoryHelper;
import com.example.testloginfb.helpers.StaffHelper;
import com.example.testloginfb.models.MaterialTransaction;
import com.example.testloginfb.room.entities.StaffEntity;
import com.example.testloginfb.views.InventoryDetailView;


import java.util.List;

public class InventoryDetailPresenter {

    private InventoryDetailView mView;
    private Context mContext;
    private InventoryDetailHelper mHelper;
    private StaffHelper mStaffHelper;

    public InventoryDetailPresenter(InventoryDetailView mView, Context mContext, Application application) {
        this.mView = mView;
        this.mContext = mContext;
        this.mHelper = new InventoryDetailHelper(application);
        this.mStaffHelper = new StaffHelper(application);

    }

    public void handleLoadInventoryDetail(String storeId, String materialId,String auth) {
        mHelper.getInventoryDetailFromServer(storeId, materialId, new CallbackData<List<MaterialTransaction>>() {
            @Override
            public void onSuccess(List<MaterialTransaction> MaterialTransaction) {
                if (!MaterialTransaction.isEmpty()) {
                    mView.loadDetailInventory(MaterialTransaction);
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
