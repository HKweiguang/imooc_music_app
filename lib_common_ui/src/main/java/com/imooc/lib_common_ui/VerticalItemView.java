package com.imooc.lib_common_ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class VerticalItemView extends RelativeLayout {

    private final Context mContext;

    /*
     * 所有样式属性
     */
    private final int mIconWidth;
    private final int mIconHeight;
    private final Drawable mIcon;

    private int mTipPaddingTop;
    private int mTipPaddingRight;
    private Drawable mTipBg;
    private int mTipTextColor;
    private float mTipTextSize;
    private String mTipText;

    private final float mInfoTextSize;
    private final int mInfoTextColor;
    private final int mInfoTextMarginTop;
    private final String mInfoText;

    /*
     * 所有View
     */
    private ImageView mIconView;
    private TextView mTipView;
    private TextView mInfoView;

    public VerticalItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        @SuppressLint("CustomViewStyleable") TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.VerticalItem);
        mIconWidth = a.getLayoutDimension(R.styleable.VerticalItem_iconWidth, 35);
        mIconHeight = a.getLayoutDimension(R.styleable.VerticalItem_iconHeight, 35);
        mIcon = a.getDrawable(R.styleable.VerticalItem_icon);
        mTipPaddingTop = a.getLayoutDimension(R.styleable.VerticalItem_tipPaddingTop, 2);
        mTipPaddingRight = a.getLayoutDimension(R.styleable.VerticalItem_tipPaddingRight, 2);
        mTipBg = a.getDrawable(R.styleable.VerticalItem_tipBg);
        mTipTextColor = a.getColor(R.styleable.VerticalItem_tipTextColor, 0xffffff);
        mTipTextSize = a.getDimension(R.styleable.VerticalItem_tipTextSize, 12);
        mTipText = a.getString(R.styleable.VerticalItem_tipText);
        mInfoTextSize = a.getDimension(R.styleable.VerticalItem_infoTextSize, 12);
        mInfoTextColor = a.getColor(R.styleable.VerticalItem_infoTextColor, 0x333333);
        mInfoTextMarginTop = a.getLayoutDimension(R.styleable.VerticalItem_infoTextMarginTop, 10);
        mInfoText = a.getString(R.styleable.VerticalItem_infoText);
        a.recycle();

        //居中添加到布局中
        RelativeLayout.LayoutParams params =
                new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(CENTER_IN_PARENT);
        addView(createItemView(), params);
    }

    private View createItemView() {
        RelativeLayout rootLayout = new RelativeLayout(mContext);
        mIconView = new ImageView(mContext);
        mIconView.setImageDrawable(mIcon);
        mIconView.setId(R.id.vertical_image_id);
        RelativeLayout.LayoutParams iconParams = new RelativeLayout.LayoutParams(mIconWidth, mIconHeight);
        iconParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        rootLayout.addView(mIconView, iconParams);

        mInfoView = new TextView(mContext);
        mInfoView.setId(R.id.vertical_text_id);
        mInfoView.setTextColor(mInfoTextColor);
        mInfoView.getPaint().setTextSize(mInfoTextSize);
        mInfoView.setText(mInfoText);
        RelativeLayout.LayoutParams textParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        textParams.setMargins(0, mInfoTextMarginTop, 0, 0);
        textParams.addRule(BELOW, R.id.vertical_image_id);
        textParams.addRule(CENTER_HORIZONTAL);
        rootLayout.addView(mInfoView, textParams);

        return rootLayout;
    }
}
