package com.imooc.lib_base.ft_login.service.impl;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.imooc.lib_base.ft_login.service.LoginService;
import com.imooc.lib_base.service.login.ILoginService;
import com.imooc.lib_base.service.login.LoginPluginConfig;
import com.imooc.lib_base.service.login.user.User;
import com.qihoo360.replugin.RePlugin;

public class LoginImpl {

//    @Autowired(name = "/login/login_service")
//    protected LoginService mLoginService;

    private final IBinder binder;

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
//        ARouter.getInstance().inject(this);
        binder = RePlugin.fetchBinder(LoginPluginConfig.PLUGIN_NAME, LoginPluginConfig.KEY_INTERFACE);
    }

    public void login(Context context) {
        Intent intent = RePlugin.createIntent(LoginPluginConfig.PLUGIN_NAME, LoginPluginConfig.PAGE.PAGE_LOGIN);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        RePlugin.startActivity(context, intent);
//        mLoginService.login(context);
    }

    public boolean hasLogin() {
//        return mLoginService.hasLogin();
        if (binder == null) return false;

        ILoginService loginService = ILoginService.Stub.asInterface(binder);
        try {
            return loginService.hasLogin();
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void removeUser() {
//        mLoginService.removeUser();
        if (binder == null) return;

        ILoginService loginService = ILoginService.Stub.asInterface(binder);
        try {
            loginService.removeUser();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public User getUserInfo() {
        if (binder == null) return null;

        ILoginService loginService = ILoginService.Stub.asInterface(binder);
        try {
            return loginService.getUserinfo();
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }
}
