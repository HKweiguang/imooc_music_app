package com.imooc.lib_video.videoplayer.core;

import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.launcher.ARouter;
import com.imooc.lib_base.ft_audio.service.impl.AudioImpl;
import com.imooc.lib_video.videoplayer.core.view.CustomVideoView;
import com.imooc.lib_video.videoplayer.core.view.VideoFullDialog;
import com.imooc.lib_video.videoplayer.utils.Utils;

public class VideoAdSlot implements CustomVideoView.ADVideoPlayerListener {

    private Context mContext;
    /**
     * UI
     */
    private CustomVideoView mVideoView;
    private ViewGroup mParentView;
    /**
     * Data
     */
    private String mVideoUrl;
    // 与context层的事件回调
    private SDKSlotListener mSlotListener;

    public VideoAdSlot(String videoUrl, SDKSlotListener slotListener) {
        ARouter.getInstance().inject(this);
        this.mVideoUrl = videoUrl;
        this.mSlotListener = slotListener;
        mParentView = slotListener.getAdParent();
        mContext = mParentView.getContext();
        initVieoView();
    }

    private void initVieoView() {
        mVideoView = new CustomVideoView(mContext);
        if (mVideoUrl != null) {
            mVideoView.setDataSource(mVideoUrl);
            mVideoView.setListener(this);
        }
        RelativeLayout paddingView = new RelativeLayout(mContext);
        paddingView.setBackgroundColor(mContext.getResources().getColor(android.R.color.black));
        paddingView.setLayoutParams(mVideoView.getLayoutParams());
        mParentView.addView(paddingView);
        mParentView.addView(mVideoView);
    }

    private boolean isRealPause() {
        if (mVideoView != null) {
            return mVideoView.isRealPause();
        }
        return false;
    }

    private boolean isComplete() {
        if (mVideoView != null) {
            return mVideoView.isComplete();
        }
        return false;
    }

    private void pauseVideo() {
        if (mVideoView != null) {
            mVideoView.seekAndPause(0);
        }
    }

    private void resumeVideo() {
        if (mVideoView != null) {
            mVideoView.resume();
        }
    }

    public void destroy() {
        if (mVideoView != null) {
            mVideoView.destroy();
            mVideoView = null;
            mContext = null;
            mVideoUrl = null;
        }
    }

    @Override
    public void onClickFullScreenBtn() {
        //获取videoview在当前界面的属性
        Bundle bundle = Utils.getViewProperty(mParentView);
        // 从容器中移除
        mParentView.removeView(mVideoView);
        VideoFullDialog dialog = new VideoFullDialog(mContext, mVideoView, mVideoUrl, mVideoView.getCurrentPosition());
        dialog.setListener(new VideoFullDialog.FullToSmallListener() {
            @Override
            public void getCurrentPlayPosition(int position) {
                // 回到小屏继续处理
                backToSmallMode(position);
            }

            @Override
            public void playComplete() {
                bigPlayComplete();
            }
        });
        dialog.setSlotListener(mSlotListener);
        dialog.setViewBundle(bundle);
        dialog.show();
        // 暂停音乐播放
        AudioImpl.getInstance().pauseAudio();
    }

    /**
     * 全屏返回小屏继续播放事件
     *
     * @param position 播放位置
     */
    private void backToSmallMode(int position) {
        if (mVideoView.getParent() == null) {
            mParentView.addView(mVideoView);
        }
        mVideoView.setTranslationY(0);
        mVideoView.isShowFullBtn(true);
        mVideoView.mute(true);
        mVideoView.setListener(this);
        mVideoView.seekAndResume(position);
        // 小屏恢复音乐播放
        AudioImpl.getInstance().resumeAudio();
    }

    /**
     * 全屏播放完毕回到小屏事件
     */
    private void bigPlayComplete() {
        if (mVideoView.getParent() == null) {
            mParentView.addView(mVideoView);
        }
        mVideoView.isShowFullBtn(true);
        mVideoView.mute(true);
        mVideoView.setListener(this);
        mVideoView.seekAndPause(0);
        // 全屏暂停音乐播放
        AudioImpl.getInstance().pauseAudio();
    }

    @Override
    public void onBufferUpdate(int time) {

    }

    @Override
    public void onClickVideo() {

    }

    @Override
    public void onClickBackBtn() {

    }

    @Override
    public void onClickPlay() {

    }

    @Override
    public void onAdVideoLoadSuccess() {
        if (mSlotListener != null) mSlotListener.onVideoLoadSuccess();
    }

    @Override
    public void onAdVideoLoadFailed() {
        if (mSlotListener != null) mSlotListener.onVideoFailed();
    }

    @Override
    public void onAdVideoLoadComplete() {
        if (mSlotListener != null) mSlotListener.onVideoComplete();
        mVideoView.setIsRealPause(true);
    }

    //传递消息到appcontext层
    public interface SDKSlotListener {

        ViewGroup getAdParent();

        void onVideoLoadSuccess();

        void onVideoFailed();

        void onVideoComplete();
    }
}
