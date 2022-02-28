package com.imooc.lib_base.service.login;

import com.imooc.lib_base.service.login.user.User;

interface ILoginService {

    boolean hasLogin();

    void removeUser();

    User getUserinfo();
}
