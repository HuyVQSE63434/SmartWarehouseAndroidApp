package com.example.testloginfb.helpers;

import android.content.Context;

import com.example.testloginfb.callbacks.CallbackData;
import com.example.testloginfb.models.RequestAddTransaction;
import com.example.testloginfb.models.Transaction;
import com.example.testloginfb.repositories.api.ConfigApi;
import com.example.testloginfb.repositories.transactionService.TransactionRepository;
import com.example.testloginfb.repositories.transactionService.TransactionRepositoryImpl;

import java.util.List;
import java.util.Map;

public class TransactionHelper {

    private Context mContext;
    private TransactionRepository mRepository;

    public TransactionHelper(Context mContext) {
        this.mContext = mContext;
    }

    public void getTransactionFromServer(String storeId, Map<String, String> params,final CallbackData<List<Transaction>> callbackData,String auth) {
        mRepository = new TransactionRepositoryImpl();
        mRepository.getTransaction(mContext, storeId, params, callbackData, auth);
    }

    public void addTransactionIntoServer(RequestAddTransaction requestAddTransaction, final  CallbackData<Boolean> callbackData,String auth){
        mRepository = new TransactionRepositoryImpl();
        mRepository.addTransaction(mContext,requestAddTransaction,callbackData, auth);
    }

    public void getTransactionEC(String storeId, final  CallbackData<List<Transaction>> callbackData,String auth){
        mRepository = new TransactionRepositoryImpl();
        mRepository.getTransactionEC(mContext,storeId,callbackData,auth);
    }
}
