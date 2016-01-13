package com.oubowu.exerciseprogram.aigestudiostudy.customview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.oubowu.exerciseprogram.R;
import com.oubowu.exerciseprogram.utils.MeasureUtil;

/**
 * 类名： CustomView
 * 作者: oubowu
 * 时间： 2016/1/6 13:43
 * 功能：
 * svn版本号:$$Rev$$
 * 更新时间:$$Date$$
 * 更新人:$$Author$$
 * 更新描述:
 */
public class LightingColorCustomView extends View implements View.OnClickListener, View.OnTouchListener {

    private Bitmap mBitmap;
    private Rect appAreaRect;
    private Paint mTextPaint;

    public LightingColorCustomView(Context context) {
        super(context);
        initPaint();
    }

    public LightingColorCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public LightingColorCustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private Paint mPaint;
    private int mRadius;

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

       /*
        * 设置画笔样式为描边，圆环嘛……当然不能填充不然就么意思了
        *
        * 画笔样式分三种：
        * 1.Paint.Style.STROKE：描边
        * 2.Paint.Style.FILL_AND_STROKE：描边并填充
        * 3.Paint.Style.FILL：填充
        */
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        // 设置画笔颜色为自定义颜色
        mPaint.setColor(Color.argb(255, 255, 128, 103));

       /*
        * 设置描边的粗细，单位：像素px
       * 注意：当setStrokeWidth(0)的时候描边宽度并不为0而是只占一个像素
        */
        mPaint.setStrokeWidth(10);

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.mipmap.ic_baoqiang, options);

        mBitmap = MeasureUtil.decodeSampledBitmapFromResource(getResources(),
                R.mipmap.ic_baoqiang,
                MeasureUtil.getScreenWidth((Activity) getContext()),
                MeasureUtil.getScreenWidth((Activity) getContext()) * options.outHeight / options.outWidth);

//        LightingColorFilter
//        顾名思义光照颜色过滤，这肯定是跟光照是有关的了~~该类有且只有一个构造方法：
//        LightingColorFilter (int mul, int add)
//        这个方法非常非常地简单！mul全称是colorMultiply意为色彩倍增，而add全称是colorAdd意为色彩添加，这两个值都是16进制的色彩值0xAARRGGBB。
        // 设置颜色过滤(去掉绿色)
//        mPaint.setColorFilter(new LightingColorFilter(0xFFFF00FF, 0x00000000));

        setOnClickListener(this);

        setOnTouchListener(this);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(30);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 画圆环
        if (appAreaRect == null) return;
        canvas.drawBitmap(mBitmap, (appAreaRect.width() - mBitmap.getWidth()) / 2,
                (appAreaRect.height() - MeasureUtil.getToolbarHeight(getContext()) - mBitmap.getHeight()) / 2,
                mPaint);
        canvas.drawText("使用LightingColorFilter点击图片变色", (appAreaRect.width() - mBitmap.getWidth()) / 2,
                (appAreaRect.height() - MeasureUtil.getToolbarHeight(getContext()) - mBitmap.getHeight()) / 2 - 30 * 2,
                mTextPaint);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus) {
            appAreaRect = MeasureUtil.getAppAreaRect((Activity) getContext());
            invalidate();
        }
    }

    @Override
    public void onClick(View v) {
        if (x >= (appAreaRect.width() - mBitmap.getWidth()) / 2
                && x <= mBitmap.getWidth() + (appAreaRect.width() - mBitmap.getWidth()) / 2
                && y >= (appAreaRect.height() - MeasureUtil.getToolbarHeight(getContext()) - mBitmap.getHeight()) / 2
                && y <= mBitmap.getHeight() + (appAreaRect.height() - MeasureUtil.getToolbarHeight(getContext()) - mBitmap.getHeight()) / 2) {
//        LightingColorFilter(0xFFFFFFFF, 0x00000000)的时候原图是不会有任何改变的，如果我们想增加红色的值，那么LightingColorFilter(0xFFFFFFFF, 0x00XX0000)就好，其中XX取值为00至FF。
//        那么这个方法有什么存在的意义呢？存在必定合理，这个方法存在一定是有它可用之处的，点击一个图片如何直接改变它的颜色而不是为他多准备另一张点击效果的图片
            if (mPaint.getColorFilter() == null) {
                mPaint.setColorFilter(new LightingColorFilter(0xffffffff, 0x00445566));
            } else {
                mPaint.setColorFilter(null);
            }
            invalidate();
        }

    }

    float x, y;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x = event.getX();
                y = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                x = event.getX();
                y = event.getY();
                break;
        }
        return false;
    }
}
