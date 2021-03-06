package com.robooot.ft_home.view.home;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.launcher.ARouter;
import com.imooc.lib_base.ft_audio.model.CommonAudioBean;
import com.imooc.lib_base.ft_audio.service.impl.AudioImpl;
import com.imooc.lib_base.ft_login.model.LoginEvent;
import com.imooc.lib_base.ft_login.service.impl.LoginImpl;
import com.imooc.lib_common_ui.base.BaseActivity;
import com.imooc.lib_common_ui.pager_indictor.ScaleTransitionPagerTitleView;
import com.imooc.lib_image_loader.app.ImageLoaderManager;
import com.imooc.lib_update.app.UpdateHelper;
import com.robooot.ft_home.R;
import com.robooot.ft_home.constant.Constant;
import com.robooot.ft_home.model.CHANNEL;
import com.robooot.ft_home.utils.Utils;
import com.robooot.ft_home.view.home.adpater.HomePagerAdapter;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

public class HomeActivity extends BaseActivity implements View.OnClickListener {

    public static void start(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
    }

    private static final CHANNEL[] CHANNELS =
            new CHANNEL[]{CHANNEL.MY, CHANNEL.DISCORY, CHANNEL.FRIEND};

    private UpdateReceiver mReceiver = null;

    /*
     * View
     */
    private DrawerLayout mDrawerLayout;
    private View mToggleView;
    private View mSearchView;
    private ViewPager mViewPager;
    private HomePagerAdapter mAdapter;
    private View unLogginLayout;
    private ImageView mPhotoView;
    private View mDrawerQrcodeView;
    private View mDrawerShareView;

