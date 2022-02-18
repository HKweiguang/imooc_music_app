package com.imooc.imooc_voice.application;

import android.app.Application;

import com.imooc.lib_audio.app.AudioHelper;

public class ImoocVoiceApplication extends Application {

    private static ImoocVoiceApplication mApplication = null;

    public static ImoocVoiceApplication getInstance() {
        return mApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        //视频SDK初始化
//        VideoHelper.init(this);
        //音频SDK初始化
        AudioHelper.init(this);
        //分享SDK初始化
//        ShareManager.initSDK(this);
        //更新组件下载
//        UpdateHelper.init(this);
        //ARouter初始化
//        ARouter.init(this);
    }
}
