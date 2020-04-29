package com.example.testloginfb.helpers;

import android.content.Context;

import com.example.testloginfb.callbacks.CallbackData;
import com.example.testloginfb.models.Material;
import com.example.testloginfb.models.StoreMaterial;
import com.example.testloginfb.models.Transaction;
import com.example.testloginfb.repositories.InventoryService.InventoryRepository;
import com.example.testloginfb.repositories.InventoryService.InventoryRepositoryImpl;
import com.example.testloginfb.repositories.InventoryService.InventoryService;
import com.example.testloginfb.repositories.api.ConfigApi;
import com.example.testloginfb.repositories.transactionService.TransactionRepositoryImpl;

import java.util.List;
import java.util.Map;

public class InventoryHelper {
    private Context mContext;
    private InventoryRepository miInventoryRepository;

    public InventoryHelper(Context mContext) {
        this.mContext = mContext;
    }

    public void getInventoryFromServer(String storeId, final CallbackData<List<StoreMaterial>> callbackData,String auth) {
        miInventoryRepository = new InventoryRepositoryImpl();
        miInventoryRepository.getInventory(mContext, storeId, callbackData,auth);

    }
}
