package com.oubowu.exerciseprogram.aigestudiostudy;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;

import com.oubowu.exerciseprogram.R;
import com.oubowu.exerciseprogram.utils.MeasureUtil;

/**
 * 类名： BlurMaskFilterView
 * 作者: oubowu
 * 时间： 2016/1/8 11:51
 * 功能：
 * svn版本号:$$Rev$$
 * 更新时间:$$Date$$
 * 更新人:$$Author$$
 * 更新描述:
 */
public class BlurMaskFilterView extends View {

    private Bitmap mSrcBitmap, mShadowBitmap;
    private Rect mAppAreaRect;
    private Paint mShadowPaint;

    public BlurMaskFilterView(Context context) {
        super(context);
        initPaint();
    }

    public BlurMaskFilterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public BlurMaskFilterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        mShadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mShadowPaint.setColor(Color.DKGRAY);
        //  BlurMaskFilter只有一个含参的构造函数BlurMaskFilter(float radius, BlurMaskFilter.Blur style)，其中radius很容易理解，值越大我们的阴影越扩散
        // 而第二个参数style表示的是模糊的类型，上面我们用到的是SOLID，其效果就是在图像的Alpha边界外产生一层与Paint颜色一致的阴影效果而不影响图像本身，
        // 除了SOLID还有三种，NORMAL,OUTER和INNER,
        // NORMAL会将整个图像模糊掉
        // 而OUTER会在Alpha边界外产生一层阴影且会将原本的图像变透明
        // INNER则会在图像内部产生模糊
        mShadowPaint.setMaskFilter(new BlurMaskFilter(10, BlurMaskFilter.Blur.SOLID));

        // PS:setMaskFilter(MaskFilter maskfilter) MaskFilter类中没有任何实现方法，而它有两个子类BlurMaskFilter和EmbossMaskFilter，
        // 前者为模糊遮罩滤镜（比起称之为过滤器哥更喜欢称之为滤镜）,而后者为浮雕遮罩滤镜
        // EmbossMaskFilter的使用面并不是很大，因为所说其参数稍复杂但是其实现原理是简单粗暴的，简而言之就是根据参数在图像周围绘制一个“色带”来模拟浮雕的效果
        // 如果我们的图像很复杂EmbossMaskFilter很难会正确模拟，所以一般遇到这类图直接call美工

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.mipmap.ic_baoqiang, options);

        mSrcBitmap = MeasureUtil.decodeSampledBitmapFromResource(getResources(),
                R.mipmap.ic_baoqiang,
                MeasureUtil.getScreenWidth((Activity) getContext()),
                MeasureUtil.getScreenWidth((Activity) getContext()) * options.outHeight / options.outWidth);

        // 获取位图的Alpha通道图
        mShadowBitmap = mSrcBitmap.extractAlpha();

        setLayerType(LAYER_TYPE_SOFTWARE, null);

    }

    public void setMaskFilter(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_solid:
                mShadowPaint.setMaskFilter(new BlurMaskFilter(10, BlurMaskFilter.Blur.SOLID));
                break;
            case R.id.action_normal:
                mShadowPaint.setMaskFilter(new BlurMaskFilter(10, BlurMaskFilter.Blur.NORMAL));
                break;
            case R.id.action_outer:
                mShadowPaint.setMaskFilter(new BlurMaskFilter(10, BlurMaskFilter.Blur.OUTER));
                break;
            case R.id.action_inner:
                mShadowPaint.setMaskFilter(new BlurMaskFilter(10, BlurMaskFilter.Blur.INNER));
                break;
        }
        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        if (mAppAreaRect == null) return;

        canvas.drawColor(Color.WHITE);

        // 通过Bitmap的extractAlpha()方法从原图中分离出一个Alpha通道位图并在计算模糊滤镜的时候使用该位图生成模糊效果
//        canvas.drawBitmap(mShadowBitmap, (mAppAreaRect.width() - mSrcBitmap.getWidth()) / 2, (mAppAreaRect.height() - MeasureUtil.getStatusBarHeight(getContext()) - mSrcBitmap.getHeight()) / 2, mShadowPaint);

        canvas.drawBitmap(mSrcBitmap, (mAppAreaRect.width() - mSrcBitmap.getWidth()) / 2, (mAppAreaRect.height() - MeasureUtil.getStatusBarHeight(getContext()) - mSrcBitmap.getHeight()) / 2, mShadowPaint);

    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus) {
            mAppAreaRect = MeasureUtil.getAppAreaRect((Activity) getContext());
            invalidate();
        }
    }

}
