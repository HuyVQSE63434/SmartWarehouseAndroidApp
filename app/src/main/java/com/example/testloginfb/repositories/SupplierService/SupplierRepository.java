package com.example.testloginfb.repositories.SupplierService;

import android.content.Context;

import com.example.testloginfb.callbacks.CallbackData;
import com.example.testloginfb.models.Detail;

import java.util.List;

public interface SupplierRepository {

    void getSuppliers(Context mContext, CallbackData<List<Detail>> callbackData, String authToken);

}
