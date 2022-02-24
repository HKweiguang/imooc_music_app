package com.imooc.lib_base.ft_home.service;

import android.content.Context;

import com.alibaba.android.arouter.facade.template.IProvider;

public interface HomeService extends IProvider {

    void startHomeActivity(Context context);
}
