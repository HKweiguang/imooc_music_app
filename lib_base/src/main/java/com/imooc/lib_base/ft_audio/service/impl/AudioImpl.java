package com.imooc.lib_base.ft_audio.service.impl;

import android.app.Activity;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.imooc.lib_base.ft_audio.model.CommonAudioBean;
import com.imooc.lib_base.ft_audio.service.AudioService;

import java.util.ArrayList;

public class AudioImpl {

    @Autowired(name = "/audio/audio_service")
    protected static AudioService audioService;

    private static volatile AudioImpl instance;

    public static AudioImpl getInstance() {
        if (instance == null) {
            synchronized (AudioImpl.class) {
                if (instance == null) instance = new AudioImpl();
            }
        }
        return instance;
    }

    private AudioImpl() {
        ARouter.getInstance().inject(this);
    }

    /**
     * 暂停音乐播放器
     */
    public void pauseAudio() {
        audioService.pauseAudio();
    }

    /**
     * 恢复音乐播放器
     */
    public void resumeAudio() {
        audioService.resumeAudio();
    }

    /**
     * 添加并播放歌曲
     */
    public void addAudio(Activity activity, CommonAudioBean bean) {
        audioService.addAudio(activity, bean);
    }

    /**
     * 启动音乐播放服务
     */
    public void startMusicService(ArrayList<CommonAudioBean> audioBeans) {
        audioService.startMusicService(audioBeans);
    }
}
