package com.oubowu.exerciseprogram.aigestudiostudy;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.oubowu.exerciseprogram.R;
import com.oubowu.exerciseprogram.utils.MeasureUtil;

/**
 * 类名： DreamEffectView
 * 作者: oubowu
 * 时间： 2016/1/11 10:38
 * 功能：
 * svn版本号:$$Rev$$
 * 更新时间:$$Date$$
 * 更新人:$$Author$$
 * 更新描述:
 */
public class DreamEffectView extends View {

    public DreamEffectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private Paint mBitmapPaint, mShaderPaint;// 位图画笔和Shader图形的画笔
    private Bitmap mBitmap, mDarkCornerBitmap;// 源图的Bitmap和我们自己画的暗角Bitmap
    private PorterDuffXfermode mXfermode;// 图形混合模式
    private int mSrcX, mSrcY;// 位图起点坐标
    private int mWidth, mHeight;// 屏幕宽高

    private void init() {
        // 位图
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.mipmap.ic_baoqiang, options);
        mBitmap = MeasureUtil.decodeSampledBitmapFromResource(getResources(), R.mipmap.ic_baoqiang,
                MeasureUtil.getScreenWidth((Activity) getContext()),
                MeasureUtil.getScreenWidth((Activity) getContext()) * options.outHeight / options.outWidth);

        // 混合模式
        mXfermode = new PorterDuffXfermode(PorterDuff.Mode.SCREEN);

        mWidth = MeasureUtil.getScreenWidth((Activity) getContext());
        mHeight = MeasureUtil.getScreenHeight((Activity) getContext()) - MeasureUtil.getStatusBarHeight(getContext()) - MeasureUtil.getToolbarHeight(getContext());

        mSrcX = mWidth / 2 - mBitmap.getWidth() / 2;
        mSrcY = mHeight / 2 - mBitmap.getHeight() / 2;

        mBitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // 去饱和、提亮、色相矫正
        mBitmapPaint.setColorFilter(new ColorMatrixColorFilter(new float[]{
                0.8587F, 0.2940F, -0.0927F, 0, 6.79F,
                0.0821F, 0.9145F, 0.0634F, 0, 6.79F,
                0.2019F, 0.1097F, 0.7483F, 0, 6.79F,
                0, 0, 0, 1, 0}));
        mBitmapPaint.setXfermode(mXfermode);

        mShaderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // 根据我们源图的大小生成暗角Bitmap
        mDarkCornerBitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        // 将该暗角Bitmap注入Canvas
        Canvas canvas = new Canvas(mDarkCornerBitmap);
        // 计算径向渐变半径
        float radius = canvas.getHeight() * 2 / 3.0f;

        // RadialGradient 径向渐变，径向渐变说的简单点就是个圆形中心向四周渐变的效果
        // colors是一个int型数组，我们用来定义所有渐变的颜色，positions表示的是渐变的相对区域，其取值只有0到1
        RadialGradient radialGradient = new RadialGradient(canvas.getWidth() / 2,
                canvas.getHeight() / 2,
                radius,
                // 0x00000000透明度为100%黑色
                new int[]{0x00000000, 0x00000000, 0xAA000000},
                new float[]{0F, 0.7F, 1.0F},
                Shader.TileMode.CLAMP);
        Matrix matrix = new Matrix();
        // 设置矩阵的缩放
        matrix.setScale(canvas.getWidth() / (radius * 2f), 1f);
        // 设置矩阵的预平移(preTranslate是指在上面的setScale前)
        matrix.preTranslate(((radius * 2F) - canvas.getWidth()) / 2F, 0);
        // 将该矩阵注入径向渐变
        radialGradient.setLocalMatrix(matrix);

        mShaderPaint.setShader(radialGradient);
        // 绘制矩形
        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), mShaderPaint);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        final int i = canvas.saveLayer(mSrcX, mSrcY, mSrcX + mBitmap.getWidth(), mSrcY + mBitmap.getHeight(), null, Canvas.ALL_SAVE_FLAG);
        // 绘制混合颜色
        canvas.drawColor(0xcc1c093e);
        canvas.drawBitmap(mBitmap, mSrcX, mSrcY, mBitmapPaint);
        canvas.restoreToCount(i);

        canvas.drawBitmap(mDarkCornerBitmap, mSrcX, mSrcY, null);
    }
}
