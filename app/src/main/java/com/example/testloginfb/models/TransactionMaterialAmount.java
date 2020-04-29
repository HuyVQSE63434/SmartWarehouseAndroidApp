package com.example.testloginfb.models;

import java.io.Serializable;

public class TransactionMaterialAmount implements Serializable {
    private Detail material;
    private long materialAmount;
    private String unit;

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Detail getMaterial() {
        return material;
    }

    public void setMaterial(Detail material) {
        this.material = material;
    }

    public long getMaterialAmount() {
        return materialAmount;
    }

    public void setMaterialAmount(long materialAmount) {
        this.materialAmount = materialAmount;
    }

    public TransactionMaterialAmount(Detail material, long materialAmount,String unit) {
        this.material = material;
        this.materialAmount = materialAmount;
        this.unit = unit;
    }

    public TransactionMaterialAmount() {
    }
}
