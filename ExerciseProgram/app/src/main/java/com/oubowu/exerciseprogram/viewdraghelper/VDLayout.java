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

import tyrantgit.explosionfield.ExplosionField;

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

    View normalView;
    private int normalWidth;
    private int normalHeight;
    private PointF normalPointF;

    View explodeView;
    private int explodeWidth;
    private int explodeHeight;

    private Paint paint;
    private ExplosionField explosionField;

    public VDLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        normalPointF = new PointF();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        paint.setTextSize(25);

        dragHelper = ViewDragHelper.create(this, 1f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return child == normalView || child == explodeView;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                if (child == normalView) {
                    KLog.e(left + "   " + dx);
                    left = left > getWidth() - normalWidth - getPaddingLeft() ? getWidth() - normalWidth - getPaddingLeft() : left;
                    left = left < getPaddingLeft() ? getPaddingLeft() : left;
                } else {
                    left = left > getWidth() - explodeWidth - getPaddingLeft() ? getWidth() - explodeWidth - getPaddingLeft() : left;
                    left = left < getPaddingLeft() ? getPaddingLeft() : left;
                }
                return left;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                if (child == normalView) {
                    top = top > getHeight() - normalHeight - getPaddingTop() ? getHeight() - normalHeight - getPaddingTop() : top;
                    top = top < getPaddingBottom() ? getPaddingBottom() : top;
                } else {
                    top = top > getHeight() - explodeHeight - getPaddingTop() ? getHeight() - explodeHeight - getPaddingTop() : top;
                    top = top < getPaddingBottom() ? getPaddingBottom() : top;
                }
                return top;
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {

                if (releasedChild == normalView) {
                    dragHelper.settleCapturedViewAt((int) normalPointF.x, (int) normalPointF.y);
                    invalidate();
                } else {
                    if (explosionField != null && releasedChild.getX() >= normalPointF.x && releasedChild.getX() <= normalPointF.x + normalWidth
                            && releasedChild.getY() >= normalPointF.y && releasedChild.getY() <= normalPointF.y + normalHeight) {
                        explosionField.explode(releasedChild);
                    }
                }
            }

            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
                // 边缘拖动时回调
                dragHelper.captureChildView(normalView, pointerId);
            }

            // 如果子View不消耗事件，那么整个手势（DOWN-MOVE*-UP）都是直接进入onTouchEvent，在onTouchEvent的DOWN的时候就确定了captureView。
            // 如果消耗事件，那么就会先走onInterceptTouchEvent方法，判断是否可以捕获，而在判断的过程中会去判断另外两个回调的方法：
            // getViewHorizontalDragRange和getViewVerticalDragRange，只有这两个方法返回大于0的值才能正常的捕获。
            @Override
            public int getViewHorizontalDragRange(View child) {
                if (child == normalView) {
                    return getMeasuredWidth() - normalView.getMeasuredWidth();
                } else {
                    return getMeasuredWidth() - explodeView.getMeasuredWidth();
                }
            }

            @Override
            public int getViewVerticalDragRange(View child) {
                if (child == normalView) {
                    return getMeasuredHeight() - normalView.getMeasuredHeight();
                } else {
                    return getMeasuredWidth() - explodeView.getMeasuredHeight();
                }
            }
        });

        dragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        normalView = getChildAt(0);
        normalView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                normalWidth = normalView.getWidth();
                normalHeight = normalView.getHeight();
                normalPointF.x = normalView.getX();
                normalPointF.y = normalView.getY();
                normalView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
        normalView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "点击了View", Toast.LENGTH_SHORT).show();
            }
        });

        explodeView = getChildAt(1);
        explodeView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                explodeWidth = explodeView.getWidth();
                explodeHeight = explodeView.getHeight();
                explodeView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
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

    public void setExplosionField(ExplosionField field) {
        this.explosionField = field;
    }
}
