package com.oubowu.exerciseprogram.aigestudiostudy.customview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.oubowu.exerciseprogram.R;
import com.oubowu.exerciseprogram.utils.MeasureUtil;

/**
 * ClassName:
 * Author:oubowu
 * Fuction:
 * CreateDate:2016/1/12 21:10
 * UpdateUser:
 * UpdateDate:
 */
public class WaveView extends View implements Runnable {

    private float mOffset;
    private Paint mPaint;
    private Path mPath;
    private final int mWidth;
    private final int mHeight;
    private int mWaveHeight;

    public WaveView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mWidth = MeasureUtil.getScreenWidth((Activity) getContext());
        mHeight = MeasureUtil.getScreenHeight((Activity) getContext())
                - MeasureUtil.getToolbarHeight(context) - MeasureUtil.getStatusBarHeight(context);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(ContextCompat.getColor(context, R.color.material_deep_orange_400));

        mPath = new Path();

        mWaveHeight = 100;

        mOffset = 0;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPath.moveTo(0, mWaveHeight);
        mPath.cubicTo(
                mWidth / 4,
                mWaveHeight + mHeight / 8.0f - mOffset,
                mWidth * 3.0f / 4,
                mWaveHeight - mHeight / 8.0f + mOffset,
                mWidth,
                mWaveHeight);
        mPath.lineTo(mWidth, mHeight);
        mPath.lineTo(0, mHeight);
        mPath.close();
        canvas.drawPath(mPath, mPaint);
        mPath.reset();
    }

    private boolean mStartWave = true;

    public void stopWave() {
        mStartWave = false;
    }

    public void startWave() {
        mStartWave = true;
    }

    private boolean mAdd = true;

    @Override
    public void run() {
        while (mStartWave) {

            if (mOffset < mHeight / 4.0f && mAdd) {
                mOffset += 10;
            } else {
                mOffset -= 10;
                mAdd = false;
            }

            if (mOffset == 0) {
                mAdd = true;
            }

            mWaveHeight += 15;
            if (mWaveHeight > mHeight) {
                mWaveHeight = 0;
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            postInvalidate();
        }
    }
}
