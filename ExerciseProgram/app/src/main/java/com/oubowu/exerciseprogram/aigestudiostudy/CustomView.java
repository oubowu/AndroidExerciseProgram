package com.oubowu.exerciseprogram.aigestudiostudy;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

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
public class CustomView extends View implements Runnable {
    public void ondestroy() {
        this.mIsRun = false;
    }

    private boolean mIsRun = true;
    private int mCenterX;

    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
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
        mPaint.setStyle(Paint.Style.STROKE);

        // 设置画笔颜色为浅灰色
        mPaint.setColor(Color.LTGRAY);

       /*
        * 设置描边的粗细，单位：像素px
       * 注意：当setStrokeWidth(0)的时候描边宽度并不为0而是只占一个像素
        */
        mPaint.setStrokeWidth(10);

        mCenterX = MeasureUtil.getScreenWidth((Activity) getContext()) / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 画圆环
        canvas.drawCircle(mCenterX,
                MeasureUtil.getScreenHeight((Activity) getContext()) / 2,
                mRadius,
                mPaint);
    }

    @Override
    public void run() {
        while (mIsRun) {
            if (mRadius <= mCenterX) {
                mRadius += 10;
                postInvalidate();
            } else {
                mRadius = 0;
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
