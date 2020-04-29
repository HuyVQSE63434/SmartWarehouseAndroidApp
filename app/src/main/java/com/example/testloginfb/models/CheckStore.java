package com.example.testloginfb.models;

import java.util.Date;
import java.util.List;

public class CheckStore {
    private int id;
    private Detail simpleStore;
    private Detail simpleStaff;
    private String time;
    private Date dateTime;

    public List<TransactionMaterialAmount> detail;

    public CheckStore(int id, Detail simpleStore, Detail simpleStaff, String time, Date dateTime, List<TransactionMaterialAmount> detail) {
        this.id = id;
        this.simpleStore = simpleStore;
        this.simpleStaff = simpleStaff;
        this.time = time;
        this.dateTime = dateTime;
        this.detail = detail;
    }

    public CheckStore() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Detail getSimpleStore() {
        return simpleStore;
    }

    public void setSimpleStore(Detail simpleStore) {
        this.simpleStore = simpleStore;
    }

    public Detail getSimpleStaff() {
        return simpleStaff;
    }

    public void setSimpleStaff(Detail simpleStaff) {
        this.simpleStaff = simpleStaff;
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

    public List<TransactionMaterialAmount> getDetail() {
        return detail;
    }

    public void setDetail(List<TransactionMaterialAmount> detail) {
        this.detail = detail;
    }
}