    /*
     * data
     */
    private final ArrayList<CommonAudioBean> mLists = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerBroadcastReceiver();
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_home);
        initView();
        initData();
    }

    private void initData() {
        mLists.add(new CommonAudioBean("100001", "http://sp-sycdn.kuwo.cn/resource/n2/85/58/433900159.mp3",
                "?????????????????????", "?????????", "?????????", "???????????????????????????????????????,?????????????????????????????????,?????????????????????????????????????????????",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1559698076304&di=e6e99aa943b72ef57b97f0be3e0d2446&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fblog%2F201401%2F04%2F20140104170315_XdG38.jpeg",
                "4:30"));
        mLists.add(
                new CommonAudioBean("100002", "http://sq-sycdn.kuwo.cn/resource/n1/98/51/3777061809.mp3", "??????",
                        "?????????", "??????", "???????????????????????????????????????,?????????????????????????????????,?????????????????????????????????????????????",
                        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1559698193627&di=711751f16fefddbf4cbf71da7d8e6d66&imgtype=jpg&src=http%3A%2F%2Fimg0.imgtn.bdimg.com%2Fit%2Fu%3D213168965%2C1040740194%26fm%3D214%26gp%3D0.jpg",
                        "4:40"));
        mLists.add(
                new CommonAudioBean("100003", "http://sp-sycdn.kuwo.cn/resource/n2/52/80/2933081485.mp3", "????????????",
                        "??????", "?????????", "???????????????????????????????????????,?????????????????????????????????,?????????????????????????????????????????????",
                        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1559698239736&di=3433a1d95c589e31a36dd7b4c176d13a&imgtype=0&src=http%3A%2F%2Fpic.zdface.com%2Fupload%2F201051814737725.jpg",
                        "3:20"));
        mLists.add(
                new CommonAudioBean("100004", "http://sr-sycdn.kuwo.cn/resource/n2/33/25/2629654819.mp3", "?????????",
                        "?????????", "?????????", "???????????????????????????????????????,?????????????????????????????????,?????????????????????????????????????????????",
                        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1559698289780&di=5146d48002250bf38acfb4c9b4bb6e4e&imgtype=0&src=http%3A%2F%2Fpic.baike.soso.com%2Fp%2F20131220%2Fbki-20131220170401-1254350944.jpg",
                        "2:45"));

        AudioImpl.getInstance().startMusicService(mLists);
    }

    private void initView() {
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mToggleView = findViewById(R.id.toggle_view);
        mSearchView = findViewById(R.id.search_view);
        mViewPager = findViewById(R.id.view_pager);
        mDrawerQrcodeView = findViewById(R.id.home_qrcode);
        mDrawerShareView = findViewById(R.id.home_music);
        unLogginLayout = findViewById(R.id.unloggin_layout);
        mPhotoView = findViewById(R.id.avatr_view);

        mToggleView.setOnClickListener(this);
        mSearchView.setOnClickListener(this);
        mDrawerQrcodeView.setOnClickListener(this);
        mDrawerShareView.setOnClickListener(this);
        findViewById(R.id.online_music_view).setOnClickListener(this);
        findViewById(R.id.check_update_view).setOnClickListener(this);
        unLogginLayout.setOnClickListener(this);
        findViewById(R.id.exit_layout).setOnClickListener(this);

        mAdapter = new HomePagerAdapter(getSupportFragmentManager(), CHANNELS);
        mViewPager.setAdapter(mAdapter);
        initMagicIndicator();
    }

    /**
     * ??????????????????
     */
    private void initMagicIndicator() {
        MagicIndicator magicIndicator = findViewById(R.id.magic_indicator);
        magicIndicator.setBackgroundColor(Color.WHITE);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return CHANNELS.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, int index) {
                SimplePagerTitleView simplePagerTitleView = new ScaleTransitionPagerTitleView(HomeActivity.this);
                simplePagerTitleView.setText(CHANNELS[index].getKey());
                simplePagerTitleView.setTextSize(19);
                simplePagerTitleView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                simplePagerTitleView.setNormalColor(Color.parseColor("#999999"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#333333"));
                simplePagerTitleView.setOnClickListener(v ->
                        mViewPager.setCurrentItem(index)
                );
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                return null;
            }

            @Override
            public float getTitleWeight(Context context, int index) {
                return 1.0f;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, mViewPager);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //???????????????task???activity
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.exit_layout) {
            finish();
            System.exit(0);
        }

        if (id == R.id.unloggin_layout) {
            if (!LoginImpl.getInstance().hasLogin()) {
                LoginImpl.getInstance().login(this);
            } else {
                mDrawerLayout.closeDrawer(Gravity.LEFT);
            }
        }
        if (id == R.id.toggle_view) {
            if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                mDrawerLayout.closeDrawer(Gravity.LEFT);
            } else {
                mDrawerLayout.openDrawer(Gravity.LEFT);
            }
        }
        if (id == R.id.home_qrcode) {
            if (hasPermission(Constant.HARDWEAR_CAMERA_PERMISSION)) {
                doCameraPermission();
            } else {
                requestPermission(Constant.HARDWEAR_CAMERA_CODE, Constant.HARDWEAR_CAMERA_PERMISSION);
            }
        }
        if (id == R.id.home_music) {//shareFriend();
            goToMusic();
        }
        if (id == R.id.online_music_view) {//????????????webactivity
            gotoWebView("https://www.imooc.com");
        }
        if (id == R.id.check_update_view) {
            checkUpdate();
        }
    }

    @Override
    public void doCameraPermission() {
        ARouter.getInstance().build(Constant.Router.ROUTER_CAPTURE_ACTIVIYT).navigation();
    }

    private void goToMusic() {
        ARouter.getInstance().build(Constant.Router.ROUTER_MUSIC_ACTIVIYT).navigation();
    }

    private void gotoWebView(String url) {
        ARouter.getInstance()
                .build(Constant.Router.ROUTER_WEB_ACTIVIYT)
                .withString("url", url)
                .navigation();
    }

    /**
     * ??????????????????
     */
    private void checkUpdate() {
        UpdateHelper.checkUpdate(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        unRegisterBroadcastReceiver();
    }

    /**
     * ??????????????????
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginEvent(LoginEvent event) {
        unLogginLayout.setVisibility(View.GONE);
        mPhotoView.setVisibility(View.VISIBLE);
        ImageLoaderManager.getInstance()
                .displayImageForCircle(mPhotoView, LoginImpl.getInstance().getUserInfo().data.photoUrl);
    }

    private void registerBroadcastReceiver() {
        if (mReceiver == null) {
            mReceiver = new UpdateReceiver();
            LocalBroadcastManager.getInstance(this)
                    .registerReceiver(mReceiver, new IntentFilter(UpdateHelper.UPDATE_ACTION));
        }
    }

    private void unRegisterBroadcastReceiver() {
        if (mReceiver != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver);
        }
    }

    /**
     * ??????Update???????????????
     */
    public static class UpdateReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //??????????????????
            context.startActivity(
                    Utils.getInstallApkIntent(context, intent.getStringExtra(UpdateHelper.UPDATE_FILE_KEY)));
        }
    }
}
