package com.oubowu.exerciseprogram.toucheventdelivery;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

import com.socks.library.KLog;

/**
 * ClassName:
 * Author:oubowu
 * Fuction:
 * CreateDate:2015/12/9 0:53
 * UpdateUser:
 * UpdateDate:
 */
public class TouchEventTextView extends TextView {

    public boolean isSuccess() {
        return mIsSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.mIsSuccess = isSuccess;
    }

    private boolean mIsSuccess;

    public TouchEventTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        KLog.e("【农民】任务<" + Util.actionToString(ev.getAction()) + "> : 需要分派，我下面没人了，怎么办？自己干吧");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        KLog.e("【农民】任务<" + Util.actionToString(ev.getAction()) + "> : 自己动手，埋头苦干。能解决？" + mIsSuccess);
        return mIsSuccess;
    }
}
