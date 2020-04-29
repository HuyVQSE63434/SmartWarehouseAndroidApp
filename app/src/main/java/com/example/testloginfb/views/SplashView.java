package com.example.testloginfb.views;

import com.example.testloginfb.models.Staff;

public interface SplashView extends BaseView {
    void goToMainActivity();

    void goToNoInternetActivity();

    void goToHomeScreenActivity(Staff staff);
}
