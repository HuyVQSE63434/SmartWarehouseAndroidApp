package com.example.testloginfb.views;

import com.example.testloginfb.models.Material;
import com.example.testloginfb.models.MaterialTransaction;
import com.example.testloginfb.models.StoreMaterial;
import com.example.testloginfb.room.entities.StaffEntity;

import java.util.List;

public interface InventoryDetailView extends BaseView {
    void loadDetailInventory(List<MaterialTransaction> MaterialTransaction);
    void done();
    void showMessage(String message);

    void loadLocalStaff(StaffEntity staffEntity);
}
