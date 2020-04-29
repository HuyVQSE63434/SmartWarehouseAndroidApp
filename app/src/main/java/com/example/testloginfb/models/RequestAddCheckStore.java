package com.example.testloginfb.models;

import java.util.List;

public class RequestAddCheckStore {
    private int id;
    private String storeId;
    private String staffId;
    private long time;

    private List<TransactionMaterialAmount> detail;

    public RequestAddCheckStore() {
    }

    public RequestAddCheckStore(int id, String storeId, String staffId, long time, List<TransactionMaterialAmount> detail) {
        this.id = id;
        this.storeId = storeId;
        this.staffId = staffId;
        this.time = time;
        this.detail = detail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public List<TransactionMaterialAmount> getDetail() {
        return detail;
    }

    public void setDetail(List<TransactionMaterialAmount> detail) {
        this.detail = detail;
    }
}
