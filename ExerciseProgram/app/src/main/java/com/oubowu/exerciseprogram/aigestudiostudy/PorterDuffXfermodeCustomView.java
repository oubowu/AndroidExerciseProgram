package com.oubowu.exerciseprogram.aigestudiostudy;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
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
    private Rect mAppAreaRect;
    private StringBuilder mDesctiption = new StringBuilder("PorterDuff.Mode.ADD 饱和相加");
    private StaticLayout mLayout;
    private Bitmap mXfermodeBitmap;

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

    private Paint mXfermodePaint;
    private TextPaint mTextPaint;
    private Paint mTextPaint1;

    private void initPaint() {

        mXfermodePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        PorterDuffXfermode mXfermode = new PorterDuffXfermode(PorterDuff.Mode.ADD);
        mXfermodePaint.setXfermode(mXfermode);

        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(30);

        mTextPaint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint1.setTextSize(25);
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
                mXfermodePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.ADD));
                KLog.e("ADD");
                break;
            case R.id.action_clear:
                mXfermodePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
                KLog.e("CLEAR");
                break;
            case R.id.action_darken:
                mXfermodePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DARKEN));
                KLog.e("DARKEN");
                break;
            case R.id.action_lighten:
                mXfermodePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.LIGHTEN));
                KLog.e("LIGHTEN");
                break;
            case R.id.action_dst:
                mXfermodePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST));
                KLog.e("DST");
                break;
            case R.id.action_dst_atop:
                mXfermodePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP));
                KLog.e("DST_ATOP");
                break;
            case R.id.action_dst_in:
                mXfermodePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
                KLog.e("DST_IN");
                break;
            case R.id.action_dst_out:
                mXfermodePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
                KLog.e("DST_OUT");
                break;
            case R.id.action_dst_over:
                mXfermodePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OVER));
                KLog.e("DST_OVER");
                break;
            case R.id.action_multiply:
                mXfermodePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));
                KLog.e("MULTIPLY");
                break;
            case R.id.action_overlay:
                mXfermodePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.OVERLAY));
                KLog.e("OVERLAY");
                break;
            case R.id.action_screen:
                mXfermodePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SCREEN));
                KLog.e("SCREEN");
                break;
            case R.id.action_src:
                mXfermodePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
                KLog.e("SRC");
                break;
            case R.id.action_src_atop:
                mXfermodePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
                KLog.e("SRC_ATOP");
                break;
            case R.id.action_src_in:
                mXfermodePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                KLog.e("SRC_IN");
                break;
            case R.id.action_src_out:
                mXfermodePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
                KLog.e("SRC_OUT");
                break;
            case R.id.action_src_over:
                mXfermodePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
                KLog.e("SRC_OVER");
                break;
            case R.id.action_xor:
                mXfermodePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.XOR));
                KLog.e("XOR");
                break;
        }

        mXfermodeBitmap = createXfermodeBitmap();

        mLayout = new StaticLayout(mDesctiption.toString(), mTextPaint, mAppAreaRect.width(),
                Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, true);
        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mAppAreaRect == null) return;

        canvas.save();
        canvas.drawColor(Color.WHITE);
        mLayout.draw(canvas);

