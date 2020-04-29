package com.example.testloginfb.repositories.StoreService;

import android.content.Context;

import com.example.testloginfb.callbacks.CallbackData;
import com.example.testloginfb.models.Detail;
import com.example.testloginfb.repositories.api.ClientApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoreRepositoryImpl implements StoreRepository{


    @Override
    public void getSimpleStores(Context context, final CallbackData<List<Detail>> callbackData, String authToken) {
        ClientApi api = new ClientApi();
        StoreService service = api.getStoreService();
        Call<List<Detail>> call = service.getsimpleStores("Bearer " + authToken);
        call.enqueue(new Callback<List<Detail>>() {
            @Override
            public void onResponse(Call<List<Detail>> call, Response<List<Detail>> response) {

                if (!response.isSuccessful()) {
                    callbackData.onFail(response.message());
                    return;
                }
                List<Detail> transactions = response.body();
                callbackData.onSuccess(transactions);
            }
            @Override
            public void onFailure(Call<List<Detail>> call, Throwable t) {
                callbackData.onFail(t.getMessage());
            }
        });
    }
}
