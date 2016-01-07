package com.oubowu.exerciseprogram.aigestudiostudy;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;

import com.oubowu.exerciseprogram.R;
import com.oubowu.exerciseprogram.utils.MeasureUtil;
import com.socks.library.KLog;

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
public class PorterDuffXfermodeCustomView extends View {

    private Bitmap mDisBitmap;
    private Bitmap mSrcBitmap;
    private Rect appAreaRect;
    private PorterDuffXfermode mXfermode;
    private StringBuilder mDesctiption = new StringBuilder("PorterDuff.Mode.ADD 饱和相加");
    private StaticLayout layout;

    public PorterDuffXfermodeCustomView(Context context) {
        super(context);
        initPaint();
    }

    public PorterDuffXfermodeCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public PorterDuffXfermodeCustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private Paint mPaint;
    private TextPaint mTextPaint;

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

       /*
        * 设置画笔样式为描边，圆环嘛……当然不能填充不然就么意思了
        *
        * 画笔样式分三种：
        * 1.Paint.Style.STROKE：描边
        * 2.Paint.Style.FILL_AND_STROKE：描边并填充
        * 3.Paint.Style.FILL：填充
        */
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        // 设置画笔颜色为自定义颜色
        mPaint.setColor(Color.argb(255, 255, 128, 103));

