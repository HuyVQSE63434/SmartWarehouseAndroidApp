package com.example.testloginfb.models;

public class Material {
    public int id;
    public String name;
    public Detail supplier;
    public Long barcode;
    public Detail type;
    public String mainUnit;
    public String changeUnit;
    public Detail status;


    public Material(int id, String name, Detail supplier, Long barcode, Detail type, String main_unit, String change_unit, Detail status) {
        this.id = id;
        this.name = name;
        this.supplier = supplier;
        this.barcode = barcode;
        this.type = type;
        this.mainUnit = main_unit;
        this.changeUnit = change_unit;
        this.status = status;
    }

    public Material() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Detail getSupplier() {
        return supplier;
    }

    public void setSupplier(Detail supplier) {
        this.supplier = supplier;
    }

    public Long getBarcode() {
        return barcode;
    }

    public void setBarcode(Long barcode) {
        this.barcode = barcode;
    }

    public Detail getType() {
        return type;
    }

    public void setType(Detail type) {
        this.type = type;
    }

    public String getMain_unit() {
        return mainUnit;
    }

    public void setMain_unit(String main_unit) {
        this.mainUnit = main_unit;
    }

    public String getChange_unit() {
        return changeUnit;
    }

    public void setChange_unit(String change_unit) {
        this.changeUnit = change_unit;
    }

    public Detail getStatus() {
        return status;
    }

    public void setStatus(Detail status) {
        this.status = status;
    }
}
