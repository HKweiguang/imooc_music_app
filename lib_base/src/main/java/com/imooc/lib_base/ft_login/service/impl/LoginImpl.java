package com.imooc.lib_base.ft_login.service.impl;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.imooc.lib_base.service.login.ILoginService;
import com.imooc.lib_base.service.login.LoginPluginConfig;
import com.imooc.lib_base.service.login.user.User;
import com.qihoo360.replugin.RePlugin;

public class LoginImpl {

    private final IBinder binder;
    private ILoginService loginService;

    private static volatile LoginImpl instance = null;

    public static LoginImpl getInstance() {
        if (instance == null) {
            synchronized (LoginImpl.class) {
                if (instance == null) {
                    instance = new LoginImpl();
                }
            }
        }
        return instance;
    }

    private LoginImpl() {
        binder = RePlugin.fetchBinder(LoginPluginConfig.PLUGIN_NAME, LoginPluginConfig.KEY_INTERFACE);
        if (binder != null) {
            loginService = ILoginService.Stub.asInterface(binder);
        }
    }

    public void login(Context context) {
        Intent intent = RePlugin.createIntent(LoginPluginConfig.PLUGIN_NAME, LoginPluginConfig.PAGE.PAGE_LOGIN);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        RePlugin.startActivity(context, intent);
    }

    public boolean hasLogin() {
        if (binder == null) return false;
        try {
            return loginService.hasLogin();
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void removeUser() {
        if (binder == null) return;
        try {
            loginService.removeUser();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public User getUserInfo() {
        if (binder == null) return null;
        try {
            return loginService.getUserinfo();
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }
}
