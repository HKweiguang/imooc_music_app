package com.imooc.ft_login;

import android.app.Application;

import com.imooc.ft_login.service.aidl.LoginServiceImpl;
import com.imooc.lib_base.service.login.LoginPluginConfig;
import com.qihoo360.replugin.RePlugin;

public class LoginApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RePlugin.registerPluginBinder(LoginPluginConfig.KEY_INTERFACE, new LoginServiceImpl());
    }
}
