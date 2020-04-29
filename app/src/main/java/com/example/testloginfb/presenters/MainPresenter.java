package com.example.testloginfb.presenters;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.example.testloginfb.callbacks.CallbackData;
import com.example.testloginfb.helpers.DeviceTokenHelper;
import com.example.testloginfb.helpers.StaffHelper;
import com.example.testloginfb.models.Detail;
import com.example.testloginfb.models.Staff;
import com.example.testloginfb.views.MainView;
import com.google.firebase.iid.FirebaseInstanceId;

public class MainPresenter {
    private MainView mView;
    private Context mContext;
    private StaffHelper mStaffHelper;
    private DeviceTokenHelper mDeviceTokenHelper;

    public MainPresenter(Context mContext, Application application, MainView mView) {
        this.mView = mView;
        this.mContext = mContext;
        this.mStaffHelper = new StaffHelper(application);
        this.mDeviceTokenHelper = new DeviceTokenHelper(application);
    }

    public void handleLoginWithFacebook(Long staffId) {
        mStaffHelper.processLoginByFacebook(staffId, new CallbackData<Staff>() {
            @Override
            public void onSuccess(Staff staff) {
                if(staff!=null){
                    mView.showProgressHUD();
                    addDeviceToken(staff,staff.getAuthToken());
                }
            }

            @Override
            public void onFail(String message) {
                Toast.makeText(mContext, "Không có tài khoản", Toast.LENGTH_LONG).show();
                mView.showToastMessage(message);
                mView.dismissProgressHUD();
            }
        });
    }

    private void addDeviceToken(final Staff staff,String auth) {
        Detail detail = new Detail(staff.getStaffId().toString(), FirebaseInstanceId.getInstance().getToken());
        mDeviceTokenHelper.addDeviceTokentoServer(mContext, detail, new CallbackData<Boolean>() {
            @Override
            public void onSuccess(Boolean aBoolean) {
                mView.goToHomeActivity(staff);
            }

            @Override
            public void onFail(String message) {
                System.out.println(message);
            }
        },auth);
    }

    public void handleLoginWithGoogle(String google) {
        mStaffHelper.progressLoginByGoogle(google, new CallbackData<Staff>() {
            @Override
            public void onSuccess(Staff staff) {
                mView.dismissProgressHUD();
                mView.goToHomeActivity(staff);
            }

            @Override
            public void onFail(String message) {
                mView.dismissProgressHUD();
                mView.showToastMessage(message);
            }
        });
    }
}
