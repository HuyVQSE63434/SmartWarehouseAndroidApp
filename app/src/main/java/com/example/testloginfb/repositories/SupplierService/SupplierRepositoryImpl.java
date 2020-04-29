package com.example.testloginfb.repositories.SupplierService;

import android.content.Context;

import com.example.testloginfb.callbacks.CallbackData;
import com.example.testloginfb.models.Detail;
import com.example.testloginfb.repositories.api.ClientApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SupplierRepositoryImpl implements SupplierRepository {
    @Override
    public void getSuppliers(Context mContext, final CallbackData<List<Detail>> callbackData, String authToken) {
        ClientApi api = new ClientApi();
        SupplierService service = api.getSupplierService();
        Call<List<Detail>> call = service.getSupplier("Bearer " + authToken);
        call.enqueue(new Callback<List<Detail>>() {
            @Override
            public void onResponse(Call<List<Detail>> call, Response<List<Detail>> response) {

                if (!response.isSuccessful()) {
                    callbackData.onFail(response.message());
                    return;
                }
                List<Detail> results = response.body();

                callbackData.onSuccess(results);

            }

            @Override
            public void onFailure(Call<List<Detail>> call, Throwable t) {
                callbackData.onFail(t.getMessage());
            }
        });
    }
}
