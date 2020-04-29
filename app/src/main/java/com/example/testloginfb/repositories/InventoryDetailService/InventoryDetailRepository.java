package com.example.testloginfb.repositories.InventoryDetailService;

import android.content.Context;

import com.example.testloginfb.callbacks.CallbackData;
import com.example.testloginfb.models.MaterialTransaction;
import com.example.testloginfb.models.StoreMaterial;

import java.util.List;

public interface InventoryDetailRepository {
    public void getInventoryDetail(Context context, String storeId, String materialId, CallbackData<List<MaterialTransaction>> callbackData, String authToken);
}
