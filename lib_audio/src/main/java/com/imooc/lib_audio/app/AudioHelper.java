package com.imooc.lib_audio.app;

import android.annotation.SuppressLint;
import android.content.Context;

@SuppressLint("StaticFieldLeak")
public class AudioHelper {

    //SDK全局Context, 供子模块用
    private static Context mContext;

    public static void init(Context context) {
        mContext = context;
        //初始化本地数据库
//        GreenDaoHelper.initDatabase();
    }

    public static Context getContext() {
        return mContext;
    }

}
