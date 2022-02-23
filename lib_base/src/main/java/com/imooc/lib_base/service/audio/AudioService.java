package com.imooc.lib_base.service.audio;

import com.alibaba.android.arouter.facade.template.IProvider;

public interface AudioService extends IProvider {

    void pauseAudio();

    void resumeAudio();
}
