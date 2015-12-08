package com.oubowu.exerciseprogram.tailview;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 类名： TailTextView
 * 作者: oubowu
 * 时间： 2015/12/8 9:56
 * 功能：
 * svn版本号:$$Rev$$
 * 更新时间:$$Date$$
 * 更新人:$$Author$$
 * 更新描述:
 */
public class TailTextView extends TextView {

    private int mRightWidth;

    public TailTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TailTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TailTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    private Paint mPaint;

    public String getTailText() {
        return mTailText;
    }

    public void setTailText(String tailText) {
        this.mTailText = tailText;
        invalidate();
    }

    private String mTailText = "";

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(getTextSize());
        mPaint.setColor(getCurrentTextColor() & 0x00111111 | 0x88000000);
        final Drawable[] drawables = getCompoundDrawables();
        final int drawablePadding = getCompoundDrawablePadding();

        mRightWidth = drawablePadding + getPaddingRight();
        if (drawables[2] != null) {
            mRightWidth += drawables[2].getIntrinsicWidth();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText(mTailText, getMeasuredWidth() - mPaint.measureText(mTailText) - mRightWidth * 4,
                (getMeasuredHeight() + getPaddingTop() + getPaddingBottom()) / 2, mPaint);
    }
}
