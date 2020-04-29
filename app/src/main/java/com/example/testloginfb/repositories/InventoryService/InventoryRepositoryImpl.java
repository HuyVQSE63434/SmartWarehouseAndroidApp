package com.example.testloginfb.repositories.InventoryService;

import android.content.Context;

import com.example.testloginfb.callbacks.CallbackData;
import com.example.testloginfb.models.StoreMaterial;
import com.example.testloginfb.models.Transaction;
import com.example.testloginfb.repositories.api.ClientApi;
import com.example.testloginfb.repositories.transactionService.TransactionRepository;
import com.example.testloginfb.repositories.transactionService.TransactionService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InventoryRepositoryImpl implements InventoryRepository {


    @Override
    public void getInventory(Context context, String storeId, final CallbackData<List<StoreMaterial>> callbackData, String authToken) {
        ClientApi api = new ClientApi();
        InventoryService service = api.getInventoryService();
        Call<List<StoreMaterial>> call = service.getInventory("Bearer " + authToken, storeId);
        String auth = call.request().header("Authorization");
        call.enqueue(new Callback<List<StoreMaterial>>() {
            @Override
            public void onResponse(Call<List<StoreMaterial>> call, Response<List<StoreMaterial>> response) {

                if (!response.isSuccessful()) {
                    callbackData.onFail(response.message());
                    return;
                }
                List<StoreMaterial> storeMaterials = response.body();

                callbackData.onSuccess(storeMaterials);

            }

            @Override
            public void onFailure(Call<List<StoreMaterial>> call, Throwable t) {
                callbackData.onFail(t.getMessage());
            }
        });
    }


}
