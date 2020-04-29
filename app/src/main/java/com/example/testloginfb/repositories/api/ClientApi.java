package com.example.testloginfb.repositories.api;

import com.example.testloginfb.repositories.DeviceTokenService.DeviceTokenService;
import com.example.testloginfb.repositories.InventoryDetailService.InventoryDetailService;
import com.example.testloginfb.repositories.InventoryService.InventoryService;
import com.example.testloginfb.repositories.CheckStoreService.CheckStoreService;
import com.example.testloginfb.repositories.StoreService.StoreService;
import com.example.testloginfb.repositories.SupplierService.SupplierService;
import com.example.testloginfb.repositories.authService.AuthService;
import com.example.testloginfb.repositories.materialService.MaterialService;
import com.example.testloginfb.repositories.transactionService.TransactionService;

public class ClientApi extends BaseApi {
    public AuthService getAuthService() {
        return this.getService(AuthService.class, ConfigApi.BASE_URL);
    }

    public TransactionService getTransactionService(){
        return this.getService(TransactionService.class,ConfigApi.BASE_URL);
    }

    public MaterialService getMaterialService(){
        return this.getService(MaterialService.class, ConfigApi.BASE_URL);
    }


    public InventoryService getInventoryService() {
        return this.getService(InventoryService.class, ConfigApi.BASE_URL);
    }
    public InventoryDetailService getInventoryDetailService() {
        return this.getService(InventoryDetailService.class, ConfigApi.BASE_URL);
    }
    public CheckStoreService getCheckStoreService(){
        return this.getService(CheckStoreService.class, ConfigApi.BASE_URL);
    }

    public DeviceTokenService getDeviceTokenService(){
        return this.getService(DeviceTokenService.class,ConfigApi.BASE_URL);
    }

    public StoreService getStoreService(){
        return  this.getService(StoreService.class, ConfigApi.BASE_URL);
    }

    public SupplierService getSupplierService() {
        return  this.getService(SupplierService.class, ConfigApi.BASE_URL);
    }
}
