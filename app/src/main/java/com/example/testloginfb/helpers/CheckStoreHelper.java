package com.example.testloginfb.helpers;

import android.content.Context;

import com.example.testloginfb.callbacks.CallbackData;
import com.example.testloginfb.models.CheckStore;
import com.example.testloginfb.models.RequestAddCheckStore;
import com.example.testloginfb.repositories.CheckStoreService.CheckStoreRepository;
import com.example.testloginfb.repositories.CheckStoreService.CheckStoreRepositoryImpl;
import com.example.testloginfb.repositories.api.ConfigApi;

import java.util.List;
import java.util.Map;

public class CheckStoreHelper {

    private Context mContext;
    private CheckStoreRepository mRepository;

    public CheckStoreHelper(Context mContext) {
        this.mContext = mContext;
    }

    public void getCheckStoreFromServer(String storeId, Map<String, String> params, final CallbackData<List<CheckStore>> callbackData,String auth) {
        mRepository = new CheckStoreRepositoryImpl();
        mRepository.getCheckStore(mContext, storeId, params, callbackData, auth);
    }

    public void addCheckStore(String storeId, RequestAddCheckStore addCheckStore, final CallbackData<Boolean> callbackData,String auth) {
        mRepository = new CheckStoreRepositoryImpl();
        mRepository.addCheckStore(mContext, storeId, addCheckStore, callbackData, auth);
    }

}
