package com.oubowu.exerciseprogram.aigestudiostudy;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.oubowu.exerciseprogram.R;
import com.oubowu.exerciseprogram.utils.MeasureUtil;

/**
 * 类名： IconView
 * 作者: oubowu
 * 时间： 2016/1/20 14:35
 * 功能：
 * svn版本号:$$Rev$$
 * 更新时间:$$Date$$
 * 更新人:$$Author$$
 * 更新描述:
 */
public class IconView extends View {

    private Bitmap mBitmap;// 位图
    private TextPaint mPaint;// 绘制文本的画笔
    private String mStr;// 绘制的文本

    private float mTextSize;// 画笔的文本尺寸

    /**
     * 宽高枚举类
     */
    private enum Ratio {
        WIDTH, HEIGHT
    }

    public IconView(Context context, AttributeSet attrs) {
        super(context, attrs);

        calArgs(context);

        init();

    }

    /**
     * 参数计算
     *
     * @param context 上下文环境引用
     */
    private void calArgs(Context context) {
        // 获取屏幕宽
        int sreenW = MeasureUtil.getScreenSize((Activity) context)[0];

        // 计算文本尺寸
        mTextSize = sreenW * 1.0f / 10F;
    }

    private void init() {
        /*
         * 获取Bitmap
         */
        if (null == mBitmap) {
            mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        }

        /*
         * 为mStr赋值
         */
        if (null == mStr || mStr.trim().length() == 0) {
            mStr = "AigeStudio";
        }

        /*
         * 初始化画笔并设置参数
         */
        mPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.LINEAR_TEXT_FLAG);
        mPaint.setColor(Color.LTGRAY);
        mPaint.setTextSize(mTextSize);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTypeface(Typeface.DEFAULT_BOLD);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 设置测量后的尺寸
        setMeasuredDimension(getMeasureSize(widthMeasureSpec, Ratio.WIDTH), getMeasureSize(heightMeasureSpec, Ratio.HEIGHT));
    }

    /**
     * 获取测量后的尺寸
     *
     * @param measureSpec 测量规格
     * @param ratio       宽高标识
     * @return 宽或高的测量值
     */
    private int getMeasureSize(int measureSpec, Ratio ratio) {
        // 声明临时变量保存测量值
        int result = 0;

        /*
         * 获取mode和size
         */
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        /*
         * 判断mode的具体值
         */
        switch (mode) {
            case MeasureSpec.EXACTLY:// EXACTLY时直接赋值
                result = size;
                break;
            default:// 默认情况下将UNSPECIFIED和AT_MOST一并处理
                if (ratio == Ratio.WIDTH) {
                    float textWidth = mPaint.measureText(mStr);
                    result = ((int) (textWidth >= mBitmap.getWidth() ? textWidth : mBitmap.getWidth())) + getPaddingLeft() + getPaddingRight();
                } else if (ratio == Ratio.HEIGHT) {
                    result = ((int) ((mPaint.descent() - mPaint.ascent()) * 2 + mBitmap.getHeight())) + getPaddingTop() + getPaddingBottom();
                }

            /*
             * AT_MOST时判断size和result的大小取小值
             */
                if (mode == MeasureSpec.AT_MOST) {
                    result = Math.min(result, size);
                }
                break;
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        /*
         * 绘制
         * 参数就不做单独处理了因为只会Draw一次不会频繁调用
         */
        canvas.drawBitmap(mBitmap, getWidth() / 2 - mBitmap.getWidth() / 2, getHeight() / 2 - mBitmap.getHeight() / 2, null);
        canvas.drawText(mStr, getWidth() / 2, mBitmap.getHeight() + getHeight() / 2 - mBitmap.getHeight() / 2 - mPaint.ascent(), mPaint);
    }

}
