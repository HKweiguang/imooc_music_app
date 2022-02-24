package com.robooot.ft_home.view.home.adpater;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.robooot.ft_home.model.CHANNEL;
import com.robooot.ft_home.view.discory.DiscoryFragment;
import com.robooot.ft_home.view.friend.FriendFragment;
import com.robooot.ft_home.view.mine.MineFragment;

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
        }
        return null;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.length;
    }
}