//        mTextPaint1.setColor(Color.RED);
//        canvas.drawText("红色为目标图", (mAppAreaRect.width() - mXfermodeBitmap.getWidth()) / 2,
//                (mAppAreaRect.height() - MeasureUtil.getToolbarHeight(getContext()) - mXfermodeBitmap.getHeight()) / 2 - (mTextPaint1.getFontMetrics().ascent + mTextPaint1.getFontMetrics().descent) * 2,
//                mTextPaint1);
//
//        mTextPaint1.setColor(Color.BLUE);
//        canvas.drawText("蓝色为源图", (mAppAreaRect.width() - mXfermodeBitmap.getWidth()) / 2 * 8,
//                (mAppAreaRect.height() - MeasureUtil.getToolbarHeight(getContext()) - mXfermodeBitmap.getHeight()) / 2 - (mTextPaint1.getFontMetrics().ascent + mTextPaint1.getFontMetrics().descent) * 2,
//                mTextPaint1);

        mTextPaint1.setColor(Color.RED);
        canvas.drawText("红色为目标图",
                (mAppAreaRect.width() - mXfermodeBitmap.getWidth()) / 2,
                (mAppAreaRect.height() - MeasureUtil.getToolbarHeight(getContext()) - mXfermodeBitmap.getHeight()) / 2 - 20,
                mTextPaint1);

        mTextPaint1.setColor(Color.BLUE);
        canvas.drawText("蓝色为源图",
                (mAppAreaRect.width() - mXfermodeBitmap.getWidth()) / 2 * 5,
                (mAppAreaRect.height() - MeasureUtil.getToolbarHeight(getContext()) - mXfermodeBitmap.getHeight()) / 2 - 20,
                mTextPaint1);

        canvas.restore();

        // baseline 的位置 相当于 四线中 第三条线
        // ascent 的位置相当于 四线中 有 f 的时候 他是固定在 f 的最上方
        // descent 的位置 相当于 四线中 有 g 的时候 他是固定在 g 的最下方
        // top    的位置相当于 baseline 到四线最上面线的位置
        // bottom  的位置 相当于 baseline 到四线最下面线的位置
        // canvas.drawText(mDesctiption.toString(), 0, -(mTextPaint.getFontMetrics().ascent + mTextPaint.getFontMetrics().descent) * 2, mTextPaint);

        canvas.drawBitmap(mXfermodeBitmap, (mAppAreaRect.width() - mXfermodeBitmap.getWidth()) / 2,
                (mAppAreaRect.height() - MeasureUtil.getToolbarHeight(getContext()) - mXfermodeBitmap.getHeight()) / 2, null);

    }

    private Bitmap createBitamp(int w, int h) {
        return Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
    }

    private Bitmap createXfermodeBitmap() {
        Bitmap bitmap = createBitamp(mSrcBitmap.getWidth(), mSrcBitmap.getHeight());
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.LTGRAY);
        final int i = canvas.saveLayer(0, 0, bitmap.getWidth(), bitmap.getHeight(), null, Canvas.ALL_SAVE_FLAG);
        // mSrcBitmap包住mDisBitmap
        canvas.drawBitmap(mDisBitmap, 0, 0, null);
        canvas.drawBitmap(mSrcBitmap, 0, 0, mXfermodePaint);
        canvas.restoreToCount(i);
        return bitmap;
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus) {
            mAppAreaRect = MeasureUtil.getAppAreaRect((Activity) getContext());
            mLayout = new StaticLayout(mDesctiption.toString(), mTextPaint, mAppAreaRect.width(),
                    Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, true);

            Bitmap bm = createBitamp(mAppAreaRect.width() / 2, mAppAreaRect.width() / 2);
            Canvas c = new Canvas(bm);
            Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
            p.setColor(0xFFFF0000);
            c.drawOval(new RectF(0, 0, mAppAreaRect.width() / 2, mAppAreaRect.width() / 2), p);
            mDisBitmap = bm;

            Bitmap bm1 = createBitamp(mAppAreaRect.width() / 2 + mDisBitmap.getWidth() / 2, mAppAreaRect.width() / 2 + mDisBitmap.getHeight() / 2);
            Canvas c1 = new Canvas(bm1);
            Paint p1 = new Paint(Paint.ANTI_ALIAS_FLAG);
            p1.setColor(0xFF0000FF);
            c1.drawRect(mDisBitmap.getWidth() / 2, mDisBitmap.getHeight() / 2, mAppAreaRect.width(), (mAppAreaRect.height() - MeasureUtil.getToolbarHeight(getContext())) / 2 + mDisBitmap.getHeight() / 2, p1);
            mSrcBitmap = bm1;

            mXfermodeBitmap = createXfermodeBitmap();

            invalidate();
        }
    }


}
