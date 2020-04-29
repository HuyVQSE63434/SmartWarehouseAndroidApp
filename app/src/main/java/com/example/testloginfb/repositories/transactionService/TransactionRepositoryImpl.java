package com.example.testloginfb.repositories.transactionService;

import android.content.Context;
import android.widget.Toast;

import com.example.testloginfb.callbacks.CallbackData;
import com.example.testloginfb.models.RequestAddTransaction;
import com.example.testloginfb.models.Transaction;
import com.example.testloginfb.repositories.api.ClientApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionRepositoryImpl implements TransactionRepository {
    @Override
    public void getTransaction(Context context,String storeId, Map<String, String> params, final CallbackData<List<Transaction>> callbackData, String authToken) {
        ClientApi api = new ClientApi();
        TransactionService service = api.getTransactionService();
        Call<List<Transaction>> call = service.getTransaction("Bearer " + authToken, storeId, params);
        call.enqueue(new Callback<List<Transaction>>() {
            @Override
            public void onResponse(Call<List<Transaction>> call, Response<List<Transaction>> response) {

                if (!response.isSuccessful()) {
                    callbackData.onFail(response.message());
                    return;
                }
                List<Transaction> transactions = response.body();
                for (Transaction tran : transactions) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
                    try {
                        tran.setDateTime(dateFormat.parse(tran.getTime()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                callbackData.onSuccess(transactions);

            }

            @Override
            public void onFailure(Call<List<Transaction>> call, Throwable t) {
                callbackData.onFail(t.getMessage());
            }
        });
    }

    @Override
    public void addTransaction(Context context, RequestAddTransaction requestAddTransaction, final CallbackData<Boolean> callbackData, String authToken) {
        ClientApi api = new ClientApi();
        TransactionService service = api.getTransactionService();
        Call<Boolean> call = service.addTransaction("Bearer " + authToken, requestAddTransaction);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                if (!response.isSuccessful()) {
                    callbackData.onFail(response.message());
                    return;
                }
                Boolean result = response.body();

                callbackData.onSuccess(result);

            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                callbackData.onFail(t.getMessage());
            }
        });
    }

    @Override
    public void getTransactionEC(Context mContext, String storeId, final CallbackData<List<Transaction>> callbackData, String authToken) {
        ClientApi api = new ClientApi();
        TransactionService service = api.getTransactionService();
        Call<List<Transaction>> call = service.getTransactionEc("Bearer " + authToken, storeId);
        call.enqueue(new Callback<List<Transaction>>() {
            @Override
            public void onResponse(Call<List<Transaction>> call, Response<List<Transaction>> response) {

                if (!response.isSuccessful()) {
                    callbackData.onFail(response.message());
                    return;
                }
                List<Transaction> results = response.body();

                callbackData.onSuccess(results);

            }

            @Override
            public void onFailure(Call<List<Transaction>> call, Throwable t) {
                callbackData.onFail(t.getMessage());
            }
        });
    }

}
