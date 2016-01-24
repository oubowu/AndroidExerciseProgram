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

    // 画笔
    private Paint mBallPaint;
    // 宽度
    private int mWidth;
    // 高度
    private int mHeight;
    // 圆之间的距离
    private float mSpace;
    // 圆的半径
    private float mBallRadius;
    // 三个圆合起来的距离(包括间距)
    private float mTotalLength;
    // A圆心的x坐标
    private float mABallX;
    // A圆心的y坐标
    private float mABallY;
    // B圆心的x坐标
    private float mBBallX;
    // B圆心的y坐标
    private float mBBallY;
    // C圆心的x坐标
    private float mCBallX;
    // C圆心的y坐标
    private float mCBallY;

    // 圆心移动的距离
    private float mMoveLength;

    // A圆心做二阶贝塞尔曲线的起点、控制点、终点
    private PointF mABallP0;
    private PointF mABallP1;
    private PointF mABallP2;

    // A圆心贝塞尔曲线运动时的坐标
    private float mABallazierX;
    private float mABallazierY;

    // 值动画
    private ValueAnimator mAnimator;

    // 值动画产生的x方向的偏移量
    private float mOffsetX = 0;
    // 根据mOffsetX算得的y方向的偏移量
    private float mOffsetY;

    // A圆的起始透明度
    private int mABallAlpha = 255;
    // B圆的起始透明度
    private int mBBallAlpha = (int) (255 * 0.8);
    // C圆的起始透明度
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

        // 考虑padding值
        mWidth = measureSize(widthMeasureSpec, MeasureUtil.dip2px(getContext(), 180)) + getPaddingLeft() + getPaddingRight();
        mHeight = measureSize(heightMeasureSpec, MeasureUtil.dip2px(getContext(), 180)) + getPaddingTop() + getPaddingBottom();

        setMeasuredDimension(mWidth, mHeight);

        // 间距为宽度10分之一
        mSpace = mWidth * 1.0f / 20;

        // 半径为宽度50分之一
        mBallRadius = mWidth * 1.0f / 50;

        // 总的长度为三个圆直径加上之间的间距
        mTotalLength = mBallRadius * 6 + mSpace * 2;

        // 两个圆圆心的距离
        mMoveLength = mSpace + mBallRadius * 2;

        // A圆心起始坐标，同时贝塞尔曲线的起始坐标也是这个
        mABallazierX = mABallX = (mWidth - mTotalLength) / 2 + mBallRadius;
        mABallazierY = mABallY = mHeight / 2;

        // A圆心起始点，控制点，终点
        mABallP0.set(mABallX, mABallY);
        mABallP1.set(mABallX + mMoveLength / 2, mABallY - mMoveLength / 2);
        mABallP2.set(mBBallX, mBBallY);

        // B圆心的起始坐标
        mBBallX = (mWidth - mTotalLength) / 2 + mBallRadius * 3 + mSpace;
        mBBallY = mHeight / 2;

        // C圆心的起始坐标
        mCBallX = (mWidth - mTotalLength) / 2 + mBallRadius * 5 + mSpace * 2;
        mCBallY = mHeight / 2;

    }

    @Override
    protected void onDraw(Canvas canvas) {

        // 根据x方向偏移量求出y方向偏移量
        mOffsetY = (float) Math.sqrt(mMoveLength / 2 * mMoveLength / 2 - (mMoveLength / 2 - mOffsetX) * (mMoveLength / 2 - mOffsetX));

        // 绘制B圆
        mBallPaint.setAlpha(mBBallAlpha);
        canvas.drawCircle(mBBallX - mOffsetX,
                (float) (mBBallY + mOffsetY),
                mBallRadius,
                mBallPaint);

        // 绘制C圆
        mBallPaint.setAlpha(mCBallAlpha);
        canvas.drawCircle(mCBallX - mOffsetX,
                (float) (mCBallY - mOffsetY),
                mBallRadius,
                mBallPaint);

        // 绘制A圆
        mBallPaint.setAlpha(mABallAlpha);
        canvas.drawCircle(mABallazierX, mABallazierY, mBallRadius, mBallPaint);

        if (mAnimator == null) {
            // 启动值动画
            startLoading();
        }

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        // 销毁view时取消动画，避免内存泄露
        mAnimator.cancel();
    }

    // 开启值动画
    private void startLoading() {

        // 范围在0到圆心移动的距离，这个是以B圆到A圆位置为基准的
        mAnimator = ValueAnimator.ofFloat(0, mMoveLength);

        // 设置监听
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                // B圆和C圆对应的X的偏移量
                mOffsetX = (float) animation.getAnimatedValue();

                float fraction = animation.getAnimatedFraction();

                // B移动到A，透明度变化255*0.8->255
                mBBallAlpha = (int) (255 * 0.8 + 255 * fraction * 0.2);
                // C移动到B，透明度变化255*0.6->255*0.8
                mCBallAlpha = (int) (255 * 0.6 + 255 * fraction * 0.2);
                // A移动到C，透明度变化255->255*0.6
                mABallAlpha = (int) (255 - 255 * fraction * 0.4);

                // A圆的分段二阶贝塞尔曲线的处理
                if (fraction < 0.5) {
                    // fraction小于0.5时，为A到B过程的情况

                    // 乘以2是因为贝塞尔公式的t范围在0到1
                    fraction *= 2;

                    // 设置当前情况的起始点、控制点、终点
                    mABallP0.set(mABallX, mABallY);
                    mABallP1.set(mABallX + mMoveLength / 2, mABallY - mMoveLength / 2);
                    mABallP2.set(mBBallX, mBBallY);

                    // 代入贝塞尔公式得到贝塞尔曲线过程的x,y坐标
                    mABallazierX = getBazierValue(fraction, mABallP0.x, mABallP1.x, mABallP2.x);
                    mABallazierY = getBazierValue(fraction, mABallP0.y, mABallP1.y, mABallP2.y);
                } else {
                    // fraction大于等于0.5时，为A到B过程之后，再从B到C过程的情况

                    // 减0.5是因为t要从0开始变化
                    fraction -= 0.5;
                    // 乘以2是因为贝塞尔公式的t范围在0到1
                    fraction *= 2;

                    // 设置当前情况的起始点、控制点、终点
                    mABallP0.set(mBBallX, mBBallY);
                    mABallP1.set(mBBallX + mMoveLength / 2, mBBallY + mMoveLength / 2);
                    mABallP2.set(mCBallX, mCBallY);

                    // 代入贝塞尔公式得到贝塞尔曲线过程的x,y坐标
                    mABallazierX = getBazierValue(fraction, mABallP0.x, mABallP1.x, mABallP2.x);
                    mABallazierY = getBazierValue(fraction, mABallP0.y, mABallP1.y, mABallP2.y);
                }

                // 强制刷新
                postInvalidate();

            }
        });
        // 动画无限模式
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        // 时长1秒
        mAnimator.setDuration(1000);
        // 延迟0.5秒执行
        mAnimator.setStartDelay(500);
        // 开启动画
        mAnimator.start();

    }

    /**
     * 二阶贝塞尔公式：B(t)=(1-t)^2*P0+2*t*(1-t)*P1+t^2*P2,(t∈[0,1])
     */
    private float getBazierValue(float fraction, float p0, float p1, float p2) {
        return (1 - fraction) * (1 - fraction) * p0 + 2 * fraction * (1 - fraction) * p1 + fraction * fraction * p2;
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

}
