package com.oubowu.exerciseprogram.viewdraghelper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.socks.library.KLog;

/**
 * ClassName:
 * Author:oubowu
 * Fuction:
 * CreateDate:2015/12/3 22:47
 * UpdateUser:
 * UpdateDate:
 */
public class VDLayout extends RelativeLayout {

    ViewDragHelper dragHelper;
    View view;
    private int width;
    private int height;
    private PointF pointF;
    private Paint paint;

    public VDLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        pointF = new PointF();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        paint.setTextSize(25);
        dragHelper = ViewDragHelper.create(this, 1f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return child == view;
            }

            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                super.onViewPositionChanged(changedView, left, top, dx, dy);
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                KLog.e(left + "   " + dx);
                left = left > getWidth() - width - getPaddingLeft() ? getWidth() - width - getPaddingLeft() : left;
                left = left < getPaddingLeft() ? getPaddingLeft() : left;
                return left;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                top = top > getHeight() - height - getPaddingTop() ? getHeight() - height - getPaddingTop() : top;
                top = top < getPaddingBottom() ? getPaddingBottom() : top;
                return top;
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                if (releasedChild == view) {
                    dragHelper.settleCapturedViewAt((int) pointF.x, (int) pointF.y);
                    invalidate();
                }
            }

            /*@Override
            public void onEdgeTouched(int edgeFlags, int pointerId) {
                dragHelper.captureChildView(view, pointerId);
            }*/

            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
                // 边缘拖动时回调
                dragHelper.captureChildView(view, pointerId);
            }

            // 如果子View不消耗事件，那么整个手势（DOWN-MOVE*-UP）都是直接进入onTouchEvent，在onTouchEvent的DOWN的时候就确定了captureView。
            // 如果消耗事件，那么就会先走onInterceptTouchEvent方法，判断是否可以捕获，而在判断的过程中会去判断另外两个回调的方法：
            // getViewHorizontalDragRange和getViewVerticalDragRange，只有这两个方法返回大于0的值才能正常的捕获。
            @Override
            public int getViewHorizontalDragRange(View child) {
                return getMeasuredWidth() - view.getMeasuredWidth();
            }

            @Override
            public int getViewVerticalDragRange(View child) {
                return getMeasuredHeight() - view.getMeasuredHeight();
            }
        });
        dragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        view = getChildAt(0);
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                width = view.getWidth();
                height = view.getHeight();
                pointF.x = view.getX();
                pointF.y = view.getY();
                view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "点击了View", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return dragHelper.shouldInterceptTouchEvent(ev) || super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        dragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        if (dragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText("Padding值为：" + getPaddingLeft(), 0, paint.getTextSize(), paint);
    }
}
