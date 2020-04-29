package com.example.testloginfb.helpers;

import android.content.Context;

import com.example.testloginfb.callbacks.CallbackData;
import com.example.testloginfb.models.Material;
import com.example.testloginfb.repositories.api.ConfigApi;
import com.example.testloginfb.repositories.materialService.MaterialRepository;
import com.example.testloginfb.repositories.materialService.MaterialRepositoryImpl;

import java.util.List;

public class Materialhelper {
    private Context mContext;
    private MaterialRepository mRepository;

    public Materialhelper(Context context){
        this.mContext = context;
    }

    public void getMaterialFromServerByBarCode(String id, final CallbackData<Material> callbackData,String auth){
        mRepository = new MaterialRepositoryImpl();
        mRepository.getMaterial(mContext,id,callbackData, auth);
    }

    public void getMaterialsFromServer(final CallbackData<List<Material>> callbackData,String auth){
        mRepository = new MaterialRepositoryImpl();
        mRepository.getAllMaterial(mContext,callbackData, auth);
    }

    public void getMaterialFromServerFromSupplier(String supplierId, CallbackData<List<Material>> listCallbackData,String auth) {
        mRepository = new MaterialRepositoryImpl();
        mRepository.getMaterialFromSupplier(supplierId, listCallbackData,auth);
    }
}
