package com.oubowu.exerciseprogram.aigestudiostudy.customview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.oubowu.exerciseprogram.R;
import com.oubowu.exerciseprogram.utils.MeasureUtil;

/**
 * 类名： ReflectView
 * 作者: oubowu
 * 时间： 2016/1/11 9:51
 * 功能：
 * svn版本号:$$Rev$$
 * 更新时间:$$Date$$
 * 更新人:$$Author$$
 * 更新描述:
 */
public class ReflectView extends View {
    private int mWidth;
    private int mHeight;
    private int mSrcX;
    private int mSrcY;

    // LinearGradient  线性渐变，顾名思义这锤子玩意就是来画渐变的，实际上Shader的五个子类中除了上面我们说的那个怪胎，
    // 还有个变形金刚ComposeShader外其余三个都是渐变只是效果不同而已
    // LinearGradient(float x0, float y0, float x1, float y1, int color0, int color1, Shader.TileMode tile)
    // 这是LinearGradient最简单的一个构造方法，参数虽多其实很好理解x0和y0表示渐变的起点坐标而x1和y1则表示渐变的终点坐标，
    // 这两点都是相对于屏幕坐标系而言的，而color0和color1则表示起点的颜色和终点的颜色，这些即便是213也能懂 - - ……
    // Shader.TileMode上面我们给的是REPEAT重复但是并没有任何效果，这时因为我们渐变的起点和终点都落在了图形的两端，整个渐变shader已经填充了图形所以不起作用，如果我们改改，把终点坐标变一下：

    // LinearGradient(float x0, float y0, float x1, float y1, int[] colors, float[] positions, Shader.TileMode tile)
    // 前面四个参数也是定义坐标的不扯了colors是一个int型数组，我们用来定义所有渐变的颜色，positions表示的是渐变的相对区域，其取值只有0到1，
    // 上面的代码中我们定义了一个[0, 0.1F, 0.5F, 0.7F, 0.8F]，意思就是红色到黄色的渐变起点坐标在整个渐变区域（left, top, right, bottom定义了渐变的区域）
    // 的起点，而终点则在渐变区域长度 * 10%的地方，而黄色到绿色呢则从渐变区域10%开始到50%的地方以此类推，positions可以为空：为空时各种颜色的渐变将会均分整个渐变区域

    private Bitmap mBitmap, mReflectBitmap;
    private Paint mPaint;

    public ReflectView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();

    }

    private void init() {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.mipmap.ic_baoqiang, options);
        mBitmap = MeasureUtil.decodeSampledBitmapFromResource(getResources(), R.mipmap.ic_baoqiang,
                MeasureUtil.getScreenWidth((Activity) getContext()),
                MeasureUtil.getScreenWidth((Activity) getContext()) * options.outHeight / options.outWidth);

        // 实例化一个矩阵对象
        Matrix matrix = new Matrix();
        matrix.setScale(1, -1);
        // 生成倒影图
        mReflectBitmap = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(), mBitmap.getHeight(), matrix, true);

        mWidth = MeasureUtil.getScreenWidth((Activity) getContext());
        mHeight = MeasureUtil.getScreenHeight((Activity) getContext()) - MeasureUtil.getStatusBarHeight(getContext()) - MeasureUtil.getToolbarHeight(getContext());

        mSrcX = mWidth / 2 - mBitmap.getWidth() / 2;
        mSrcY = mHeight / 2 - mBitmap.getHeight();

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        // 倒影图片有灰渐变到透明
        mPaint.setShader(new LinearGradient(mSrcX,
                mSrcY + mBitmap.getHeight(),
                mSrcX,
                mSrcY + 2 * mBitmap.getHeight(),
                Color.DKGRAY,
                Color.TRANSPARENT,
                Shader.TileMode.CLAMP));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(mBitmap, mSrcX, mSrcY, null);
        final int i = canvas.saveLayer(0, 0, mWidth, mHeight, null, Canvas.ALL_SAVE_FLAG);
        // 目标图
        canvas.drawBitmap(mReflectBitmap, mSrcX, mSrcY + mBitmap.getHeight(), null);
        // 源图
        canvas.drawRect(mSrcX, mHeight / 2, mSrcX + mBitmap.getWidth(), mHeight / 2 + mBitmap.getHeight(), mPaint);
        canvas.restoreToCount(i);
    }
}
