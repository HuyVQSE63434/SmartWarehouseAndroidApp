package com.example.testloginfb.presenters;

import android.app.Application;
import android.content.Context;

import com.example.testloginfb.callbacks.CallbackData;
import com.example.testloginfb.helpers.CheckStoreHelper;
import com.example.testloginfb.helpers.InventoryHelper;
import com.example.testloginfb.helpers.Materialhelper;
import com.example.testloginfb.helpers.StoreHelper;
import com.example.testloginfb.helpers.SupplierHelper;
import com.example.testloginfb.helpers.TransactionHelper;
import com.example.testloginfb.models.Detail;
import com.example.testloginfb.models.Material;
import com.example.testloginfb.models.RequestAddCheckStore;
import com.example.testloginfb.models.RequestAddTransaction;
import com.example.testloginfb.models.StoreMaterial;
import com.example.testloginfb.models.Transaction;
import com.example.testloginfb.views.AddNewTransactionView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddNewTransactionPresenter {

    private AddNewTransactionView mView;
    private Context mContext;
    private Materialhelper mMaterialHelper;
    private TransactionHelper mTransactionHelper;
    private InventoryHelper mInventoryHelper;
    private CheckStoreHelper mCheckStoreHelper;
    private StoreHelper mStoreHelper;
    private SupplierHelper mSupplierHelper;


    public AddNewTransactionPresenter(AddNewTransactionView mView, Context mContext, Application application) {
        this.mView = mView;
        this.mContext = mContext;
        this.mMaterialHelper = new Materialhelper(application);
        this.mTransactionHelper = new TransactionHelper(application);
        this.mInventoryHelper = new InventoryHelper(application);
        this.mCheckStoreHelper = new CheckStoreHelper(application);
        this.mStoreHelper = new StoreHelper(application);
        this.mSupplierHelper = new SupplierHelper(application);
    }



    public void handleLoadMaterialByBarCode(String id,String auth){
        mView.showProgressHUD();
        mMaterialHelper.getMaterialFromServerByBarCode(id, new CallbackData<Material>() {
            @Override
            public void onSuccess(Material material) {
                if(material !=null){
                    mView.updateScanMaterial(material);
                    mView.done();
                }
            }

            @Override
            public void onFail(String message) {
                mView.showMessage(message);
                mView.done();
            }
        },auth);
    }

    public void handleLoadMaterials(String auth){
        mView.showProgressHUD();
        mMaterialHelper.getMaterialsFromServer( new CallbackData<List<Material>>() {
            @Override
            public void onSuccess(List<Material> materials) {
                if(materials !=null){
                    mView.loadMaterials(materials);
                    mView.done();
                }
            }

            @Override
            public void onFail(String message) {
                mView.showMessage(message);
                mView.done();
            }
        },auth);
    }

    public void handleAddTrannsaction(RequestAddTransaction requestAddTransaction,String auth){
        mView.showProgressHUD();
        mTransactionHelper.addTransactionIntoServer(requestAddTransaction, new CallbackData<Boolean>() {
            @Override
            public void onSuccess(Boolean aBoolean) {
                mView.returnAddResult(aBoolean);
                mView.done();
            }

            @Override
            public void onFail(String message) {
                mView.showMessage(message);
                mView.done();
            }
        },auth);
    }

    public void handleLoadTransactionEC(String storeId,String auth) {
        mView.showProgressHUD();
        mTransactionHelper.getTransactionEC(storeId, new CallbackData<List<Transaction>>() {
            @Override
            public void onSuccess(List<Transaction> transactions) {
                mView.returnTransactions(transactions);
                mView.done();
            }

            @Override
            public void onFail(String message) {
                mView.returnTransactions(null);
                mView.done();
            }
        },auth);
    }

    public void handleLoadtransaction(String storeId, String type,String auth) {
        Map<String,String> params = new HashMap<>();
        params.put("typeId",type);
        mTransactionHelper.getTransactionFromServer(storeId, params, new CallbackData<List<Transaction>>() {
            @Override
            public void onSuccess(List<Transaction> transactions) {
                mView.returnTransactions(transactions);
                mView.done();
            }

            @Override
            public void onFail(String message) {
                mView.done();
            }
        },auth);
    }

    public void handleLoadMaterialsByStore(String storeId,String auth) {
        mInventoryHelper.getInventoryFromServer(storeId, new CallbackData<List<StoreMaterial>>() {
            @Override
            public void onSuccess(List<StoreMaterial> storeMaterials) {
                mView.returnStoreMaterial(storeMaterials);
                mView.done();
            }

            @Override
            public void onFail(String message) {
                mView.done();
            }
        },auth);
    }

    public void handleLoadMaterialsFromSupplier(String SupplierId,String auth) {
        mMaterialHelper.getMaterialFromServerFromSupplier(SupplierId, new CallbackData<List<Material>>() {
            @Override
            public void onSuccess(List<Material> storeMaterials) {
                mView.dismissProgressHUDNow();
                mView.loadMaterials(storeMaterials);
            }

            @Override
            public void onFail(String message) {
                mView.dismissProgressHUDNow();
                mView.showMessage(message);
            }
        },auth);
    }

    public void handleAddCheckStore(String storeId,RequestAddCheckStore addCheckStore,String auth) {
        mCheckStoreHelper.addCheckStore(storeId,addCheckStore, new CallbackData<Boolean>() {
            @Override
            public void onSuccess(Boolean result) {
                mView.returnAddResult(result);
                mView.done();
            }

            @Override
            public void onFail(String message) {
                mView.done();
            }
        },auth);
    }

    public void hanleGetSimpleStore(String auth) {
        mStoreHelper.getTransactionFromServer(new CallbackData<List<Detail>>() {
            @Override
            public void onSuccess(List<Detail> details) {
                mView.dismissProgressHUDNow();
                mView.addExchangeStore(details);
            }

            @Override
            public void onFail(String message) {
                mView.showMessage(message);
            }
        },auth);
    }

    public void handleLoadSupplier(String auth){
        mSupplierHelper.getSupplierFromServer(new CallbackData<List<Detail>>() {
            @Override
            public void onSuccess(List<Detail> details) {
                mView.addSuppliers(details);
                mView.dismissProgressHUDNow();
            }

            @Override
            public void onFail(String message) {

                mView.showMessage(message);
            }
        },auth);
    }
}
