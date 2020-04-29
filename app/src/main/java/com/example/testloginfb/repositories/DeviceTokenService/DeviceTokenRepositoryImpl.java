package com.example.testloginfb.repositories.DeviceTokenService;

import android.content.Context;

import com.example.testloginfb.callbacks.CallbackData;
import com.example.testloginfb.models.Detail;
import com.example.testloginfb.repositories.api.ClientApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeviceTokenRepositoryImpl implements DeviceTokenRepository {
    @Override
    public void addDeviceToken(Context context, Detail dto, final CallbackData<Boolean> callbackData, String authToken) {
        ClientApi api = new ClientApi();
        DeviceTokenService service = api.getDeviceTokenService();
        Call<Boolean> call = service.addDeviceToken("Bearer " + authToken,dto);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(!response.isSuccessful()){
                    callbackData.onFail(response.message());
                }
                callbackData.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                callbackData.onFail(t.getMessage());
            }
        });
    }

    @Override
    public void removeDeviceToken(Context context, Detail dto, final CallbackData<Boolean> callbackData, String authToken) {
        ClientApi api = new ClientApi();
        DeviceTokenService service = api.getDeviceTokenService();
        Call<Boolean> call = service.removeDeviceToken("Bearer " + authToken,dto);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(!response.isSuccessful()){
                    callbackData.onFail(response.message());
                }
                callbackData.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                callbackData.onFail(t.getMessage());
            }
        });
    }
}
