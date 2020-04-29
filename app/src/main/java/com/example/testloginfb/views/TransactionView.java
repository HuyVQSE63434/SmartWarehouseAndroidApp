package com.example.testloginfb.views;

import com.example.testloginfb.models.CheckStore;
import com.example.testloginfb.models.Transaction;
import com.example.testloginfb.room.entities.StaffEntity;

import java.util.List;

public interface TransactionView extends BaseView {

    void loadImportDetail(List<Transaction> transactions);

    void showMessage(String message);

    void done();

    void loadLocalStaff(StaffEntity staffEntity);


    void loadCheckStore(List<CheckStore> checkStores);
}