       /*
        * 设置描边的粗细，单位：像素px
       * 注意：当setStrokeWidth(0)的时候描边宽度并不为0而是只占一个像素
        */
        mPaint.setStrokeWidth(10);

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.mipmap.ic_baifeng, options);

        mDisBitmap = MeasureUtil.decodeSampledBitmapFromResource(getResources(),
                R.mipmap.ic_baifeng,
                MeasureUtil.getScreenWidth((Activity) getContext()),
                MeasureUtil.getScreenWidth((Activity) getContext()) * options.outHeight / options.outWidth);

        BitmapFactory.decodeResource(getResources(), R.mipmap.ic_shaosiming, options);
        mSrcBitmap = MeasureUtil.decodeSampledBitmapFromResource(getResources(),
                R.mipmap.ic_shaosiming,
                mDisBitmap.getHeight() * 3 / 4 * options.outWidth / options.outHeight,
                mDisBitmap.getHeight() * 3 / 4);

        mXfermode = new PorterDuffXfermode(PorterDuff.Mode.ADD);

        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(30);

    }

    public void porterDuffXfermode(MenuItem item) {
        // PorterDuffXfermode
        // 该类同样有且只有一个含参的构造方法PorterDuffXfermode(PorterDuff.Mode mode)，这个PorterDuff.Mode大家看后是否会有些面熟，
        // 它跟上面我们讲ColorFilter时候用到的PorterDuff.Mode是一样的！麻雀虽小五脏俱全，虽说构造方法的签名列表里只有一个PorterDuff.Mode的参数，
        // 但是它可以实现很多酷毙的图形效果！
        // 图片从一定程度上形象地说明了图形混合的作用，两个图形一圆一方通过一定的计算产生不同的组合效果，在API中Android为我们提供了18种（比上图多了两种ADD和OVERLAY）模式

        // Src为源图像，意为将要绘制的图像；Dis为目标图像，意为我们将要把源图像绘制到的图像
        // 先绘制dis，然后再绘制src

        // PorterDuff.Mode.ADD 计算方式：Saturate(S + D)；Chinese：饱和相加
        // PorterDuff.Mode.CLEAR 计算方式：[0, 0]；Chinese：清除
        // PorterDuff.Mode.DARKEN 计算方式：[Sa + Da - Sa*Da, Sc*(1 - Da) + Dc*(1 - Sa) + min(Sc, Dc)]；Chinese：变暗
        // PorterDuff.Mode.LIGHTEN 计算方式：[Sa + Da - Sa*Da, Sc*(1 - Da) + Dc*(1 - Sa) + max(Sc, Dc)]；Chinese：变亮
        // PorterDuff.Mode.DST 计算方式：[Da, Dc]；Chinese：只绘制目标图像
        // PorterDuff.Mode.DST_ATOP 计算方式：[Sa, Sa * Dc + Sc * (1 - Da)]；Chinese：在源图像和目标图像相交的地方绘制目标图像而在不相交的地方绘制源图像
        // PorterDuff.Mode.DST_IN 计算方式：[Sa * Da, Sa * Dc]；Chinese：只在源图像和目标图像相交的地方绘制目标图像
        // PorterDuff.Mode.DST_OUT 计算方式：[Da * (1 - Sa), Dc * (1 - Sa)]；Chinese：只在源图像和目标图像不相交的地方绘制目标图像
        // PorterDuff.Mode.DST_OVER 计算方式：[Sa + (1 - Sa)*Da, Rc = Dc + (1 - Da)*Sc]；Chinese：在源图像的上方绘制目标图像
        // PorterDuff.Mode.MULTIPLY 计算方式：[Sa * Da, Sc * Dc]；Chinese：正片叠底
        // PorterDuff.Mode.OVERLAY 计算方式：未给出；Chinese：叠加
        // PorterDuff.Mode.SCREEN 计算方式：[Sa + Da - Sa * Da, Sc + Dc - Sc * Dc]；Chinese：滤色
        // PorterDuff.Mode.SRC 计算方式：[Sa, Sc]；Chinese：显示源图
        // PorterDuff.Mode.SRC_ATOP 计算方式：[Da, Sc * Da + (1 - Sa) * Dc]；Chinese：在源图像和目标图像相交的地方绘制源图像，在不相交的地方绘制目标图像
        // PorterDuff.Mode.SRC_IN 计算方式：[Sa * Da, Sc * Da]；Chinese：只在源图像和目标图像相交的地方绘制源图像
        // PorterDuff.Mode.SRC_OUT 计算方式：[Sa * (1 - Da), Sc * (1 - Da)]；Chinese：只在源图像和目标图像不相交的地方绘制源图像
        // PorterDuff.Mode.SRC_OVER 计算方式：[Sa + (1 - Sa)*Da, Rc = Sc + (1 - Sa)*Dc]；Chinese：在目标图像的顶部绘制源图像
        // PorterDuff.Mode.XOR 计算方式：[Sa + Da - 2 * Sa * Da, Sc * (1 - Da) + (1 - Sa) * Dc]；Chinese：在源图像和目标图像重叠之外的任何地方绘制他们，而在重叠的地方不绘制任何内容

        mDesctiption.setLength(0);
        mDesctiption.append(item.getTitle().toString());

        switch (item.getItemId()) {
            case R.id.action_add:
                mXfermode = new PorterDuffXfermode(PorterDuff.Mode.ADD);
                KLog.e("ADD");
                break;
            case R.id.action_clear:
                mXfermode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
                KLog.e("CLEAR");
                break;
            case R.id.action_darken:
                mXfermode = new PorterDuffXfermode(PorterDuff.Mode.DARKEN);
                KLog.e("DARKEN");
                break;
            case R.id.action_lighten:
                mXfermode = new PorterDuffXfermode(PorterDuff.Mode.LIGHTEN);
                KLog.e("LIGHTEN");
                break;
            case R.id.action_dst:
                mXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST);
                KLog.e("DST");
                break;
            case R.id.action_dst_atop:
                mXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP);
                KLog.e("DST_ATOP");
                break;
            case R.id.action_dst_in:
                mXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
                KLog.e("DST_IN");
                break;
            case R.id.action_dst_out:
                mXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);
                KLog.e("DST_OUT");
                break;
            case R.id.action_dst_over:
                mXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_OVER);
                KLog.e("DST_OVER");
                break;
            case R.id.action_multiply:
                mXfermode = new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY);
                KLog.e("MULTIPLY");
                break;
            case R.id.action_overlay:
                mXfermode = new PorterDuffXfermode(PorterDuff.Mode.OVERLAY);
                KLog.e("OVERLAY");
                break;
            case R.id.action_screen:
                mXfermode = new PorterDuffXfermode(PorterDuff.Mode.SCREEN);
                KLog.e("SCREEN");
                break;
            case R.id.action_src:
                mXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC);
                KLog.e("SRC");
                break;
            case R.id.action_src_atop:
                mXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP);
                KLog.e("SRC_ATOP");
                break;
            case R.id.action_src_in:
                mXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
                KLog.e("SRC_IN");
                break;
            case R.id.action_src_out:
                mXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT);
                KLog.e("SRC_OUT");
                break;
            case R.id.action_src_over:
                mXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER);
                KLog.e("SRC_OVER");
                break;
            case R.id.action_xor:
                mXfermode = new PorterDuffXfermode(PorterDuff.Mode.XOR);
                KLog.e("XOR");
                break;
        }

        /*final int length = mDesctiption.length();
        int start = 0;
        int end;
        for (end = 1; end < length - 1; end++) {
            if (mTextPaint.measureText(mDesctiption.substring(start, end)) >= appAreaRect.width()) {
                mDesctiption.insert(end, "\n");
                start = end;
            }
        }*/
        layout = new StaticLayout(mDesctiption.toString(), mTextPaint, appAreaRect.width(),
                Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, true);
        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (appAreaRect == null) return;

        canvas.drawColor(Color.LTGRAY);

        // baseline 的位置 相当于 四线中 第三条线
        // ascent 的位置相当于 四线中 有 f 的时候 他是固定在 f 的最上方
        // descent 的位置 相当于 四线中 有 g 的时候 他是固定在 g 的最下方
        // top    的位置相当于 baseline 到四线最上面线的位置
        // bottom  的位置 相当于 baseline 到四线最下面线的位置
        // canvas.drawText(mDesctiption.toString(), 0, -(mTextPaint.getFontMetrics().ascent + mTextPaint.getFontMetrics().descent) * 2, mTextPaint);

        // Src为源图像，意为将要绘制的图像；Dis为目标图像，意为我们将要把源图像绘制到的图像

        // 离屏缓存
        final int i = canvas.saveLayer(appAreaRect.left, appAreaRect.top, appAreaRect.right, appAreaRect.bottom, null, Canvas.ALL_SAVE_FLAG);

        // 先绘制dis，然后再绘制src
        canvas.drawBitmap(mDisBitmap, (appAreaRect.width() - mDisBitmap.getWidth()) / 2,
                (appAreaRect.height() - MeasureUtil.getToolbarHeight(getContext()) - mDisBitmap.getHeight()) / 2,
                mPaint);

        mPaint.setXfermode(mXfermode);

        canvas.drawBitmap(mSrcBitmap, (appAreaRect.width() - mSrcBitmap.getWidth()) / 2,
                (appAreaRect.height() - MeasureUtil.getToolbarHeight(getContext()) - mSrcBitmap.getHeight()) / 2 + mSrcBitmap.getHeight() / 2,
                mPaint);

        mPaint.setXfermode(null);

        // 还原画布
        canvas.restoreToCount(i);

        canvas.save();
        layout.draw(canvas);
        canvas.restore();

    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus) {
            appAreaRect = MeasureUtil.getAppAreaRect((Activity) getContext());
            layout = new StaticLayout(mDesctiption.toString(), mTextPaint, appAreaRect.width(),
                    Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, true);
            invalidate();
        }
    }

}
