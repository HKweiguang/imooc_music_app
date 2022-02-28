package com.imooc.ft_login.view.presenter;

import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;
import com.imooc.ft_login.api.MockData;
import com.imooc.ft_login.api.RequestCenter;
import com.imooc.ft_login.view.inter.IUserLoginPresenter;
import com.imooc.ft_login.view.inter.IUserLoginView;
import com.imooc.ft_login.manager.UserManager;
import com.imooc.lib_base.service.login.LoginPluginConfig;
import com.imooc.lib_base.service.login.user.User;
import com.imooc.lib_network.listener.DisposeDataListener;

/**
 * 登陆页面对应Presenter
 */
public class UserLoginPresenter implements IUserLoginPresenter, DisposeDataListener {

    private final IUserLoginView mIView;
    private final Context mContext;

    public UserLoginPresenter(IUserLoginView iView, Context context) {
        mIView = iView;
        mContext = context;
    }

    @Override
    public void login(String username, String password) {
        mIView.showLoadingView();
        RequestCenter.login(this);
    }

    @Override
    public void onSuccess(Object responseObj) {
        mIView.hideLoadingView();
        User user = (User) responseObj;
        UserManager.getInstance().saveUser(user);
        //发送登陆Event
//        EventBus.getDefault().post(new LoginEvent());
        sendUserBoradcast(user);
        mIView.finishActivity();
    }

    @Override
    public void onFailure(Object reasonObj) {
        mIView.hideLoadingView();
        onSuccess(new Gson().fromJson(MockData.LOGIN_DATA, User.class));
        mIView.showLoginFailedView();
    }

    private void sendUserBoradcast(User user) {
        Intent intent = new Intent();
        intent.setAction(LoginPluginConfig.ACTION.LOGIN_SUCCESS_ACTION);
        intent.putExtra(LoginPluginConfig.ACTION.KEY_DATA, new Gson().toJson(user));
        mContext.sendBroadcast(intent);
    }
}
