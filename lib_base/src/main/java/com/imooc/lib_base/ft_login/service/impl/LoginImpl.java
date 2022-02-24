package com.imooc.lib_base.ft_login.service.impl;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.imooc.lib_base.ft_login.model.user.User;
import com.imooc.lib_base.ft_login.service.LoginService;

public class LoginImpl {

    @Autowired(name = "/login/login_service")
    protected LoginService mLoginService;

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
        ARouter.getInstance().inject(this);
    }

    public void login(Context context) {
        mLoginService.login(context);
    }

    public boolean hasLogin() {
        return mLoginService.hasLogin();
    }

    public void removeUser() {
        mLoginService.removeUser();
    }

    public User getUserInfo() {
        return mLoginService.getUserInfo();
    }
}
