package com.example.testloginfb.helpers;

import android.content.Context;

import com.example.testloginfb.callbacks.CallbackData;
import com.example.testloginfb.models.Detail;
import com.example.testloginfb.repositories.DeviceTokenService.DeviceTokenRepository;
import com.example.testloginfb.repositories.DeviceTokenService.DeviceTokenRepositoryImpl;
import com.example.testloginfb.repositories.api.ConfigApi;

public class DeviceTokenHelper {
    private Context mContext;
    private DeviceTokenRepository mDeviceTokenRepository;

    public DeviceTokenHelper(Context context){
        mContext = context;
    }

    public void addDeviceTokentoServer(Context context, Detail dto, CallbackData<Boolean> callbackData,String auth){
        mDeviceTokenRepository = new DeviceTokenRepositoryImpl();
        mDeviceTokenRepository.addDeviceToken(mContext,dto,callbackData, auth);
    }

    public void removeDeviceTokenFromServer(Context context, Detail dto, CallbackData<Boolean> callbackData,String auth){
        mDeviceTokenRepository = new DeviceTokenRepositoryImpl();
        mDeviceTokenRepository.removeDeviceToken(mContext,dto,callbackData, auth);
    }
}
