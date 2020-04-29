package com.example.testloginfb.views;

import com.example.testloginfb.models.Staff;

public interface MainView extends BaseView {
    void goToHomeActivity(Staff staff);

    void showToastMessage(String message);
}
