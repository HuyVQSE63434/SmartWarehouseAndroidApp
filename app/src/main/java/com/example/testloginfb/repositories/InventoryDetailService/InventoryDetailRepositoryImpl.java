package com.example.testloginfb.repositories.InventoryDetailService;

import android.content.Context;

import com.example.testloginfb.callbacks.CallbackData;
import com.example.testloginfb.models.MaterialTransaction;
import com.example.testloginfb.models.StoreMaterial;
import com.example.testloginfb.models.Transaction;
import com.example.testloginfb.repositories.InventoryService.InventoryRepository;
import com.example.testloginfb.repositories.InventoryDetailService.InventoryDetailService;
import com.example.testloginfb.repositories.api.ClientApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InventoryDetailRepositoryImpl implements InventoryDetailRepository {


    @Override
    public void getInventoryDetail(Context context, String storeId,String materialId, final CallbackData<List<MaterialTransaction>> callbackData, String authToken) {
        ClientApi api = new ClientApi();
        InventoryDetailService service = api.getInventoryDetailService();
        Call<List<MaterialTransaction>> call = service.getInventoryDetail("Bearer " + authToken, storeId,materialId);
        String auth = call.request().header("Authorization");
        call.enqueue(new Callback<List<MaterialTransaction>>() {
            @Override
            public void onResponse(Call<List<MaterialTransaction>> call, Response<List<MaterialTransaction>> response) {

                if (!response.isSuccessful()) {
                    callbackData.onFail(response.message());
                    return;
                }
                List<MaterialTransaction> storeMaterialsDetail = response.body();
                for (MaterialTransaction tran : storeMaterialsDetail) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
                    try {
                        tran.setDateTime(dateFormat.parse(tran.getTime()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                callbackData.onSuccess(storeMaterialsDetail);

            }

            @Override
            public void onFailure(Call<List<MaterialTransaction>> call, Throwable t) {
                callbackData.onFail(t.getMessage());
            }
        });
    }


}
