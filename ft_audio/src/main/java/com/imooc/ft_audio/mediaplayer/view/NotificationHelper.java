package com.imooc.ft_audio.mediaplayer.view;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import android.widget.RemoteViews;

import com.imooc.ft_audio.R;
import com.imooc.ft_audio.app.AudioHelper;
import com.imooc.ft_audio.mediaplayer.core.AudioController;
import com.imooc.ft_audio.mediaplayer.core.MusicService;
import com.imooc.ft_audio.mediaplayer.db.GreenDaoHelper;
import com.imooc.ft_audio.mediaplayer.model.AudioBean;
import com.imooc.lib_image_loader.app.ImageLoaderManager;

public class NotificationHelper {

    public static NotificationHelper getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        private static final NotificationHelper instance = new NotificationHelper();
    }

    public static final String CHANNEL_ID = "channel_id_audio";
    public static final String CHANNEL_NAME = "channel_name_audio";
    public static final int NOTIFICATION_ID = 0x111;

    //最终的Notification显示类
    private Notification mNotification;
    private RemoteViews mRemoteViews; // 大布局
    private RemoteViews mSmallRemoteViews; //小布局
    private NotificationManager mNotificationManager;
    private NotificationHelperListener mListener;
    private String packageName;
    //当前要播的歌曲Bean
    private AudioBean mAudioBean;

    public void init(NotificationHelperListener listener) {
        mNotificationManager = (NotificationManager) AudioHelper.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        packageName = AudioHelper.getContext().getPackageName();
        mAudioBean = AudioController.getInstance().getNowPlaying();
        initNotification();
        mListener = listener;
        if (mListener != null) mListener.onNotificationInit();
    }

    private void initNotification() {
        if (mNotification == null) {
            // 首先创建布局
            initRemoteViews();
            // 构建Notification
            // 适配Android 8.0
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
                channel.enableLights(false);
                channel.enableVibration(false);
                mNotificationManager.createNotificationChannel(channel);
            }

            Intent intent = new Intent(AudioHelper.getContext(), MusicPlayerActivity.class);
            @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntent = PendingIntent.getActivity(AudioHelper.getContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(AudioHelper.getContext(), CHANNEL_ID)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setCustomBigContentView(mRemoteViews)
                    .setContent(mSmallRemoteViews);
            mNotification = builder.build();

            showLoadStatus(mAudioBean);
        }
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private void initRemoteViews() {
        mRemoteViews = new RemoteViews(packageName, R.layout.notification_big_layout);
        mRemoteViews.setTextViewText(R.id.title_view, mAudioBean.name);
        mRemoteViews.setTextViewText(R.id.tip_view, mAudioBean.album);

        if (GreenDaoHelper.selectFavourite(mAudioBean) != null) {
            mRemoteViews.setImageViewResource(R.id.favourite_view, R.mipmap.note_btn_loved);
        } else {
            mRemoteViews.setImageViewResource(R.id.favourite_view, R.mipmap.note_btn_love_white);
        }

        mSmallRemoteViews = new RemoteViews(packageName, R.layout.notification_small_layout);
        mSmallRemoteViews.setTextViewText(R.id.title_view, mAudioBean.name);
        mSmallRemoteViews.setTextViewText(R.id.tip_view, mAudioBean.album);

        //点击播放按钮广播
        Intent playIntent = new Intent(MusicService.NotificationReceiver.ACTION_STATUS_BAR);
        playIntent.putExtra(MusicService.NotificationReceiver.EXTRA, MusicService.NotificationReceiver.EXTRA_PLAY);
        PendingIntent playPendngIntent = PendingIntent.getBroadcast(AudioHelper.getContext(), 1, playIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setOnClickPendingIntent(R.id.play_view, playPendngIntent);
        mSmallRemoteViews.setOnClickPendingIntent(R.id.play_view, playPendngIntent);

        //点击上一首按钮广播
        Intent previousIntent = new Intent(MusicService.NotificationReceiver.ACTION_STATUS_BAR);
        previousIntent.putExtra(MusicService.NotificationReceiver.EXTRA,
                MusicService.NotificationReceiver.EXTRA_PRE);
        PendingIntent previousPendingIntent =
                PendingIntent.getBroadcast(AudioHelper.getContext(), 2, previousIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setOnClickPendingIntent(R.id.previous_view, previousPendingIntent);

        //点击下一首按钮广播
        Intent nextIntent = new Intent(MusicService.NotificationReceiver.ACTION_STATUS_BAR);
        nextIntent.putExtra(MusicService.NotificationReceiver.EXTRA,
                MusicService.NotificationReceiver.EXTRA_NEXT);
        PendingIntent nextPendingIntent =
                PendingIntent.getBroadcast(AudioHelper.getContext(), 3, nextIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setOnClickPendingIntent(R.id.next_view, nextPendingIntent);
        mRemoteViews.setImageViewResource(R.id.next_view, R.mipmap.note_btn_next_white);
        mSmallRemoteViews.setOnClickPendingIntent(R.id.next_view, nextPendingIntent);
        mSmallRemoteViews.setImageViewResource(R.id.next_view, R.mipmap.note_btn_next_white);

        //点击收藏按钮广播
        Intent favouriteIntent = new Intent(MusicService.NotificationReceiver.ACTION_STATUS_BAR);
        favouriteIntent.putExtra(MusicService.NotificationReceiver.EXTRA,
                MusicService.NotificationReceiver.EXTRA_FAV);
        PendingIntent favouritePendingIntent =
                PendingIntent.getBroadcast(AudioHelper.getContext(), 4, favouriteIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setOnClickPendingIntent(R.id.favourite_view, favouritePendingIntent);
    }

    /**
     * 显示Notification的加载状态
     */
    public void showLoadStatus(AudioBean bean) {
        mAudioBean = bean;

        mRemoteViews.setImageViewResource(R.id.play_view, R.mipmap.note_btn_pause_white);
        mRemoteViews.setTextViewText(R.id.title_view, mAudioBean.name);
        mRemoteViews.setTextViewText(R.id.tip_view, mAudioBean.album);
        ImageLoaderManager.getInstance().displayImageForNotification(AudioHelper.getContext(), mRemoteViews, R.id.image_view, mNotification, NOTIFICATION_ID, mAudioBean.albumPic);

        if (GreenDaoHelper.selectFavourite(mAudioBean) != null) {
            mRemoteViews.setImageViewResource(R.id.favourite_view, R.mipmap.note_btn_loved);
        } else {
            mRemoteViews.setImageViewResource(R.id.favourite_view, R.mipmap.note_btn_love_white);
        }

        //小布局也要更新
        mSmallRemoteViews.setImageViewResource(R.id.play_view, R.mipmap.note_btn_pause_white);
        mSmallRemoteViews.setTextViewText(R.id.title_view, mAudioBean.name);
        mSmallRemoteViews.setTextViewText(R.id.tip_view, mAudioBean.album);
        ImageLoaderManager.getInstance()
                .displayImageForNotification(AudioHelper.getContext(), mSmallRemoteViews, R.id.image_view,
                        mNotification, NOTIFICATION_ID, mAudioBean.albumPic);

        mNotificationManager.notify(NOTIFICATION_ID, mNotification);
    }

    /**
     * 显示Notification的播放状态
     */
    public void showPlayStatus() {
        if (mRemoteViews != null) {
            mRemoteViews.setImageViewResource(R.id.play_view, R.mipmap.note_btn_pause_white);
            mSmallRemoteViews.setImageViewResource(R.id.play_view, R.mipmap.note_btn_pause_white);
            mNotificationManager.notify(NOTIFICATION_ID, mNotification);
        }
    }

    /**
     * 显示Notification的暂停状态
     */
    public void showPauseStatus() {
        if (mRemoteViews != null) {
            mRemoteViews.setImageViewResource(R.id.play_view, R.mipmap.note_btn_play_white);
            mSmallRemoteViews.setImageViewResource(R.id.play_view, R.mipmap.note_btn_play_white);
            mNotificationManager.notify(NOTIFICATION_ID, mNotification);
        }
    }

    public void changeFavouriteStatus(boolean isFavourite) {
        if (mRemoteViews != null) {
            mRemoteViews.setImageViewResource(R.id.favourite_view,
                    isFavourite ? R.mipmap.note_btn_loved : R.mipmap.note_btn_love_white);
            mNotificationManager.notify(NOTIFICATION_ID, mNotification);
        }
    }

    public Notification getNotification() {
        return mNotification;
    }

    /**
     * 与音乐service的回调通信
     */
    public interface NotificationHelperListener {
        void onNotificationInit();
    }
}
