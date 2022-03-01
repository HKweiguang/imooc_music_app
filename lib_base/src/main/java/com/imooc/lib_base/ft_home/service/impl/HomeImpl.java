package com.imooc.lib_base.ft_home.service.impl;

import android.content.Context;
import android.content.Intent;

import com.imooc.lib_base.service.home.HomePluginConfig;
import com.qihoo360.replugin.RePlugin;

public class HomeImpl {

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
//        ARouter.getInstance().inject(this);
    }

    public void startHomeActivity(Context context) {
        Intent intent = RePlugin.createIntent(HomePluginConfig.PLUGIN_NAME, HomePluginConfig.PAGE.PAGE_HOME);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        RePlugin.startActivity(context, intent);
    }
}
