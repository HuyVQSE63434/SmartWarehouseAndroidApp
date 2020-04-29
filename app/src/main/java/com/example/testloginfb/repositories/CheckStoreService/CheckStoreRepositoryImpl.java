package com.example.testloginfb.repositories.CheckStoreService;

import android.content.Context;

import com.example.testloginfb.callbacks.CallbackData;
import com.example.testloginfb.models.CheckStore;
import com.example.testloginfb.models.RequestAddCheckStore;
import com.example.testloginfb.models.Transaction;
import com.example.testloginfb.repositories.api.ClientApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckStoreRepositoryImpl implements CheckStoreRepository {
    @Override
    public void getCheckStore(Context context, String id, Map<String, String> params, final CallbackData<List<CheckStore>> callbackData, String authToken) {
        ClientApi api = new ClientApi();
        CheckStoreService service = api.getCheckStoreService();
        Call<List<CheckStore>> call = service.getCheckStore("Bearer " + authToken, id, params);
        call.enqueue(new Callback<List<CheckStore>>() {
            @Override
            public void onResponse(Call<List<CheckStore>> call, Response<List<CheckStore>> response) {

                if (!response.isSuccessful()) {
                    callbackData.onFail(response.message());
                    return;
                }
                List<CheckStore> checkStores = response.body();
                for (CheckStore tran : checkStores) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
                    try {
                        tran.setDateTime(dateFormat.parse(tran.getTime()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                callbackData.onSuccess(checkStores);

            }

            @Override
            public void onFailure(Call<List<CheckStore>> call, Throwable t) {
                callbackData.onFail(t.getMessage());
            }
        });
    }

    @Override
    public void addCheckStore(Context mContext, String storeId, RequestAddCheckStore addCheckStore, final CallbackData<Boolean> callbackData, String authToken) {
        ClientApi api = new ClientApi();
        CheckStoreService service = api.getCheckStoreService();
        Call<Boolean> call = service.addCheckStore("Bearer " + authToken, storeId, addCheckStore);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful()){
                    callbackData.onSuccess(true);
                }
                else callbackData.onFail(response.message());
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                callbackData.onFail(t.getMessage());
            }
        });
    }
}
