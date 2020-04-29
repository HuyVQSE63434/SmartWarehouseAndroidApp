package com.example.testloginfb.repositories.StoreService;

import com.example.testloginfb.models.Detail;
import com.example.testloginfb.repositories.api.ConfigApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface StoreService {
    @GET(ConfigApi.Api.GET_SIMPLE_STORE)
    Call<List<Detail>> getsimpleStores(@Header("Authorization") String authToken);
}
