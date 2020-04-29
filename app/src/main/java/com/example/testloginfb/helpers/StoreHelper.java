package com.example.testloginfb.helpers;

import android.content.Context;

import com.example.testloginfb.callbacks.CallbackData;
import com.example.testloginfb.models.Detail;
import com.example.testloginfb.repositories.StoreService.StoreRepository;
import com.example.testloginfb.repositories.StoreService.StoreRepositoryImpl;
import com.example.testloginfb.repositories.api.ConfigApi;

import java.util.List;
import java.util.Map;

public class StoreHelper {

    private Context mContext;
    private StoreRepository mRepository;

    public StoreHelper(Context context) {
        this.mContext = context;
    }

    public void getTransactionFromServer(final CallbackData<List<Detail>> callbackData,String auth) {
        mRepository = new StoreRepositoryImpl();
        mRepository.getSimpleStores(mContext,callbackData, auth);
    }
}
