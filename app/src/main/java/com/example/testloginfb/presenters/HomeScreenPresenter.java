package com.example.testloginfb.presenters;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.example.testloginfb.callbacks.CallbackData;
import com.example.testloginfb.helpers.DeviceTokenHelper;
import com.example.testloginfb.helpers.StaffHelper;
import com.example.testloginfb.models.Detail;
import com.example.testloginfb.room.entities.StaffEntity;
import com.example.testloginfb.views.HomeScreenView;

public class HomeScreenPresenter {
    private HomeScreenView mHomeScreenView;
    private Context mContext;
    private StaffHelper mStaffHelper;
    private DeviceTokenHelper mDeviceTokenHelper;

    public HomeScreenPresenter(HomeScreenView mHomeScreenView, Context mContext, Application application) {
        this.mHomeScreenView = mHomeScreenView;
        this.mContext = mContext;
        this.mStaffHelper = new StaffHelper(application);
        this.mDeviceTokenHelper = new DeviceTokenHelper(application);
    }

    public void handleLoadStaffFromRoom() {
        mStaffHelper.getLocalStaff(new CallbackData<StaffEntity>() {
            @Override
            public void onSuccess(StaffEntity staffEntity) {
                mHomeScreenView.loadLocalStaff(staffEntity);
            }

            @Override
            public void onFail(String message) {
                mHomeScreenView.showProgressHUD();
                //Toast.makeText(mContext, "Room hổng load đc", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void handleLogOut() {
        mStaffHelper.clearLocalStaff(new CallbackData<String>() {
            @Override
            public void onSuccess(String s) {
                mHomeScreenView.goToMainActivity();
            }

            @Override
            public void onFail(String message) {

            }
        });
    }

    public void removeDeviceToken(String id,String auth){
        Detail detail = new Detail(id,"null");
        mDeviceTokenHelper.removeDeviceTokenFromServer(mContext, detail, new CallbackData<Boolean>() {
            @Override
            public void onSuccess(Boolean aBoolean) {
                handleLogOut();
            }

            @Override
            public void onFail(String message) {
                System.out.println(message);
            }
        },auth);
    }
}
