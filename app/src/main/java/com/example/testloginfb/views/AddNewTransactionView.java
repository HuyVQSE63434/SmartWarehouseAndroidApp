package com.example.testloginfb.views;

import com.example.testloginfb.models.Detail;
import com.example.testloginfb.models.Material;
import com.example.testloginfb.models.StoreMaterial;
import com.example.testloginfb.models.Transaction;

import java.util.List;

public interface AddNewTransactionView extends BaseView {
    void updateScanMaterial(Material material);

    void showMessage(String message);

    void doneAddTransaction();

    void done();

    void returnAddResult(boolean aBoolean);

    void loadMaterials(List<Material> materials);

    void returnTransactions(List<Transaction> transactions);

    void returnStoreMaterial(List<StoreMaterial> storeMaterials);

    void addExchangeStore(List<Detail> details);

    void addSuppliers(List<Detail> details);
}
