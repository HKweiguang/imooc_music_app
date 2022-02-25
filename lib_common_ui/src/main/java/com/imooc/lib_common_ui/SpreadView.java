package com.imooc.lib_common_ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class SpreadView extends View {

    private int radius = 100; //中心圆半径
    private final Paint spreadPaint; //扩散圆paint
    private float centerX;//圆心x
    private float centerY;//圆心y
    private int distance = 3; //每次圆递增间距
    private int maxRadius = 80; //最大圆半径
    private final int delayMilliseconds = 12;//扩散延迟间隔，越大扩散越慢
    private final List<Integer> spreadRadius = new ArrayList<>();//扩散圆层级数，元素为扩散的距离
    private final List<Integer> alphas = new ArrayList<>();//对应每层圆的透明度

    public SpreadView(Context context) {
        this(context, null, 0);
    }

    public SpreadView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SpreadView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SpreadView, defStyleAttr, 0);
        radius = a.getInt(R.styleable.SpreadView_spread_radius, radius);
        maxRadius = a.getInt(R.styleable.SpreadView_spread_max_radius, maxRadius);
        int centerColor = a.getColor(R.styleable.SpreadView_spread_center_color,
                ContextCompat.getColor(context, android.R.color.holo_red_dark));
        int spreadColor = a.getColor(R.styleable.SpreadView_spread_spread_color,
                ContextCompat.getColor(context, R.color.color_F71816));
        distance = a.getInt(R.styleable.SpreadView_spread_distance, distance);
        a.recycle();

        //最开始不透明且扩散距离为0
        alphas.add(255);
        spreadRadius.add(0);
        spreadPaint = new Paint();
        spreadPaint.setAntiAlias(true);
        spreadPaint.setStyle(Paint.Style.STROKE);
        spreadPaint.setStrokeWidth(3f);
        spreadPaint.setAlpha(255);
        spreadPaint.setColor(spreadColor);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //圆心位置
        centerX = w / 2f;
        centerY = h / 2f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 绘制所有扩散圆并计算下次绘制参数
        for (int i = 0; i < spreadRadius.size(); i++) {
            int alpha = alphas.get(i);
            if (alpha == 0) {
                alphas.remove(i);
                spreadRadius.remove(i);
                i--;
                continue;
            }

            spreadPaint.setAlpha(alpha);
            int width = spreadRadius.get(i);

            //绘制扩散的圆
            canvas.drawCircle(centerX, centerY, radius + width, spreadPaint);

            //每次扩散圆半径递增，圆透明度递减
            alpha = Math.max((alpha - distance), 0);
            alphas.set(i, alpha);
            spreadRadius.set(i, width + distance);
        }

        // 当上一个扩散圆达到扩散半径时添加新扩散圆
        if (spreadRadius.get(spreadRadius.size() - 1) > maxRadius) {
            spreadRadius.add(0);
            alphas.add(255);
        }

        //超过8个扩散圆，删除最先绘制的圆，即最外层的圆
        if (spreadRadius.size() >= 8) {
            alphas.remove(0);
            spreadRadius.remove(0);
        }

        //延迟更新，达到扩散视觉差效果
        postInvalidateDelayed(delayMilliseconds);
    }
}

