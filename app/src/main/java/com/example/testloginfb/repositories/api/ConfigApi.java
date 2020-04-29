package com.example.testloginfb.repositories.api;

public class ConfigApi {
    //todo: update base url after upload api to server
    public static final String BASE_URL = "https://swhapicore.azurewebsites.net/api/";
    public static String AUTH_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYW1laWQiOiIxMzU0NDEzMTQzMTQ5ODciLCJ1bmlxdWVfbmFtZSI6IlZ1IFF1YW5nIEh1eSIsInJvbGUiOiJOViIsIm5iZiI6MTU2MjU2NTk1NywiZXhwIjoxNTY1MTU3OTU3LCJpYXQiOjE1NjI1NjU5NTcsImlzcyI6IlNXSCIsImF1ZCI6IlNXSCJ9.78sR-_LL0Vh98sfCuR8HPNUfENV5SgkY8IB9TFD11Bg";

    public interface Api {
        String LOGIN_BY_FACEBOOK = "staff/{id}";
        String LOGIN_BY_GOOGLE = "staff/google";
        String LOAD_TRANSACTION = "transaction/{storeId}";
        String ADD_TRANSACTION = "transaction";
        String LOAD_MATERIAL = "material/{id}";
        String LOAD_ALL_MATERIAL = "material";
        String GET_STAFF_INFO = "staff/{id}";
        String GET_CHECKSTORE = "checkStore/{id}";
        String LOAD_INVENTORY="storeMaterial/{storeId}";
        String LOAD_INVENTORY_DETAIL="materialTransaction/{storeId}/{materialId}";
        String LOAD_TRANSACTION_EC="transaction/EC/{storeId}";
        String DEVICE_TOKEN_LOGIN ="device/login";
        String DEVICE_TOKEN_LOGOUT ="device/logout";
        String GET_SIMPLE_STORE = "store/simpleStores";
        String LOAD_SUPPLIERS = "material/suppliers";
        String LOAD_SUPPLIER = "material/supplier/{id}";

    }
}
