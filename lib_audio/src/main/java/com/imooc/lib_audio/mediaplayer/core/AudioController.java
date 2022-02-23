package com.imooc.lib_audio.mediaplayer.core;

import com.imooc.lib_audio.mediaplayer.db.GreenDaoHelper;
import com.imooc.lib_audio.mediaplayer.events.AudioCompleteEvent;
import com.imooc.lib_audio.mediaplayer.events.AudioErrorEvent;
import com.imooc.lib_audio.mediaplayer.events.AudioFavouriteEvent;
import com.imooc.lib_audio.mediaplayer.events.AudioPlayModeEvent;
import com.imooc.lib_audio.mediaplayer.exception.AudioQueueEmptyException;
import com.imooc.lib_audio.mediaplayer.model.AudioBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Random;

/**
 * 音频控制中心
 * <p>
 * 保存、处理播放列表，处理收藏音乐功能
 */
public class AudioController {

    public static AudioController getInstance() {
        return AudioController.SingletonHolder.instance;
    }

    private static class SingletonHolder {
        private static final AudioController instance = new AudioController();
    }

    /**
     * 播放方式
     */
    public enum PlayMode {
        /**
         * 列表循环
         */
        LOOP,
        /**
         * 随机
         */
        RANDOM,
        /**
         * 单曲循环
         */
        REPEAT
    }

    private final AudioPlayer mAudioPlayer;
    //播放队列,不能为空,不设置主动抛错
    private final ArrayList<AudioBean> mQueue;
    private int mQueueIndex;
    private PlayMode mPlayMode;

    private AudioController() {
        EventBus.getDefault().register(this);
        mAudioPlayer = new AudioPlayer();
        mQueue = new ArrayList<>();
        mQueueIndex = 0;
        mPlayMode = PlayMode.LOOP;
    }

    private int queryAudio(AudioBean bean) {
        return mQueue.indexOf(bean);
    }

    public ArrayList<AudioBean> getQueue() {
        return mQueue == null ? new ArrayList<AudioBean>() : mQueue;
    }

    /**
     * 设置播放队列
     */
    public void setQueue(ArrayList<AudioBean> queue) {
        setQueue(queue, 0);
    }

    public void setQueue(ArrayList<AudioBean> queue, int queueIndex) {
        mQueue.addAll(queue);
        mQueueIndex = queueIndex;
    }

    /**
     * 队列头添加播放哥曲
     */
    public void addAudio(AudioBean bean) {
        this.addAudio(0, bean);
    }

    private void addCustomAudio(int index, AudioBean bean) {
        if (mQueue == null) {
            throw new AudioQueueEmptyException("当前播放队列为空,请先设置播放队列.");
        }
        mQueue.add(index, bean);
    }

    public void addAudio(int index, AudioBean bean) {
        if (mQueue == null) {
            throw new AudioQueueEmptyException("当前播放队列为空,请先设置播放队列.");
        }
        int query = queryAudio(bean);
        if (query <= -1) {
            //没添加过此id的歌曲，添加且直播番放
            addCustomAudio(index, bean);
            setPlayIndex(index);
        } else {
            AudioBean currentBean = getNowPlaying();
            if (!currentBean.id.equals(bean.id)) {
                //添加过且不是当前播放，播，否则什么也不干
                setPlayIndex(query);
            }
        }
    }

    public PlayMode getPlayMode() {
        return mPlayMode;
    }

    public void setPlayMode(PlayMode playMode) {
        mPlayMode = playMode;
        //还要对外发送切换事件，更新UI
        EventBus.getDefault().post(new AudioPlayModeEvent(mPlayMode));
    }

    public void setPlayIndex(int index) {
        if (mQueue == null) {
            throw new AudioQueueEmptyException("当前播放队列为空,请先设置播放队列.");
        }
        mQueueIndex = index;
        play();
    }

    public int getPlayIndex() {
        return mQueueIndex;
    }

    /**
     * 获取播放器当前状态
     */
    private CustomMediaPlayer.Status getStatus() {
        return mAudioPlayer.getStatus();
    }

    /**
     * 对外提供是否播放中状态
     */
    public boolean isStartState() {
        return CustomMediaPlayer.Status.STARTED == getStatus();
    }

    /**
     * 对外提提供是否暂停状态
     */
    public boolean isPauseState() {
        return CustomMediaPlayer.Status.PAUSED == getStatus();
    }

    /**
     * 对外提供获取当前播放时间
     */
    public int getNowPlayTime() {
        return mAudioPlayer.getCurrentPosition();
    }

    /**
     * 对外提供获取总播放时间
     */
    public int getTotalPlayTime() {
        return mAudioPlayer.getCurrentPosition();
    }

    /**
     * 对外提供的获取当前歌曲信息
     */
    public AudioBean getNowPlaying() {
        return getPlaying();
    }

    /**
     * 加载当前index歌曲
     */
    public void play() {
        AudioBean bean = getPlaying();
        load(bean);
    }

    public void resume() {
        mAudioPlayer.resume();
    }

    public void pause() {
        mAudioPlayer.pause();
    }

    public void release() {
        mAudioPlayer.release();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 加载下一首歌曲
     */
    public void next() {
        AudioBean bean = getNextPlaying();
        load(bean);
    }

    /**
     * 加载上一首歌曲
     */
    public void previous() {
        AudioBean bean = getPreviousPlaying();
        load(bean);
    }

    /**
     * 播放/暂停切换
     */
    public void playOrPause() {
        if (isStartState()) {
            pause();
        } else if (isPauseState()) {
            resume();
        }
    }

    private AudioBean getPlaying() {
        if (mQueue != null && !mQueue.isEmpty() && mQueueIndex >= 0 && mQueueIndex < mQueue.size()) {
            return mQueue.get(mQueueIndex);
        } else {
            throw new AudioQueueEmptyException("当前播放队列为空,请先设置播放队列.");
        }
    }

    private AudioBean getNextPlaying() {
        switch (mPlayMode) {
            case LOOP:
                mQueueIndex = (mQueueIndex + 1) % mQueue.size();
                return getPlaying();
            case RANDOM:
                mQueueIndex = new Random().nextInt(mQueue.size()) % mQueue.size();
                return getPlaying();
            case REPEAT:
                return getPlaying();
        }
        return getPlaying();
    }

    private AudioBean getPreviousPlaying() {
        switch (mPlayMode) {
            case LOOP:
                mQueueIndex = (mQueueIndex + mQueue.size() - 1) % mQueue.size();
                return getPlaying();
            case RANDOM:
                mQueueIndex = new Random().nextInt(mQueue.size()) % mQueue.size();
                return getPlaying();
            case REPEAT:
                return getPlaying();
        }
        return getPlaying();
    }

    /**
     * 添加/移除到收藏
     */
    public void changeFavourite() {
        if (null != GreenDaoHelper.selectFavourite(getNowPlaying())) {
            // 已收藏，移除
            GreenDaoHelper.removeFavourite(getNowPlaying());
            EventBus.getDefault().post(new AudioFavouriteEvent(false));
        } else {
            // 未收藏，添加收藏
            GreenDaoHelper.addFavourite(getNowPlaying());
            EventBus.getDefault().post(new AudioFavouriteEvent(true));
        }
    }

    private void load(AudioBean bean) {
        mAudioPlayer.load(bean);
    }

    //插放完毕事件处理
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAudioCompleteEvent(AudioCompleteEvent event) {
        next();
    }

    //播放出错事件处理
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAudioErrorEvent(AudioErrorEvent event) {
        next();
    }
}
