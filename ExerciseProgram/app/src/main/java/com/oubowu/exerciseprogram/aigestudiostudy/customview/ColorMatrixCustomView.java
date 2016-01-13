package com.oubowu.exerciseprogram.aigestudiostudy.customview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.oubowu.exerciseprogram.R;
import com.oubowu.exerciseprogram.utils.MeasureUtil;
import com.socks.library.KLog;

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
public class ColorMatrixCustomView extends View {

    private int mCenterX;
    private Bitmap mBitmap;
    private Rect appAreaRect;

    public ColorMatrixCustomView(Context context) {
        super(context);
        initPaint();
    }

    public ColorMatrixCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public ColorMatrixCustomView(Context context, AttributeSet attrs, int defStyleAttr) {
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

        mCenterX = MeasureUtil.getScreenWidth((Activity) getContext()) / 2;

        mRadius = mCenterX / 2;

//        中文直译为色彩矩阵颜色过滤器，要明白这玩意你得先了解什么是色彩矩阵。
//        在Android中图片是以RGBA像素点的形式加载到内存中的，
//        修改这些像素信息需要一个叫做ColorMatrix类的支持，其定义了一个4x5的float[]类型的矩阵：

//        ColorMatrix colorMatrix = new ColorMatrix(new float[]{
//                0.5f, 0, 0, 0, 0,
//                0, 0.5f, 0, 0, 0,
//                0, 0, 0.5f, 0, 0,
//                0, 0, 0, 0.5f, 0
//        });
//
//        mPaint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.mipmap.ic_baoqiang, options);

        mBitmap = MeasureUtil.decodeSampledBitmapFromResource(getResources(),
                R.mipmap.ic_baoqiang,
                MeasureUtil.getScreenWidth((Activity) getContext()),
                MeasureUtil.getScreenWidth((Activity) getContext()) * options.outHeight / options.outWidth);
    }

    /**
     * 设置颜色矩阵的argb值
     * @param a
     * @param r
     * @param g
     * @param b
     */
    public void setArgb(float a, float r, float g, float b) {
        ColorMatrix colorMatrix = new ColorMatrix(new float[]{
                r, 0, 0, 0, 0,
                0, g, 0, 0, 0,
                0, 0, b, 0, 0,
                0, 0, 0, a, 0
        });

        mPaint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (appAreaRect == null) return;
        canvas.drawBitmap(mBitmap, appAreaRect.width() / 2 - mBitmap.getWidth() / 2, appAreaRect.height() / 2 - mBitmap.getHeight() / 2, mPaint);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus) {
            appAreaRect = MeasureUtil.getAppAreaRect((Activity) getContext());
            final int statusBarHeight = MeasureUtil.getStatusBarHeight((Activity) getContext());
            final Rect contentViewRect = MeasureUtil.getContentViewRect((Activity) getContext());
            KLog.e("App区域(不包含状态栏)：" + appAreaRect.width() + "," + appAreaRect.height()
                    + ";状态栏高度：" + statusBarHeight + ";ContentView(包含ToolBar)的高度：" + contentViewRect.height());
            invalidate();
        }
    }
}
