package com.imooc.imooc_voice.view.home.adpater;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.imooc.imooc_voice.model.CHANNEL;
import com.imooc.imooc_voice.view.VideoFragment;
import com.imooc.imooc_voice.view.discory.DiscoryFragment;
import com.imooc.imooc_voice.view.friend.FriendFragment;
import com.imooc.imooc_voice.view.mine.MineFragment;

public class HomePagerAdapter extends FragmentPagerAdapter {

    private final CHANNEL[] mList;

    public HomePagerAdapter(FragmentManager fm, CHANNEL[] datas) {
        super(fm);
        mList = datas;
    }

    @Override
    public Fragment getItem(int position) {
        int type = mList[position].getValue();
        switch (type) {
            case CHANNEL.MINE_ID:
                return MineFragment.newInstance();
            case CHANNEL.DISCORY_ID:
                return DiscoryFragment.newInstance();
            case CHANNEL.FRIEND_ID:
                return FriendFragment.newInstance();
            case CHANNEL.VIDEO_ID:
                return VideoFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.length;
    }
}
