package com.imooc.ft_home.view.home.adpater;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.imooc.ft_home.model.CHANNEL;
import com.imooc.ft_home.view.discory.DiscoryFragment;
import com.imooc.ft_home.view.friend.FriendFragment;
import com.qihoo360.replugin.RePlugin;

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
                return getMineFragment();
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

    /**
     * 获取插件中的mineFragment
     *
     * @return Fragment
     */
    private Fragment getMineFragment() {
        Fragment fragment = null;
        // 拿到插件Context
        Context context = RePlugin.fetchContext("ft_mine");
        if (context != null) {
            // 获取插件ClassLoader
            ClassLoader classLoader = RePlugin.fetchClassLoader("ft_mine");
            try {
                fragment = classLoader.loadClass("com.imooc.ft_home.view.mine.MineFragment").asSubclass(Fragment.class).newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return fragment;
    }
}
