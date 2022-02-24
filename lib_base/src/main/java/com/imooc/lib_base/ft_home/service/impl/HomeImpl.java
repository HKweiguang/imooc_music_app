package com.imooc.lib_base.ft_home.service.impl;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.imooc.lib_base.ft_home.service.HomeService;

public class HomeImpl {

    @Autowired(name = "/home/home_service")
    protected HomeService homeService;

    private static volatile HomeImpl instance;

    public static HomeImpl getInstance() {
        if (instance == null) {
            synchronized (HomeImpl.class) {
                if (instance == null) instance = new HomeImpl();
            }
        }
        return instance;
    }

    private HomeImpl() {
        ARouter.getInstance().inject(this);
    }

    public void startHomeActivity(Context context) {
        homeService.startHomeActivity(context);
    }
}
