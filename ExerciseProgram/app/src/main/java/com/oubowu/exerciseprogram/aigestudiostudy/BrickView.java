package com.oubowu.exerciseprogram.aigestudiostudy;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.oubowu.exerciseprogram.R;
import com.oubowu.exerciseprogram.utils.MeasureUtil;

/**
 * ClassName:
 * Author:oubowu
 * Fuction:
 * CreateDate:2016/1/10 17:30
 * UpdateUser:
 * UpdateDate:
 */
public class BrickView extends View {

    private int mScreenWidth;
    private int mScrrenHeight;
    private float mX;
    private float mY;
    private Bitmap bitmap;

    public BrickView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private Paint mFillPaint, mStrokePaint;

    private void init() {

        // 图像
        mFillPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mScreenWidth = MeasureUtil.getScreenWidth((Activity) getContext());
        mScrrenHeight = MeasureUtil.getScreenHeight((Activity) getContext());
        bitmap = MeasureUtil.decodeSampledBitmapFromResource(getResources(), R.mipmap.ic_wall, mScreenWidth / 5, mScreenWidth / 5);
        mFillPaint.setShader(new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));

        // BitmapShader (Bitmap bitmap, Shader.TileMode tileX, Shader.TileMode tileY)的第一个参数是位图这个很显然，而后两个参数则分别表示XY方向上的着色模式
        // Shader.TileMode里有三种模式：CLAMP、MIRROR和REPETA
        // REPEAT也就是重复的意思，同理MIRROR也就是镜像的意思,CLAMP的意思就是边缘拉伸
        // CLAMP  ：如果渲染器超出原始边界范围，会复制范围内边缘染色。(所以实现圆图的话可以用BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)，然后drawCircle，只要画的半径恰好为bitmap一半宽度即可)
        // REPEAT ：横向和纵向的重复渲染器图片，平铺。
        // MIRROR ：横向和纵向的重复渲染器图片，这个和REPEAT 重复方式不一样，他是以镜像方式平铺。

        // 边框
        mStrokePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mStrokePaint.setStrokeWidth(5);
        mStrokePaint.setColor(Color.LTGRAY);
        mStrokePaint.setStyle(Paint.Style.STROKE);

        mX = mScreenWidth / 2;
        mY = mScrrenHeight / 2;

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            mX = event.getX();
            mY = event.getY();
            invalidate();
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.DKGRAY);
        canvas.drawCircle(mX, mY, mScreenWidth / 5, mFillPaint);
        canvas.drawCircle(mX, mY, mScreenWidth / 5, mStrokePaint);
    }
}
