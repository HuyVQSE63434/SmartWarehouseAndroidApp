package com.example.testloginfb.helpers;

import android.content.Context;

import com.example.testloginfb.callbacks.CallbackData;
import com.example.testloginfb.models.Detail;
import com.example.testloginfb.repositories.SupplierService.SupplierRepository;
import com.example.testloginfb.repositories.SupplierService.SupplierRepositoryImpl;
import com.example.testloginfb.repositories.api.ConfigApi;

import java.util.List;

public class SupplierHelper {

    private Context mContext;
    private SupplierRepository mRepository;

    public SupplierHelper(Context mContext) {
        this.mContext = mContext;
    }

    public void getSupplierFromServer(final CallbackData<List<Detail>> callbackData,String auth){
        mRepository = new SupplierRepositoryImpl();
        mRepository.getSuppliers(mContext,callbackData, auth);
    }
}
