package com.example.testloginfb.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class MaterialTransaction implements Serializable {

    private Detail exahangeStore;
    private Detail material;
    private String time;
    private Date dateTime;
    private Detail staff;
    private Detail transactionType;
    private Detail status;
    @SerializedName("materialAmount")
    private long inventoryAmmount;
    private String unit;


    public MaterialTransaction(Detail exchangeStore, Detail material, String time, Date dateTime,
                               Detail staff, Detail transactionType, Detail status,String unit) {
        this.exahangeStore = exchangeStore;
        this.material = material;
        this.time = time;
        this.dateTime = dateTime;
        this.staff = staff;
        this.transactionType = transactionType;
        this.status = status;
        this.unit = unit;
    }

    public MaterialTransaction() {
    }

    public Detail getExchangeStore() {
        return exahangeStore;
    }

    public void setExchangeStore(Detail exchangeStore) {
        this.exahangeStore = exchangeStore;
    }

    public Detail getMaterial() {
        return material;
    }

    public void setMaterial(Detail material) {
        this.material = material;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public Detail getStaff() {
        return staff;
    }

    public void setStaff(Detail staff) {
        this.staff = staff;
    }

    public Detail getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(Detail transactionType) {
        this.transactionType = transactionType;
    }

    public Detail getStatus() {
        return status;
    }

    public void setStatus(Detail status) {
        this.status = status;
    }

    public long getInventoryAmmount() {
        return inventoryAmmount;
    }

    public void setInventoryAmmount(long inventoryAmmount) {
        this.inventoryAmmount = inventoryAmmount;
    }

    public Detail getExahangeStore() {
        return exahangeStore;
    }

    public void setExahangeStore(Detail exahangeStore) {
        this.exahangeStore = exahangeStore;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
