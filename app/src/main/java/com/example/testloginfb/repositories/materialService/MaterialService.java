package com.example.testloginfb.repositories.materialService;

import com.example.testloginfb.models.Material;
import com.example.testloginfb.models.Transaction;
import com.example.testloginfb.repositories.api.ConfigApi;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface MaterialService {

    @GET(ConfigApi.Api.LOAD_MATERIAL)
    Call<Material> getMaterial(@Header("Authorization") String authToken, @Path("id") String id);

    @GET(ConfigApi.Api.LOAD_ALL_MATERIAL)
    Call<List<Material>> getAllMaterial(@Header("Authorization") String authToken);

    @GET(ConfigApi.Api.LOAD_SUPPLIER)
    Call<List<Material>> getMaterialsFromSupplier(@Path("id") String supplierId, @Header("Authorization") String authToken);
}
