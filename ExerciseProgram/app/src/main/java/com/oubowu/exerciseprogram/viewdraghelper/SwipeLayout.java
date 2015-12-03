package com.oubowu.exerciseprogram.viewdraghelper;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

/**
 * 类名： SwipeLayout
 * 作者: oubowu
 * 时间： 2015/12/3 16:16
 * 功能：
 * svn版本号:$$Rev$$
 * 更新时间:$$Date$$
 * 更新人:$$Author$$
 * 更新描述:
 */
public class SwipeLayout extends FrameLayout {

    /*SwipeLayout包含两个子view，第一个子view是内容区域，第二个子view是操作区域。滑动效果的控制，主要就是通过检测SwipeLayout的touch事件来实现，
    这里我不想自己去通过监听touch事件来实现滑动效果，那是一个很繁琐的过程。
    Android support库里其实已经提供了一个很好的工具类来帮我们做这件事情ViewDragHelper。
    如果你看过Android原生的DrawerLayout的代码，就会发现DrawerLayout的滑动效果也是通过ViewDragHelper类实现的。*/

    private View contentView;
    private View actionView;
    private final double AUTO_OPEN_SPEED_LIMIT = 800.0;
    private ViewDragHelper dragHelper;
    // 总的滑动距离
    private int dragDistance;
    // 当前水平滑动的距离
    private int draggedX;

    public SwipeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        dragHelper = ViewDragHelper.create(this, 1.0f, new MyCallBack());
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        contentView = getChildAt(0);
        actionView = getChildAt(1);
//        actionView.setVisibility(INVISIBLE);
        actionView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                dragDistance = actionView.getWidth();
                ObjectAnimator.ofFloat(actionView, "translationX", 0, dragDistance).setDuration(0).start();
                actionView.getViewTreeObserver()
                        .removeGlobalOnLayoutListener(this);
            }
        });

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 整个拖动的距离为隐藏布局的宽度
//        dragDistance = actionView.getMeasuredWidth();
//        KLog.e("onMeasure: " + dragDistance);
    }

    /*通过ViewDragHelper.Callback来监听以下几种事件：
      1.拖动的状态改变
      2.被拖动的view的位置改变
      3.被拖动的view被放开的时间和位置*/
    private class MyCallBack extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            // 限定contentView和actionView是可以拖动的
//            KLog.e(child == contentView || child == actionView);
            return child == contentView || child == actionView;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            // onViewPositionChanged在被拖动的view位置改变的时候调用
            // left拖动的view相对父布局左边的距离，top拖动的view相对父布局上边的距离
            draggedX = left;
            if (changedView == contentView) {
                // 内容布局在拖动的时候隐藏布局跟随拖动
                actionView.offsetLeftAndRight(dx);
            } else {
                // 隐藏布局在拖动的时候内容布局跟随拖动
                contentView.offsetLeftAndRight(dx);
            }
//            if (actionView.getVisibility() == INVISIBLE) {
//                actionView.setVisibility(VISIBLE);
//            }
            invalidate();
        }

        // clampViewPositionHorizontal用来限制view在x轴上拖动，要实现水平拖动效果必须要实现这个方法，
        // 我们这里因为仅仅需要实现水平拖动，所以没有实现clampViewPositionVertical方法。
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if (child == contentView) {
                // 根布局的paddingleft
                final int leftBound = getPaddingLeft();
                //
                final int minLeftBound = -leftBound - dragDistance;
                final int newLeft = Math.min(Math.max(minLeftBound, left), 0);
                return newLeft;
            } else {
                final int minLeftBound = getPaddingLeft() + contentView.getMeasuredWidth() - dragDistance;
                final int maxLeftBound = getPaddingLeft() + contentView.getMeasuredWidth() + getPaddingRight();
                final int newLeft = Math.min(Math.max(left, minLeftBound), maxLeftBound);
                return newLeft;
            }
        }

        // getViewHorizontalDragRange方法用来限制view可以拖动的范围
        @Override
        public int getViewHorizontalDragRange(View child) {
            return dragDistance;
        }

        // onViewReleased方法中，根据滑动手势的速度以及滑动的距离来确定是否显示actionView。
        // smoothSlideViewTo方法用来在滑动手势之后实现惯性滑动效果
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            boolean settleToOpen = false;
            if (xvel > AUTO_OPEN_SPEED_LIMIT) {
                settleToOpen = false;
            } else if (xvel < -AUTO_OPEN_SPEED_LIMIT) {
                settleToOpen = true;
            } else if (draggedX <= -dragDistance / 2) {
                settleToOpen = true;
            } else if (draggedX > -dragDistance / 2) {
                settleToOpen = false;
            }
            final int settleDestX = settleToOpen ? -dragDistance : 0;
            dragHelper.smoothSlideViewTo(contentView, settleDestX, 0);
            ViewCompat.postInvalidateOnAnimation(SwipeLayout.this);
        }
    }

    public void close() {
        dragHelper.smoothSlideViewTo(contentView, 0, 0);
        ViewCompat.postInvalidateOnAnimation(SwipeLayout.this);
    }

    public void open() {
        dragHelper.smoothSlideViewTo(contentView, -dragDistance, 0);
        ViewCompat.postInvalidateOnAnimation(SwipeLayout.this);
    }

    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        // 通过使用mDragger.shouldInterceptTouchEvent(event)来决定我们是否应该拦截当前的事件。
        // onTouchEvent中通过mDragger.processTouchEvent(event)处理事件
        if (dragHelper.shouldInterceptTouchEvent(event)) {
            return true;
        }
        return super.onInterceptHoverEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        dragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (dragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }
}
