package com.example.testloginfb.helpers;

import android.content.Context;

import com.example.testloginfb.callbacks.CallbackData;
import com.example.testloginfb.models.MaterialTransaction;
import com.example.testloginfb.models.StoreMaterial;
import com.example.testloginfb.repositories.InventoryDetailService.InventoryDetailRepository;
import com.example.testloginfb.repositories.InventoryDetailService.InventoryDetailRepositoryImpl;
import com.example.testloginfb.repositories.api.ConfigApi;
import java.util.List;
public class InventoryDetailHelper {
    private Context mContext;
    private InventoryDetailRepository miInventoryRepository;

    public InventoryDetailHelper(Context mContext) {
        this.mContext = mContext;
    }

    public void getInventoryDetailFromServer(String storeId,String materialId, final CallbackData<List<MaterialTransaction>> callbackData,String auth) {
        miInventoryRepository = new InventoryDetailRepositoryImpl();
        miInventoryRepository.getInventoryDetail(mContext, storeId,materialId, callbackData, auth);

    }
}
