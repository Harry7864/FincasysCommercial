package com.example.fincasyscommercial;

import android.app.Application;

import com.example.fincasyscommercial.networkmanager.DroidNet;


public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DroidNet.init(this);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        DroidNet.getInstance().removeAllInternetConnectivityChangeListeners();
    }
}
