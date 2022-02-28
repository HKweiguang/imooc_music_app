package com.imooc.ft_login.service.aidl;

import android.os.RemoteException;

import com.imooc.ft_login.manager.UserManager;
import com.imooc.lib_base.service.login.ILoginService;
import com.imooc.lib_base.service.login.user.User;

public class LoginServiceImpl extends ILoginService.Stub {
    @Override
    public boolean hasLogin() throws RemoteException {
        return UserManager.getInstance().hasLogined();
    }

    @Override
    public void removeUser() throws RemoteException {
        UserManager.getInstance().removeUser();
    }

    @Override
    public User getUserinfo() throws RemoteException {
        return UserManager.getInstance().getUser();
    }
}
