package com.example.aletta.nokiaowerinternet.base;

import android.app.Application;

import com.example.aletta.nokiaowerinternet.ResourceProvider;

public class BaseApplication extends Application {

    private static Application context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        ResourceProvider resourceProvider = new ResourceProvider();
        resourceProvider.init(context);
    }

    public static Application getContext() {
        return context;
    }
}
