package com.example.testloginfb.models;

import java.io.Serializable;
import java.util.List;

public class RequestAddTransaction implements Serializable {
    private long id;
    private String exchangeStoreId;
    private String storeId;
    private String time;
    private String staffId;
    private String transactionTypeId;
    private String statusId;
    private List<TransactionMaterialAmount> detail;

    public RequestAddTransaction(long id, String exchangeStoreId, String storeId,
                                 String time, String staffId, String transactionTypeId,
                                 String statusId, List<TransactionMaterialAmount> detail) {
        this.id = id;
        this.exchangeStoreId = exchangeStoreId;
        this.storeId = storeId;
        this.time = time;
        this.staffId = staffId;
        this.transactionTypeId = transactionTypeId;
        this.statusId = statusId;
        this.detail = detail;
    }

    public RequestAddTransaction() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
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
}
