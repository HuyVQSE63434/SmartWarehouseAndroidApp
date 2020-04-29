package com.example.testloginfb.repositories.transactionService;

import com.example.testloginfb.models.RequestAddTransaction;
import com.example.testloginfb.models.Transaction;
import com.example.testloginfb.repositories.api.ConfigApi;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface TransactionService {

    @GET(ConfigApi.Api.LOAD_TRANSACTION)
    Call<List<Transaction>> getTransaction(@Header("Authorization") String authToken, @Path("storeId") String storeId, @QueryMap Map<String, String> params);

    @POST(ConfigApi.Api.ADD_TRANSACTION)
    Call<Boolean> addTransaction(@Header("Authorization") String authToken, @Body RequestAddTransaction requestAddTransaction);

    @GET(ConfigApi.Api.LOAD_TRANSACTION_EC)
    Call<List<Transaction>> getTransactionEc(@Header("Authorization") String authToken,@Path("storeId") String storeId);
}
