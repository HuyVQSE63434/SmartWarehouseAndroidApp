package com.example.testloginfb.repositories.CheckStoreService;

import android.content.Context;

import com.example.testloginfb.callbacks.CallbackData;
import com.example.testloginfb.models.CheckStore;
import com.example.testloginfb.models.RequestAddCheckStore;
import com.example.testloginfb.models.Transaction;

import java.util.List;
import java.util.Map;

public interface CheckStoreRepository {

    public void getCheckStore(Context context, String id, Map<String, String> params, CallbackData<List<CheckStore>> callbackData, String authToken);

    public void addCheckStore(Context mContext, String storeId, RequestAddCheckStore addCheckStore, CallbackData<Boolean> callbackData, String authToken);
}
