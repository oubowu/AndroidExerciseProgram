package com.oubowu.exerciseprogram.customview.uc;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.oubowu.exerciseprogram.R;

/**
 * ClassName: Author:oubowu Fuction: CreateDate:2016/2/9 14:05 UpdateUser: UpdateDate:
 */
public class UcRerfreshHead extends View {

    private Paint mPaint;
    private int mCubeWidth;
    private int mWidth;
    private int mHeight;
    private Point size = new Point();
    private int mTotalWidth;
    private int mFirstBottomOffset;
    private int mSecondBottomOffset;
    private int mThirdBottomOffset;
    private int mFourthBottomOffset;
    private int mBaseBottomOffset;
    private int mLoadingOffset;
    private ValueAnimator mValueAnimator;

    public UcRerfreshHead(Context context) {
        super(context);
        init();
    }

    public UcRerfreshHead(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setColor(ContextCompat.getColor(getContext(),
                R.color.material_deep_orange_a200));
        mPaint.setStyle(Paint.Style.FILL);

        WindowManager wm = (WindowManager) getContext().getSystemService(
                Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getSize(size);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 考虑padding值
        mWidth = measureSize(widthMeasureSpec, size.x +
                getPaddingLeft() +
                getPaddingRight());

        if (mCubeWidth == 0)
            mCubeWidth = (int) (mWidth * 1.0f / 100);

        if (mTotalWidth == 0)
            mTotalWidth = mCubeWidth * 9;

        if (mBaseBottomOffset == 0)
            mBaseBottomOffset = mFirstBottomOffset = mSecondBottomOffset =
                    mThirdBottomOffset = mFourthBottomOffset = mCubeWidth * 14;

        mHeight = measureSize(heightMeasureSpec, mCubeWidth * 9 +
                getPaddingTop() +
                getPaddingBottom());

        if (mCubeWidth * 9 + getPaddingTop() + getPaddingBottom() < 0) {
            mHeight = 0;
        }

        setMeasuredDimension(mWidth, mHeight);
    }


    // 测量尺寸
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


    @Override
    protected void onDraw(Canvas canvas) {

//        canvas.drawColor(ContextCompat.getColor(getContext(),
//                R.color.material_pink_200));

        mPaint.setColor(ContextCompat.getColor(getContext(),
                R.color.material_green_a700));

        for (int i = 0; i < 2; i++) {
            int offset = i == 1 ? mLoadingOffset : 0;
            canvas.drawRect(
                    (mWidth - mTotalWidth) / 2 + mLoadingOffset * 2,
                    mCubeWidth * 2 + mCubeWidth * 2 * (i + 1) - mFirstBottomOffset - offset + getPaddingTop(),
                    (mWidth - mTotalWidth) / 2 + mCubeWidth + mLoadingOffset * 2,
                    mCubeWidth * 2 + mCubeWidth + mCubeWidth * 2 * (i + 1) - mFirstBottomOffset - offset + getPaddingTop(),
                    mPaint);
        }

        canvas.drawRect(
                (mWidth - mTotalWidth) / 2 + mCubeWidth * 2 + mLoadingOffset,
                mCubeWidth * 2 + mCubeWidth * 2 * 2 - mFirstBottomOffset - mLoadingOffset + getPaddingTop(),
                (mWidth - mTotalWidth) / 2 + mCubeWidth * 2 + mCubeWidth * 5 - mLoadingOffset,
                mCubeWidth * 2 + mCubeWidth * 2 * 2 + mCubeWidth - mFirstBottomOffset - mLoadingOffset + getPaddingTop(),
                mPaint);

        mPaint.setColor(ContextCompat.getColor(getContext(),
                R.color.material_purple_700));

        for (int i = 0; i < 3; i++) {
            int offset = i == 0 ? mLoadingOffset : i == 2 ? -mLoadingOffset : 0;
            canvas.drawRect(
                    (mWidth - mTotalWidth) / 2 + mCubeWidth * 2 * (i + 1) + offset,
                    mCubeWidth * 2 + mCubeWidth * 2 - mSecondBottomOffset + getPaddingTop(),
                    (mWidth - mTotalWidth) / 2 + mCubeWidth + mCubeWidth * 2 * (i + 1) + offset,
                    mCubeWidth * 2 + mCubeWidth + mCubeWidth * 2 - mSecondBottomOffset + getPaddingTop(),
                    mPaint);
        }

        mPaint.setColor(ContextCompat.getColor(getContext(),
                R.color.material_deep_purple_a700));

        for (int i = 0; i < 3; i++) {
            int offset = i == 0 ? mLoadingOffset : i == 2 ? -mLoadingOffset : 0;
            canvas.drawRect(
                    (mWidth - mTotalWidth) / 2 + mCubeWidth * 2 * 4 - mLoadingOffset * 2,
                    mCubeWidth * 2 + mCubeWidth * 2 * i - mThirdBottomOffset + offset + getPaddingTop(),
                    (mWidth - mTotalWidth) / 2 + mCubeWidth + mCubeWidth * 2 * 4 - mLoadingOffset * 2,
                    mCubeWidth * 2 + mCubeWidth + mCubeWidth * 2 * i - mThirdBottomOffset + offset + getPaddingTop(),
                    mPaint);
        }

        mPaint.setColor(ContextCompat.getColor(getContext(),
                R.color.material_deep_orange_a200));

        for (int i = 0; i < 4; i++) {
            int offset = i == 0 ? mLoadingOffset * 2 : i == 1 ? mLoadingOffset : i == 3 ? -mLoadingOffset : 0;
            canvas.drawRect(
                    (mWidth - mTotalWidth) / 2 + mCubeWidth * 2 * i + offset,
                    mCubeWidth * 2 - mFourthBottomOffset + mLoadingOffset + getPaddingTop(),
                    (mWidth - mTotalWidth) / 2 + mCubeWidth + mCubeWidth * 2 * i + offset,
                    mCubeWidth * 2 + mCubeWidth - mFourthBottomOffset + mLoadingOffset + getPaddingTop(),
                    mPaint);
        }
    }

    public void performPull(float ratio) {
        ratio = ratio * 100 - 50;
//        KLog.e(ratio);
        if (ratio > 100) {
            mFirstBottomOffset = mSecondBottomOffset =
                    mThirdBottomOffset = mFourthBottomOffset = 0;
//            KLog.e("ratio > 100");
        } else if (mValueAnimator != null && mValueAnimator.isRunning()) {
            mFirstBottomOffset = mSecondBottomOffset =
                    mThirdBottomOffset = mFourthBottomOffset = 0;
        } else if (ratio <= 25) {
            mFirstBottomOffset = (int) (mBaseBottomOffset * (25 - ratio) * 1.0f /
                    25);
            mSecondBottomOffset = mThirdBottomOffset = mFourthBottomOffset
                    = mBaseBottomOffset;
        } else if (ratio <= 50) {
            mSecondBottomOffset = (int) (mBaseBottomOffset * (50 - ratio) * 1.0f /
                    25);
            mFirstBottomOffset = 0;
            mThirdBottomOffset = mFourthBottomOffset = mBaseBottomOffset;
        } else if (ratio <= 75) {
            mThirdBottomOffset = (int) (mBaseBottomOffset * (75 - ratio) * 1.0f /
                    25);
            mFirstBottomOffset = mSecondBottomOffset = 0;
            mFourthBottomOffset = mBaseBottomOffset;
        } else if (ratio <= 100) {
            mFourthBottomOffset = (int) (mBaseBottomOffset * (100 - ratio) * 1.0f /
                    25);
            mFirstBottomOffset = mSecondBottomOffset = mThirdBottomOffset = 0;
        }
        postInvalidate();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mValueAnimator != null && mValueAnimator.isRunning()) {
            mValueAnimator.removeAllUpdateListeners();
            mValueAnimator.cancel();
            mValueAnimator = null;
        }
    }

    public boolean isLoading() {
        return mValueAnimator != null && mValueAnimator.isRunning();
    }

    public boolean isCombine() {
        return 0 == mFourthBottomOffset;
    }

    public void performLoading() {
        if (mValueAnimator != null && mValueAnimator.isRunning()) {
            return;
        }
        mValueAnimator = new ValueAnimator();
        mValueAnimator.setIntValues(0, mCubeWidth * 4 / 3);
        mValueAnimator.addUpdateListener(
                new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        mLoadingOffset = (int) animation.getAnimatedValue();
                        postInvalidate();
                    }
                });
        mValueAnimator.setDuration(300);
        mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mValueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mValueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        mValueAnimator.start();
    }
}
