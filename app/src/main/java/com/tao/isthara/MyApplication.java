package com.tao.isthara;

import android.app.Application;
import android.content.Context;

import com.tao.isthara.Utils.AppPreferences;

public class MyApplication extends Application {
    private static Context context;
    private AppPreferences _appPrefs;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        _appPrefs = new AppPreferences(getApplicationContext());

    }
}