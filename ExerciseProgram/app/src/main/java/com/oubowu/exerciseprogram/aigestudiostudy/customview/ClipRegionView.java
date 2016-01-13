package com.oubowu.exerciseprogram.aigestudiostudy.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Region;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;

import com.oubowu.exerciseprogram.R;

/**
 * 类名： ClipRegionView
 * 作者: oubowu
 * 时间： 2016/1/13 12:04
 * 功能：
 * svn版本号:$$Rev$$
 * 更新时间:$$Date$$
 * 更新人:$$Author$$
 * 更新描述:
 */
public class ClipRegionView extends View {

    private final Paint mTextPaint;
    private final float mTextOffset;
    private String mDesc = "DIFFERENCE,最终区域为第一个区域与第二个区域不同的区域";
    private Rect mRegionA, mRegionB;// 区域A和区域B对象
    private Paint mPaint;// 绘制边框的Paint
    private Region.Op mOp = Region.Op.DIFFERENCE;
    private int mHeight;
    private int mWidth;

    public ClipRegionView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // 实例化画笔并设置属性
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(2);

        // 实例化区域A和区域B
        mRegionA = new Rect(100, 100, 300, 300);
        mRegionB = new Rect(200, 200, 400, 400);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setTextSize(25);
        mTextPaint.setColor(Color.WHITE);
        final Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        mTextOffset = -fontMetrics.ascent;
    }

    // clipPath(Path path, Region.Op op)
    // clipRect(Rect rect, Region.Op op)
    // clipRect(RectF rect, Region.Op op)
    // clipRect(float left, float top, float right, float bottom, Region.Op op)
    // clipRegion(Region region, Region.Op op)
    // 要明白这些方法的Region.Op参数那么首先要了解Region为何物。Region的意思是“区域”，在Android里呢它同样表示的是一块封闭的区域，
    // Region中的方法都非常的简单，我们重点来瞧瞧Region.Op，Op是Region的一个枚举类，里面呢有六个枚举常量：
    // 那么Region.Op究竟有什么用呢？其实它就是个组合模式，在1/6中我们曾学过一个叫图形混合模式的，而在本节开头我们也曾讲过Rect也有类似的组合方法
    // DIFFERENCE,INTERSECT,REPLACE,REVERSE_DIFFERENCE,UNION,XOR

    // Region和Rect有什么区别呢？首先最重要的一点，Region表示的是一个区域，而Rect表示的是一个矩形，这是最根本的区别之一，
    // 其次，Region有个很特别的地方是它不受Canvas的变换影响，Canvas的local不会直接影响到Region自身

    // 缩放画布
    // canvas.scale(0.75F, 0.75F);
    // canvas.save();
    // 裁剪矩形
    // canvas.clipRect(mRect);
    // canvas.drawColor(Color.RED);
    // canvas.restore();
    // canvas.save();
    // 裁剪区域
    // canvas.clipRegion(mRegion);
    // canvas.drawColor(Color.RED);
    // canvas.restore();
    // Rect随着Canvas的缩放一起缩放了，但是Region不会受Canvas的影响

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = h;
        mWidth = w;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 填充颜色
        canvas.drawColor(ContextCompat.getColor(getContext(), R.color.material_deep_orange_300));

        canvas.save();

        // 裁剪区域A
        // clipRegion被废弃掉了，而且使用起来y坐标有问题
        //  canvas.clipRegion(mRegionA);
        canvas.clipRect(mRegionA);
        // 再通过组合方式裁剪区域B
        //  canvas.clipRegion(mRegionB, mOp);
        canvas.clipRect(mRegionB, mOp);

        // 填充颜色
        canvas.drawColor(Color.RED);

        canvas.restore();

        // 绘制框框帮助我们观察
        canvas.drawRect(100, 100, 300, 300, mPaint);
        canvas.drawRect(200, 200, 400, 400, mPaint);

        canvas.drawText(mDesc, canvas.getWidth() / 2, canvas.getHeight() - mTextOffset, mTextPaint);
    }

    public void setRegionOp(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_difference:
                mOp = Region.Op.DIFFERENCE;
                break;
            case R.id.action_intersect:
                mOp = Region.Op.INTERSECT;
                break;
            case R.id.action_replace:
                mOp = Region.Op.REPLACE;
                break;
            case R.id.action_reverse_difference:
                mOp = Region.Op.REVERSE_DIFFERENCE;
                break;
            case R.id.action_union:
                mOp = Region.Op.UNION;
                break;
            case R.id.action_xor:
                mOp = Region.Op.XOR;
                break;
        }
        mDesc = item.getTitle().toString();
        invalidate();
    }

}
