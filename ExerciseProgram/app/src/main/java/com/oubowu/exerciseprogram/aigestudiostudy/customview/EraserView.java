package com.oubowu.exerciseprogram.aigestudiostudy.customview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import com.oubowu.exerciseprogram.R;
import com.oubowu.exerciseprogram.utils.MeasureUtil;

/**
 * 类名： CustomView
 * 作者: oubowu
 * 时间： 2016/1/6 13:43
 * 功能：
 * svn版本号:$$Rev$$
 * 更新时间:$$Date$$
 * 更新人:$$Author$$
 * 更新描述:
 */
public class EraserView extends View {

    private Bitmap mBgBitmap;
    private Bitmap mFgBitmap;
    private Rect mAppAreaRect;
    private Path mPath;
    private Canvas mFgCanvas;
    private float x, preX;
    private float y, preY;
    private int mTouchSlop;
    private Paint mTextPaint;
    private Paint.FontMetrics mFontMetrics;

    public EraserView(Context context) {
        super(context);
        initPaint();
    }

    public EraserView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public EraserView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private Paint mXfermodePaint;

    private void initPaint() {

        mXfermodePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mXfermodePaint.setARGB(0, 255, 0, 0);
        mXfermodePaint.setStyle(Paint.Style.STROKE);
        mXfermodePaint.setStrokeCap(Paint.Cap.ROUND);
        mXfermodePaint.setStrokeJoin(Paint.Join.ROUND);
        mXfermodePaint.setStrokeWidth(50);

        PorterDuffXfermode mXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
        mXfermodePaint.setXfermode(mXfermode);

        mPath = new Path();

        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(ViewConfiguration.get(getContext()));

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(30);
        mTextPaint.setColor(Color.RED);
        mFontMetrics = mTextPaint.getFontMetrics();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mAppAreaRect == null) return;


        canvas.drawBitmap(mBgBitmap,
                (mAppAreaRect.width() - mBgBitmap.getWidth()) / 2,
                (mAppAreaRect.height() - MeasureUtil.getStatusBarHeight(getContext()) - mBgBitmap.getHeight()) / 2,
                null);

        canvas.drawBitmap(mFgBitmap,
                (mAppAreaRect.width() - mFgBitmap.getWidth()) / 2,
                (mAppAreaRect.height() - MeasureUtil.getStatusBarHeight(getContext()) - mFgBitmap.getHeight()) / 2,
                null);

        /*
         * 这里要注意canvas和mCanvas是两个不同的画布对象
         * 当我们在屏幕上移动手指绘制路径时会把路径通过mCanvas绘制到fgBitmap上
         * 每当我们手指移动一次均会将路径mPath作为目标图像绘制到mCanvas上，而在上面我们先在mCanvas上绘制了中性灰色
         * 两者会因为DST_IN模式的计算只显示中性灰，但是因为mPath的透明，计算生成的混合图像也会是透明的
         * 所以我们会得到“橡皮擦”的效果
         */
        // Path明明是最后画上去的，为什么是目标图？？？？？？？？？
        // 经过测试Path应该是源图才对，由于源图没有把目标图整个包住，所以任何混合效果的作用范围只在源图与目标图相交处
        // PorterDuff.Mode.DST_IN 计算方式：[Sa * Da, Sa * Dc]；Chinese：只在源图像和目标图像相交的地方绘制目标图像
        // 由于XfermodePaint.setARGB(0, 255, 0, 0); Sa=0;故生成的颜色[0,Sa*Dc]透明度为0即全透明
        // 用Clear模式一样可以做这个效果
        mFgCanvas.drawPath(mPath, mXfermodePaint);

        // drawText都是以baseline为基准的，所以要将文字绘制到中间，需要计算出相对baseline的偏移量
        // (-mFontMetrics.ascent + mFontMetrics.descent) / 2得到文字的高度一半
        // - mFontMetrics.descent 得到相对baseline的偏移量
        canvas.drawText("橡皮擦效果", canvas.getWidth() / 2 - mTextPaint.measureText("橡皮擦效果") / 2,
                canvas.getHeight() / 2 + ((-mFontMetrics.ascent + mFontMetrics.descent) / 2 - mFontMetrics.descent), mTextPaint);
        canvas.drawLine(0, canvas.getHeight() / 2, canvas.getWidth(), canvas.getHeight() / 2, mTextPaint);
    }

    private Bitmap createBitamp(int w, int h) {
        return Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        x = event.getX();
        y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPath.moveTo(x, y);
                preX = x;
                preY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = Math.abs(x - preX);
                float dy = Math.abs(y - preY);
                if (dx >= mTouchSlop || dy >= mTouchSlop) {
                    mPath.quadTo(preX, preY, x, y);
                    preX = x;
                    preY = y;
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                mPath.reset();
                break;
        }
        return true;
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus) {
            mAppAreaRect = MeasureUtil.getAppAreaRect((Activity) getContext());

            mBgBitmap = MeasureUtil.decodeSampledBitmapFromResource(getResources(),
                    R.mipmap.ic_pal,
                    mAppAreaRect.width(),
                    mAppAreaRect.height() - MeasureUtil.getStatusBarHeight(getContext()));

            mFgBitmap = createBitamp(mAppAreaRect.width(), mAppAreaRect.height() - MeasureUtil.getStatusBarHeight(getContext()));
            mFgCanvas = new Canvas(mFgBitmap);
            mFgCanvas.drawColor(Color.DKGRAY);

            invalidate();
        }
    }


}
