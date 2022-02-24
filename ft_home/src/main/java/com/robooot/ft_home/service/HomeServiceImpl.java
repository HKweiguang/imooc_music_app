package com.robooot.ft_home.service;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.imooc.lib_base.ft_home.service.HomeService;
import com.robooot.ft_home.view.home.HomeActivity;

@Route(path = "/home/home_service")
public class HomeServiceImpl implements HomeService {

    @Override
    public void startHomeActivity(Context context) {
        HomeActivity.start(context);
    }

    @Override
    public void init(Context context) {

    }
}
