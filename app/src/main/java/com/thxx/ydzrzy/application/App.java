package com.thxx.ydzrzy.application;

import android.app.Application;

/**
 * Created by saber on 2018/1/4
 */

public class App extends Application {
    public static App mApp;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public static App getInstance() {
        return mApp;
    }
}
