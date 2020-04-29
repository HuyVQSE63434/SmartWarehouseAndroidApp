package com.example.testloginfb.repositories.materialService;

import android.content.Context;

import com.example.testloginfb.callbacks.CallbackData;
import com.example.testloginfb.models.Material;
import com.example.testloginfb.models.Transaction;
import com.example.testloginfb.repositories.api.ClientApi;
import com.example.testloginfb.repositories.transactionService.TransactionRepository;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MaterialRepositoryImpl implements MaterialRepository {

    @Override
    public void getMaterial(Context context, String id, final CallbackData<Material> callbackData, String authToken) {
        ClientApi api = new ClientApi();
        MaterialService service = api.getMaterialService();
        Call<Material> call = service.getMaterial("Bearer " + authToken, id);
        call.enqueue(new Callback<Material>() {
            @Override
            public void onResponse(Call<Material> call, Response<Material> response) {

                if (!response.isSuccessful()) {
                    callbackData.onFail(response.message());
                    return;
                }
                Material material = response.body();
                callbackData.onSuccess(material);
            }

            @Override
            public void onFailure(Call<Material> call, Throwable t) {
                callbackData.onFail(t.getMessage());
            }
        });
    }

    @Override
    public void getAllMaterial(Context context, final CallbackData<List<Material>> callbackData, String authToken) {
        ClientApi api = new ClientApi();
        MaterialService service = api.getMaterialService();
        Call<List<Material>> call = service.getAllMaterial("Bearer " + authToken);
        call.enqueue(new Callback<List<Material>>() {
            @Override
            public void onResponse(Call<List<Material>> call, Response<List<Material>> response) {

                if (!response.isSuccessful()) {
                    callbackData.onFail(response.message());
                    return;
                }
                List<Material> materials = response.body();
                callbackData.onSuccess(materials);
            }

            @Override
            public void onFailure(Call<List<Material>> call, Throwable t) {
                callbackData.onFail(t.getMessage());
            }
        });
    }

    @Override
    public void getMaterialFromSupplier(String supplierId, final CallbackData<List<Material>> callbackData, String authToken) {
        ClientApi api = new ClientApi();
        MaterialService service = api.getMaterialService();
        Call<List<Material>> call = service.getMaterialsFromSupplier(supplierId,"Bearer " + authToken);
        call.enqueue(new Callback<List<Material>>() {
            @Override
            public void onResponse(Call<List<Material>> call, Response<List<Material>> response) {

                if (!response.isSuccessful()) {
                    callbackData.onFail(response.message());
                    return;
                }
                List<Material> materials = response.body();
                callbackData.onSuccess(materials);
            }

            @Override
            public void onFailure(Call<List<Material>> call, Throwable t) {
                callbackData.onFail(t.getMessage());
            }
        });
    }
}
