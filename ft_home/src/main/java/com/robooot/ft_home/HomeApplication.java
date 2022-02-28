package com.robooot.ft_home;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;

public class HomeApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ARouter.init(this);
    }
}
