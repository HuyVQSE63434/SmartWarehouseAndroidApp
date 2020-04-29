package com.example.testloginfb.views;

import com.example.testloginfb.models.StoreMaterial;
import com.example.testloginfb.room.entities.StaffEntity;

import java.util.List;

public interface InventoryView extends BaseView {
    void loadInventory(List<StoreMaterial> storeMaterials);
    void done();
    void showMessage(String message);

    void loadLocalStaff(StaffEntity staffEntity);
}
