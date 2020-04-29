package com.example.testloginfb.repositories.InventoryService;

import android.content.Context;

import com.example.testloginfb.callbacks.CallbackData;
import com.example.testloginfb.models.StoreMaterial;
import com.example.testloginfb.models.Transaction;

import java.util.List;
import java.util.Map;

public interface InventoryRepository {
    public void getInventory(Context context, String storeId, CallbackData<List<StoreMaterial>> callbackData, String authToken);
}
