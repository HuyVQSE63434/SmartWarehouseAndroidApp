package com.example.testloginfb.repositories.DeviceTokenService;

import android.content.Context;

import com.example.testloginfb.callbacks.CallbackData;
import com.example.testloginfb.models.Detail;

import java.util.Map;

public interface DeviceTokenRepository {

    public void addDeviceToken(Context context, Detail dto, CallbackData<Boolean> callbackData, String authToken);

    public void removeDeviceToken(Context context, Detail dto, CallbackData<Boolean> callbackData, String authToken);

}
