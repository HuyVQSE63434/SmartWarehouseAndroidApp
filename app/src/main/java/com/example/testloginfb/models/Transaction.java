package com.example.testloginfb.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Transaction implements Serializable {

    private int id ;
    private Detail exchangeStore;
    private Detail store;
    private String time;
    private Date dateTime;
    private Detail staff;
    private Detail transactionType;
    private Detail status;
    private List<TransactionMaterialAmount> detail;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Detail getExchangeStore() {
        return exchangeStore;
    }

    public void setExchangeStore(Detail exchangeStore) {
        this.exchangeStore = exchangeStore;
    }

    public Detail getStore() {
        return store;
    }

    public void setStore(Detail store) {
        this.store = store;
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

    public List<TransactionMaterialAmount> getDetail() {
        return detail;
    }

    public void setDetail(List<TransactionMaterialAmount> detail) {
        this.detail = detail;
    }

    public Transaction(int id, Detail exchangeStore, Detail store, String time,
                       Detail staff, Detail transactionType, Detail status, List<TransactionMaterialAmount> detail) {
        this.id = id;
        this.exchangeStore = exchangeStore;
        this.store = store;
        this.setTime(time);
        this.staff = staff;
        this.transactionType = transactionType;
        this.status = status;
        this.detail = detail;
    }

    public Transaction() {
    }
}
