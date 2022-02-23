package com.imooc.imooc_voice.view.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.imooc.imooc_voice.R;
import com.imooc.imooc_voice.api.RequestCenter;
import com.imooc.imooc_voice.view.login.inter.IUserLoginView;
import com.imooc.imooc_voice.view.login.manager.UserManager;
import com.imooc.imooc_voice.view.login.presenter.UserLoginPresenter;
import com.imooc.imooc_voice.view.login.user.LoginEvent;
import com.imooc.imooc_voice.view.login.user.User;
import com.imooc.lib_common_ui.base.BaseActivity;
import com.imooc.lib_network.listener.DisposeDataListener;

import org.greenrobot.eventbus.EventBus;

public class LoginActivity extends BaseActivity implements View.OnClickListener, IUserLoginView {

    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    private UserLoginPresenter mUserLoginPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_layout);
        //初始化P层
        mUserLoginPresenter = new UserLoginPresenter(this);
        findViewById(R.id.login_view).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.login_view) {
            mUserLoginPresenter.login(getUserName(), getPassword());
        }
    }

    @Override
    public String getUserName() {
        return "18734924592";
    }

    @Override
    public String getPassword() {
        return "999999q";
    }

    @Override
    public void showLoginFailedView() {

    }

    @Override
    public void showLoadingView() {

    }

    @Override
    public void hideLoadingView() {

    }

    @Override
    public void finishActivity() {
        finish();
    }
}
