package com.oubowu.exerciseprogram.aigestudiostudy;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * 类名： MultiCricleView
 * 作者: oubowu
 * 时间： 2016/1/12 9:20
 * 功能：
 * svn版本号:$$Rev$$
 * 更新时间:$$Date$$
 * 更新人:$$Author$$
 * 更新描述:
 */
public class MultiCricleView extends View {

    private float mTextOffset;
    private int mScreenWidth;
    private float mStrokeWidth;
    private float mLineLength;
    private float mLargerCircleRadius;
    private float mSmallerCircleRadius;
    private float mArcRadius;
    private float mArcTextRadius;
    private Paint mPaint;
    private Paint mArcPaint;
    private Paint mTextPaint;
    private RectF oval;

    public MultiCricleView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        mArcPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.SUBPIXEL_TEXT_FLAG);
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextSize(27);
        // 设置文字水平居中
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        final Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        // 距离baseline的偏移量
        mTextOffset = (-fontMetrics.ascent - fontMetrics.descent) / 2;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mScreenWidth = w;
        mStrokeWidth = 1 / 256.0f * mScreenWidth;
        mLineLength = 3 / 32.0f * mScreenWidth;
        mLargerCircleRadius = 3 / 32.0f * mScreenWidth;
        mSmallerCircleRadius = 5 / 64.0f * mScreenWidth;
        mArcRadius = 1 / 8.0f * mScreenWidth;
        mArcTextRadius = 5 / 32.0f * mScreenWidth;

        mPaint.setStrokeWidth(mStrokeWidth);
        mArcPaint.setStrokeWidth(mStrokeWidth);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(0xFFF29B76);

        // 画中心圆
        canvas.drawCircle(mScreenWidth / 2, mScreenWidth / 2 + mLargerCircleRadius, mLargerCircleRadius, mPaint);
        canvas.drawText("OuBowu", mScreenWidth / 2, mScreenWidth / 2 + mLargerCircleRadius + mTextOffset, mTextPaint);

        // 画第二象限的两个圆
        canvas.save();
        // 画布移到中心圆中心
        canvas.translate(mScreenWidth / 2, mScreenWidth / 2 + mLargerCircleRadius);
        // 旋转画布-30°,坐标系也就跟着变化了
        canvas.rotate(-30);
        canvas.drawLine(0, -mLargerCircleRadius, 0, -mLargerCircleRadius - mLineLength, mPaint);
        canvas.drawCircle(0, -mLargerCircleRadius * 2 - mLineLength, mLargerCircleRadius, mPaint);
        canvas.drawLine(0, -mLargerCircleRadius * 3 - mLineLength, 0, -mLargerCircleRadius * 3 - 2 * mLineLength, mPaint);
        canvas.drawCircle(0, -mLargerCircleRadius * 4 - 2 * mLineLength, mLargerCircleRadius, mPaint);
        canvas.restore();

        // 左上第一个圆的文字
        canvas.save();
        // 画布移到中心圆中心
        canvas.translate(mScreenWidth / 2, mScreenWidth / 2 + mLargerCircleRadius);
        // 旋转画布-30°
        canvas.rotate(-30);
        // 移动到左上第一个圆中心
        canvas.translate(0, -mLargerCircleRadius * 2 - mLineLength);
        // 旋转回来
        canvas.rotate(30);
        canvas.drawText("Apple", 0, 0 + mTextOffset, mTextPaint);
        canvas.restore();

        // 左上第二个圆的文字
        canvas.save();
        canvas.translate(mScreenWidth / 2, mScreenWidth / 2 + mLargerCircleRadius);
        canvas.rotate(-30);
        canvas.translate(0, -mLargerCircleRadius * 4 - 2 * mLineLength);
        canvas.rotate(30);
        canvas.drawText("Android", 0, 0 + mTextOffset, mTextPaint);
        canvas.restore();

        // 画中心圆下面的圆
        canvas.drawLine(mScreenWidth / 2, mScreenWidth / 2 + mLargerCircleRadius * 2, mScreenWidth / 2, mScreenWidth / 2 + mLargerCircleRadius * 2 + mLineLength, mPaint);
        canvas.drawCircle(mScreenWidth / 2, mScreenWidth / 2 + mLargerCircleRadius * 2 + mLineLength + mSmallerCircleRadius, mSmallerCircleRadius, mPaint);
        // 中心圆的下面圆的文字
        canvas.drawText("IOS", mScreenWidth / 2, mScreenWidth / 2 + mLargerCircleRadius * 2 + mLineLength + mSmallerCircleRadius + mTextOffset, mTextPaint);

        // 画第三象限的圆
        canvas.save();
        canvas.translate(mScreenWidth / 2, mScreenWidth / 2 + mLargerCircleRadius);
        canvas.rotate(75);
        canvas.drawLine(0, mLargerCircleRadius, 0, mLargerCircleRadius + mLineLength, mPaint);
        canvas.drawCircle(0, mLargerCircleRadius + mLineLength + mSmallerCircleRadius, mSmallerCircleRadius, mPaint);
        canvas.restore();

        // 画第三象限的圆文字
        canvas.save();
        canvas.translate(mScreenWidth / 2, mScreenWidth / 2 + mLargerCircleRadius);
        canvas.rotate(75);
        canvas.translate(0, mLargerCircleRadius + mLineLength + mSmallerCircleRadius);
        canvas.rotate(-75);
        canvas.drawText("Windows", 0, 0 + mTextOffset, mTextPaint);
        canvas.restore();

        // 画第四象限的圆
        canvas.save();
        canvas.translate(mScreenWidth / 2, mScreenWidth / 2 + mLargerCircleRadius);
        canvas.rotate(-75);
        canvas.drawLine(0, mLargerCircleRadius, 0, mLargerCircleRadius + mLineLength, mPaint);
        canvas.drawCircle(0, mLargerCircleRadius + mLineLength + mSmallerCircleRadius, mSmallerCircleRadius, mPaint);
        canvas.restore();

        // 画第四象限的圆文字
        canvas.save();
        canvas.translate(mScreenWidth / 2, mScreenWidth / 2 + mLargerCircleRadius);
        canvas.rotate(-75);
        canvas.translate(0, mLargerCircleRadius + mLineLength + mSmallerCircleRadius);
        canvas.rotate(75);
        canvas.drawText("Miui", 0, 0 + mTextOffset, mTextPaint);
        canvas.restore();

        // 画第一象限的圆
        canvas.save();
        canvas.translate(mScreenWidth / 2, mScreenWidth / 2 + mLargerCircleRadius);
        canvas.rotate(30);
        canvas.drawLine(0, -mLargerCircleRadius, 0, -mLargerCircleRadius - mLineLength, mPaint);
        canvas.drawCircle(0, -mLargerCircleRadius - mLineLength - mLargerCircleRadius, mLargerCircleRadius, mPaint);
        canvas.restore();

        // 画第一象限的圆文字
        canvas.save();
        canvas.translate(mScreenWidth / 2, mScreenWidth / 2 + mLargerCircleRadius);
        canvas.rotate(30);
        canvas.translate(0, -mLargerCircleRadius - mLineLength - mLargerCircleRadius);
        canvas.rotate(-30);
        canvas.drawText("Flyme", 0, 0 + mTextOffset, mTextPaint);
        canvas.restore();

        // 画第一象限圆上的扇形
        canvas.save();
        canvas.translate(mScreenWidth / 2, mScreenWidth / 2 + mLargerCircleRadius);
        canvas.rotate(30);
        canvas.translate(0, -mLargerCircleRadius - mLineLength - mLargerCircleRadius);
        canvas.rotate(-30);
        if (oval == null)
            oval = new RectF(-mArcRadius, -mArcRadius, mArcRadius, mArcRadius);
        mArcPaint.setStyle(Paint.Style.FILL);
        mArcPaint.setColor(0x55EC6941);
        // oval弧所对应的圆的范围，22.5为开始的角度，135为转动的角度，true为显示扇形半径
        canvas.drawArc(oval, -22.5f, -135, true, mArcPaint);
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setColor(Color.WHITE);
        canvas.drawArc(oval, -22.5f, -135, false, mArcPaint);
        canvas.restore();

        // 画扇形的文字
        canvas.save();
        canvas.translate(mScreenWidth / 2, mScreenWidth / 2 + mLargerCircleRadius);
        canvas.rotate(30);
        canvas.translate(0, -mLargerCircleRadius - mLineLength - mLargerCircleRadius);
        canvas.rotate(-30);
        canvas.rotate(-(90 - 22.5f));
        for (float i = 0; i < 5 * 33.75F; i += 33.75F) {
            canvas.save();
            canvas.rotate(i);
            canvas.drawText("Aige", 0, -mArcTextRadius, mTextPaint);
            canvas.restore();
        }
        canvas.restore();

    }

}
