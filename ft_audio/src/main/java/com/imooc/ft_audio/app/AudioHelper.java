package com.imooc.ft_audio.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;

import com.imooc.ft_audio.mediaplayer.core.AudioController;
import com.imooc.ft_audio.mediaplayer.core.MusicService;
import com.imooc.ft_audio.mediaplayer.db.GreenDaoHelper;
import com.imooc.ft_audio.mediaplayer.model.AudioBean;
import com.imooc.ft_audio.mediaplayer.utils.Utils;
import com.imooc.ft_audio.mediaplayer.view.MusicPlayerActivity;
import com.imooc.lib_base.ft_audio.model.CommonAudioBean;

import java.util.ArrayList;

@SuppressLint("StaticFieldLeak")
public class AudioHelper {

    //SDK全局Context, 供子模块用
    private static Context mContext;

    public static void init(Context context) {
        mContext = context;
        //初始化本地数据库
        GreenDaoHelper.initDatabase();
    }

    public static Context getContext() {
        return mContext;
    }

    //外部启动MusicService方法
    public static void startMusicService(ArrayList<CommonAudioBean> audios) {
        MusicService.startMusicService(Utils.convertFrom(audios));
    }

    public static void addAudio(Activity activity, CommonAudioBean bean) {
        AudioController.getInstance().addAudio(Utils.convertFrom(bean));
        MusicPlayerActivity.start(activity);
    }

    public static void pauseAudio() {
        AudioController.getInstance().pause();
    }

    public static void resumeAudio() {
        AudioController.getInstance().resume();
    }

}
