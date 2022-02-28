package com.imooc.ft_login.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.imooc.ft_login.R;
import com.imooc.ft_login.view.inter.IUserLoginView;
import com.imooc.ft_login.view.presenter.UserLoginPresenter;
import com.imooc.lib_common_ui.base.PluginBaseActivity;

public class LoginActivity extends PluginBaseActivity implements View.OnClickListener, IUserLoginView {

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
        mUserLoginPresenter = new UserLoginPresenter(this, this);
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
