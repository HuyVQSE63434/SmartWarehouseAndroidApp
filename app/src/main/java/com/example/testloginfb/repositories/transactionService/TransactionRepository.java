package com.example.testloginfb.repositories.transactionService;

import android.content.Context;

import com.example.testloginfb.callbacks.CallbackData;
import com.example.testloginfb.models.RequestAddTransaction;
import com.example.testloginfb.models.Transaction;
import com.example.testloginfb.repositories.api.ConfigApi;

import java.util.List;
import java.util.Map;

public interface TransactionRepository {

    public void getTransaction(Context context, String storeId,Map<String, String> params, CallbackData<List<Transaction>> callbackData, String authToken);
    public void addTransaction(Context context, RequestAddTransaction requestAddTransaction, CallbackData<Boolean> callbackData,String authToken);

    void getTransactionEC(Context mContext, String storeId, CallbackData<List<Transaction>> callbackData, String authToken);
}
//dsaas