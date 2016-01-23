package com.oubowu.exerciseprogram.customview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.oubowu.exerciseprogram.R;
import com.oubowu.exerciseprogram.utils.MeasureUtil;
import com.socks.library.KLog;

/**
 * 类名： ThreePointLoadingView
 * 作者: oubowu
 * 时间： 2016/1/23 9:25
 * 功能：
 * svn版本号:$$Rev$$
 * 更新时间:$$Date$$
 * 更新人:$$Author$$
 * 更新描述:
 */
public class ThreePointLoadingView extends View {

    private Paint mBallPaint;
    private int mWidth;
    private int mHeight;
    private float mSpace;
    private float mBallRadius;
    private float mTotalLength;
    private float mABallX;
    private float mABallY;
    private float mBBallX;
    private float mBBallY;
    private float mCBallX;
    private float mCBallY;
    private float mMoveLength;

    // A圆做二阶贝塞尔曲线的起点、控制点、终点
    private PointF mABallP0;
    private PointF mABallP1;
    private PointF mABallP2;
    private float mABallazierX;
    private float mABallazierY;
    private ValueAnimator mAnimator;

    private int mABallAlpha = 255;
    private int mBBallAlpha = (int) (255 * 0.8);
    private int mCBallAlpha = (int) (255 * 0.6);


    public ThreePointLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {

        mBallPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);

        mBallPaint.setColor(ContextCompat.getColor(getContext(), R.color.material_deep_orange_a200));

        mBallPaint.setStyle(Paint.Style.FILL);

        mABallP0 = new PointF();
        mABallP1 = new PointF();
        mABallP2 = new PointF();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        mWidth = measureSize(widthMeasureSpec, MeasureUtil.dip2px(getContext(), 180)) + getPaddingLeft() + getPaddingRight();
        mHeight = measureSize(heightMeasureSpec, MeasureUtil.dip2px(getContext(), 180)) + getPaddingTop() + getPaddingBottom();

        setMeasuredDimension(mWidth, mHeight);

        mSpace = mWidth * 1.0f / 20;

        mBallRadius = mWidth * 1.0f / 50;

        mTotalLength = mBallRadius * 6 + mSpace * 2;

        mABallazierX = mABallX = (mWidth - mTotalLength) / 2 + mBallRadius;
        mABallazierY = mABallY = mHeight / 2;

        mBBallX = (mWidth - mTotalLength) / 2 + mBallRadius * 3 + mSpace;
        mBBallY = mHeight / 2;

        mCBallX = (mWidth - mTotalLength) / 2 + mBallRadius * 5 + mSpace * 2;
        mCBallY = mHeight / 2;

        mMoveLength = mSpace + mBallRadius * 2;

        mABallP0.set(mABallX, mABallY);
        mABallP1.set(mABallX + mMoveLength / 2, mABallY - mMoveLength / 2);
        mABallP2.set(mBBallX, mBBallY);

    }

    private int measureSize(int measureSpec, int defaultSize) {

        final int mode = MeasureSpec.getMode(measureSpec);
        final int size = MeasureSpec.getSize(measureSpec);

        if (mode == MeasureSpec.EXACTLY) {
            return size;
        } else if (mode == MeasureSpec.AT_MOST) {
            return Math.min(size, defaultSize);
        }

        return size;
    }


    private float offsetX = 0;

    @Override
    protected void onDraw(Canvas canvas) {

        if (offsetX <= mMoveLength) {

            final double offsetY = Math.sqrt(mMoveLength / 2 * mMoveLength / 2 - (mMoveLength / 2 - offsetX) * (mMoveLength / 2 - offsetX));

            mBallPaint.setAlpha(mBBallAlpha);
            canvas.drawCircle(mBBallX - offsetX,
                    (float) (mBBallY + offsetY),
                    mBallRadius,
                    mBallPaint);

            mBallPaint.setAlpha(mCBallAlpha);
            canvas.drawCircle(mCBallX - offsetX,
                    (float) (mCBallY - offsetY),
                    mBallRadius,
                    mBallPaint);

        } else {

            final double offsetY = Math.sqrt(mMoveLength / 2 * mMoveLength / 2 - (offsetX - mMoveLength / 2) * (offsetX - mMoveLength / 2));

            mBallPaint.setAlpha(mBBallAlpha);
            canvas.drawCircle(mBBallX - offsetX,
                    (float) (mBBallY + offsetY),
                    mBallRadius,
                    mBallPaint);

            mBallPaint.setAlpha(mCBallAlpha);
            canvas.drawCircle(mCBallX - offsetX,
                    (float) (mCBallY - offsetY),
                    mBallRadius,
                    mBallPaint);

        }

        mBallPaint.setAlpha(mABallAlpha);
        canvas.drawCircle(mABallazierX, mABallazierY, mBallRadius, mBallPaint);


    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mAnimator.cancel();
    }

    public void startLoading() {

        mAnimator = ValueAnimator.ofFloat(0, mMoveLength);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                offsetX = (float) animation.getAnimatedValue();
                postInvalidate();

                float fraction = animation.getAnimatedFraction();

                mBBallAlpha = (int) (255 * 0.8 + 255 * fraction * 0.2);
                mCBallAlpha = (int) (255 * 0.6 + 255 * fraction * 0.2);

                mABallAlpha = (int) (255 - 255 * fraction * 0.4);
                KLog.e(mABallAlpha);

                if (fraction < 0.5) {

                    fraction *= 2;

                    mABallP0.set(mABallX, mABallY);
                    mABallP1.set(mABallX + mMoveLength / 2, mABallY - mMoveLength / 2);
                    mABallP2.set(mBBallX, mBBallY);

                    mABallazierX = getBazierValue(fraction, mABallP0.x, mABallP1.x, mABallP2.x);
                    mABallazierY = getBazierValue(fraction, mABallP0.y, mABallP1.y, mABallP2.y);
                } else {

                    fraction -= 0.5;
                    fraction *= 2;

                    mABallP0.set(mBBallX, mBBallY);
                    mABallP1.set(mBBallX + mMoveLength / 2, mBBallY + mMoveLength / 2);
                    mABallP2.set(mCBallX, mCBallY);

                    mABallazierX = getBazierValue(fraction, mABallP0.x, mABallP1.x, mABallP2.x);
                    mABallazierY = getBazierValue(fraction, mABallP0.y, mABallP1.y, mABallP2.y);
                }
            }
        });
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mAnimator.setDuration(1000);
        mAnimator.setStartDelay(500);
        mAnimator.start();

    }

    private float getBazierValue(float fraction, float p0, float p1, float p2) {
        return (1 - fraction) * (1 - fraction) * p0 + 2 * fraction * (1 - fraction) * p1 + fraction * fraction * p2;
    }

}
