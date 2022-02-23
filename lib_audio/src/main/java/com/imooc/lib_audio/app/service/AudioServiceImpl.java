package com.imooc.lib_audio.app.service;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.imooc.lib_audio.mediaplayer.core.AudioController;
import com.imooc.lib_base.service.audio.AudioService;

@Route(path = "/audio/audio_service")
public class AudioServiceImpl implements AudioService {
    @Override
    public void pauseAudio() {
        AudioController.getInstance().pause();
    }

    @Override
    public void resumeAudio() {
        AudioController.getInstance().resume();
    }

    @Override
    public void init(Context context) {

    }
}
