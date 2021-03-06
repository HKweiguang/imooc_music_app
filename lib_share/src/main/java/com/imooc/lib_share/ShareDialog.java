package com.imooc.lib_share;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;

public class ShareDialog extends Dialog {

    private final Context mContext;
    private final DisplayMetrics dm;

    /**
     * UI
     */
    private RelativeLayout mWeixinLayout;
    private RelativeLayout mWeixinMomentLayout;
    private RelativeLayout mQQLayout;
    private RelativeLayout mQZoneLayout;

    /**
     * share relative
     */
    private int mShareType; //指定分享类型
    private String mShareTitle; //指定分享内容标题
    private String mShareText; //指定分享内容文本
    private String mSharePhoto; //指定分享本地图片
    private String mShareTileUrl;
    private String mShareSiteUrl;
    private String mShareSite;
    private String mUrl;
    private String mResourceUrl;

    private boolean isShowDownload;

    public ShareDialog(Context context, boolean isShowDownload) {
        super(context, R.style.SheetDialogStyle);
        mContext = context;
        dm = mContext.getResources().getDisplayMetrics();
        this.isShowDownload = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_share_layout);
        initView();
    }

    private void initView() {
        /*
         * 通过获取到dialog的window来控制dialog的宽高及位置
         */
        Window dialogWindow = getWindow();
        if (dialogWindow == null) return;
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = dm.widthPixels;
        dialogWindow.setAttributes(lp);
        dialogWindow.setBackgroundDrawableResource(android.R.color.transparent);

        mWeixinLayout = findViewById(R.id.weixin_layout);
        mWeixinMomentLayout = findViewById(R.id.moment_layout);
        mQQLayout = findViewById(R.id.qq_layout);
        mQZoneLayout = findViewById(R.id.qzone_layout);

        mWeixinLayout.setOnClickListener(v -> shareData(ShareManager.PlatformType.WeChat));
        mWeixinMomentLayout.setOnClickListener(v -> shareData(ShareManager.PlatformType.WechatMoments));
        mQQLayout.setOnClickListener(v -> shareData(ShareManager.PlatformType.QQ));
        mQZoneLayout.setOnClickListener(v -> shareData(ShareManager.PlatformType.QZone));
    }

    public void setResourceUrl(String resourceUrl) {
        mResourceUrl = resourceUrl;
    }

    public void setShareTitle(String title) {
        mShareTitle = title;
    }

    public void setImagePhoto(String photo) {
        mSharePhoto = photo;
    }

    public void setShareType(int type) {
        mShareType = type;
    }

    public void setShareSite(String site) {
        mShareSite = site;
    }

    public void setShareTitleUrl(String titleUrl) {
        mShareTileUrl = titleUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public void setShareSiteUrl(String siteUrl) {
        mShareSiteUrl = siteUrl;
    }

    public void setShareText(String text) {
        mShareText = text;
    }

    private final ShareManager.PlatformShareListener mListener = new ShareManager.PlatformShareListener() {
        @Override
        public void onComplete(int var2, HashMap<String, Object> var3) {

        }

        @Override
        public void onError(int var2, Throwable var3) {

        }

        @Override
        public void onCancel(int var2) {

        }
    };

    private void shareData(ShareManager.PlatformType platformType) {
        ShareData mData = new ShareData();
        Platform.ShareParams params = new Platform.ShareParams();
        params.setShareType(mShareType);
        params.setTitle(mShareTitle);
        params.setTitleUrl(mShareTileUrl);
        params.setSite(mShareSite);
        params.setSiteUrl(mShareSiteUrl);
        params.setText(mShareText);
        params.setImagePath(mSharePhoto);
        params.setUrl(mUrl);
        mData.mPlatformType = platformType;
        mData.mShareParams = params;
        ShareManager.getInstance().shareData(mData, mListener);
    }

}
