package com.example.testloginfb.repositories.materialService;

import android.content.Context;

import com.example.testloginfb.callbacks.CallbackData;
import com.example.testloginfb.models.Material;

import java.util.List;

public interface MaterialRepository {

    public void getMaterial(Context context, String id, CallbackData<Material> callbackData, String authToken);
    public void getAllMaterial(Context context, CallbackData<List<Material>> callbackData, String authToken);

    void getMaterialFromSupplier(String supplierId, CallbackData<List<Material>> listCallbackData, String authToken);
}
