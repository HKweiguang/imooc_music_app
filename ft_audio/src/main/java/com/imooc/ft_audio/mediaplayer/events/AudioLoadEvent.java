package com.imooc.ft_audio.mediaplayer.events;

import com.imooc.ft_audio.mediaplayer.model.AudioBean;

public class AudioLoadEvent {
  public AudioBean mAudioBean;

  public AudioLoadEvent(AudioBean audioBean) {
    this.mAudioBean = audioBean;
  }
}
