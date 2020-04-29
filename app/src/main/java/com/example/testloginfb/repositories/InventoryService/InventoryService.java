package com.example.testloginfb.repositories.InventoryService;

import com.example.testloginfb.models.StoreMaterial;
import com.example.testloginfb.models.Transaction;
import com.example.testloginfb.repositories.api.ConfigApi;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface InventoryService {

    @GET(ConfigApi.Api.LOAD_INVENTORY)
    Call<List<StoreMaterial>> getInventory(@Header("Authorization") String authToken, @Path("storeId") String storeId);
}
