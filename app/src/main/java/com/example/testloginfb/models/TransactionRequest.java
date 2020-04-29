package com.example.testloginfb.models;

import java.util.List;

public class TransactionRequest {

    public int id;
    public String exchangeStoreId;
    public String storeId;
    public double time;
    public int staffId;
    public String transactionTypeId;
    public String statusId;
    public List<TransactionMaterialAmount> detail;

    public TransactionRequest(int id, String exchangeStoreId, String storeId, double time, int staffId, String transactionTypeId, String statusId, List<TransactionMaterialAmount> detail) {
        this.id = id;
        this.exchangeStoreId = exchangeStoreId;
        this.storeId = storeId;
        this.time = time;
        this.staffId = staffId;
        this.transactionTypeId = transactionTypeId;
        this.statusId = statusId;
        this.detail = detail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExchangeStoreId() {
        return exchangeStoreId;
    }

    public void setExchangeStoreId(String exchangeStoreId) {
        this.exchangeStoreId = exchangeStoreId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public String getTransactionTypeId() {
        return transactionTypeId;
    }

    public void setTransactionTypeId(String transactionTypeId) {
        this.transactionTypeId = transactionTypeId;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public List<TransactionMaterialAmount> getDetail() {
        return detail;
    }

    public void setDetail(List<TransactionMaterialAmount> detail) {
        this.detail = detail;
    }

    public TransactionRequest() {


    }
}
