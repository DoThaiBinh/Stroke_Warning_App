package com.example.appcanhbaodotquy.Activity;

import android.app.Application;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        ChatIconManager chatIconManager = new ChatIconManager(this);
        registerActivityLifecycleCallbacks(chatIconManager);

    }
}
