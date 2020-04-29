package com.example.testloginfb.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class StoreMaterial implements Serializable {
    @SerializedName("simpleStore")
    private Detail store;
    @SerializedName("simpleMaterial")
    private Detail materialName;
    @SerializedName("amount")
    private long inventoryAmmount;

    private String unit;

    public StoreMaterial(Detail store, Detail materialName, long inventoryAmmount,String unit) {
        this.store = store;
        this.materialName = materialName;
        this.inventoryAmmount = inventoryAmmount;
        this.unit = unit;
    }

    public Detail getStore() {
        return store;
    }

    public void setStore(Detail store) {
        this.store = store;
    }

    public StoreMaterial() {

    }


    public Detail getMaterialName() {
        return materialName;
    }

    public void setMaterialName(Detail materialName) {
        this.materialName = materialName;
    }

    public long getInventoryAmmount() {
        return inventoryAmmount;
    }

    public void setInventoryAmmount(long inventoryAmmount) {
        this.inventoryAmmount = inventoryAmmount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
