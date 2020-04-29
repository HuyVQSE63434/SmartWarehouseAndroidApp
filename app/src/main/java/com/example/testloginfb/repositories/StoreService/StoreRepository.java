package com.example.testloginfb.repositories.StoreService;

import android.content.Context;

import com.example.testloginfb.callbacks.CallbackData;
import com.example.testloginfb.models.Detail;

import java.util.List;

public interface StoreRepository {
    public void getSimpleStores(Context context, CallbackData<List<Detail>> callbackData, String authToken);
}
