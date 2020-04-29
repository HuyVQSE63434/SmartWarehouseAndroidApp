package com.example.testloginfb.repositories.InventoryDetailService;

import com.example.testloginfb.models.MaterialTransaction;
import com.example.testloginfb.models.StoreMaterial;
import com.example.testloginfb.repositories.api.ConfigApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface InventoryDetailService {

    @GET(ConfigApi.Api.LOAD_INVENTORY_DETAIL)
    Call<List<MaterialTransaction>> getInventoryDetail(@Header("Authorization") String authToken, @Path("storeId") String storeID, @Path("materialId") String materialId);
}
