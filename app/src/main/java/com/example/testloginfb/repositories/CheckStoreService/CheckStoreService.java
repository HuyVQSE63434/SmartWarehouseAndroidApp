package com.example.testloginfb.repositories.CheckStoreService;

import com.example.testloginfb.models.CheckStore;
import com.example.testloginfb.models.RequestAddCheckStore;
import com.example.testloginfb.repositories.api.ConfigApi;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface CheckStoreService {

    @GET(ConfigApi.Api.GET_CHECKSTORE)
    Call<List<CheckStore>> getCheckStore(@Header("Authorization") String authToken, @Path("id") String id,@QueryMap Map<String, String> params);

    @POST(ConfigApi.Api.GET_CHECKSTORE)
    Call<Boolean> addCheckStore(@Header("Authorization") String authToken, @Path("id") String id,@Body RequestAddCheckStore addCheckStore);
}
