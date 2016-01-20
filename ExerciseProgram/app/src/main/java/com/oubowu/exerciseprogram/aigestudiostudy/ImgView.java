package com.oubowu.exerciseprogram.aigestudiostudy;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.View;

import com.socks.library.KLog;

/**
 * 类名： ImgView
 * 作者: oubowu
 * 时间： 2016/1/20 10:27
 * 功能：
 * svn版本号:$$Rev$$
 * 更新时间:$$Date$$
 * 更新人:$$Author$$
 * 更新描述:
 */
public class ImgView extends View {

    private final Matrix matrix;
    private Bitmap mBitmap;
    private boolean mInit;
    private int w;
    private int h;
    private int mBitmapWidth;
    private int mBitmapHeight;

    public ImgView(Context context, AttributeSet attrs) {
        super(context, attrs);
        matrix = new Matrix();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.YELLOW);
        int realH = h - getPaddingTop() - getPaddingBottom();
        int realW = w - getPaddingLeft() - getPaddingRight();
        if (!mInit
                && mBitmapHeight > realH
                || mBitmapWidth > realW) {
            mInit = true;
            float ratio = Math.min(realH * 1.0f / mBitmapHeight, realW * 1.0f / mBitmapWidth);
            KLog.e(ratio);
            matrix.postScale(ratio, ratio);
            mBitmap = Bitmap.createBitmap(mBitmap, 0, 0, mBitmapWidth, mBitmapHeight, matrix, true);
        }
        canvas.drawBitmap(mBitmap, getPaddingLeft(), getPaddingTop(), null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        w = measureSpec(widthMeasureSpec, mBitmap.getWidth() + getPaddingLeft() + getPaddingRight());
        h = measureSpec(heightMeasureSpec, mBitmap.getHeight() + getPaddingTop() + getPaddingBottom());
        setMeasuredDimension(w, h);
        KLog.e(w + ";" + h);
    }

    // MeasureSpec类中的三个Mode常量值的意义，其中UNSPECIFIED表示未指定，爹不会对儿子作任何的束缚，儿子想要多大都可以；
    // EXACTLY表示完全的，意为儿子多大爹心里有数，爹早已算好了；
    // AT_MOST表示至多，爹已经为儿子设置好了一个最大限制，儿子你不能比这个值大，不能再多了！
    private int measureSpec(int measureSpec, int defaultSize) {

        final int mode = MeasureSpec.getMode(measureSpec);
        final int size = MeasureSpec.getSize(measureSpec);

        KLog.e(Integer.toHexString(mode) + ";" + size);

        // 如果爹心里有数
        if (mode == MeasureSpec.EXACTLY) {
            // 那么儿子也不要让爹难做就取爹给的大小吧
            return size;
        }
        // 如果爹心里没数
        else if (mode == MeasureSpec.AT_MOST) {
            // 如果爹给儿子的是一个限制值,那么儿子自己的需求就要跟爹的限制比比看谁小要谁
            return Math.min(size, defaultSize);
        }

        // 那么儿子可要自己看看自己需要多大了
        return defaultSize;
    }

    /**
     * 设置位图
     *
     * @param bitmap 位图对象
     */
    public void setBitmap(Bitmap bitmap) {
        this.mBitmap = bitmap;
        mBitmapWidth = mBitmap.getWidth();
        mBitmapHeight = mBitmap.getHeight();
    }

}
