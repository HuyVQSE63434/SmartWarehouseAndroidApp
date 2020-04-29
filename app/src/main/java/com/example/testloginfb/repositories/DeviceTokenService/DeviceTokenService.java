package com.example.testloginfb.repositories.DeviceTokenService;

import com.example.testloginfb.models.Detail;
import com.example.testloginfb.models.Material;
import com.example.testloginfb.repositories.api.ConfigApi;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface DeviceTokenService {
    @POST(ConfigApi.Api.DEVICE_TOKEN_LOGIN)
    Call<Boolean> addDeviceToken(@Header("Authorization") String authToken, @Body Detail dto);

    @POST(ConfigApi.Api.DEVICE_TOKEN_LOGOUT)
    Call<Boolean> removeDeviceToken(@Header("Authorization") String authToken, @Body Detail dto);

}
