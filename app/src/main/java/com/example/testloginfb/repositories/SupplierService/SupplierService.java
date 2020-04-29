package com.example.testloginfb.repositories.SupplierService;

import com.example.testloginfb.models.Detail;
import com.example.testloginfb.repositories.api.ConfigApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface SupplierService {
    @GET(ConfigApi.Api.LOAD_SUPPLIERS)
    Call<List<Detail>> getSupplier(@Header("Authorization") String authToken);
}
