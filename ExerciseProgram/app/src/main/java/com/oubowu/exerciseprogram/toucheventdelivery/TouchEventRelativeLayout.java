package com.oubowu.exerciseprogram.toucheventdelivery;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.socks.library.KLog;

/**
 * ClassName:
 * Author:oubowu
 * Fuction:
 * CreateDate:2015/12/9 0:53
 * UpdateUser:
 * UpdateDate:
 */
public class TouchEventRelativeLayout extends RelativeLayout {
    private Paint mPaint;

    public boolean isIntercept() {
        return mIsIntercept;
    }

    public void setIsIntercept(boolean isIntercept) {
        this.mIsIntercept = isIntercept;
    }

    private boolean mIsIntercept;

    public boolean isSuccess() {
        return mIsSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.mIsSuccess = isSuccess;
    }

    private boolean mIsSuccess;

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(sp2px(getContext(), 30));
        mPaint.setColor(Color.BLACK);
    }

    private int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public TouchEventRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText("市长", sp2px(getContext(), 30), sp2px(getContext(), 30), mPaint);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        KLog.e("【市长】任务<" + Util.actionToString(ev.getAction()) + "> : 需要分派");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        KLog.e("【市长】任务<" + Util.actionToString(ev.getAction()) + "> : 拦截吗？" + mIsIntercept);
        return mIsIntercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        KLog.e("【市长】任务<" + Util.actionToString(ev.getAction()) + "> : 农民真没用，下次再也不找你了，我自己来尝试一下。能解决？" + mIsSuccess);
        return mIsSuccess;
    }

}
