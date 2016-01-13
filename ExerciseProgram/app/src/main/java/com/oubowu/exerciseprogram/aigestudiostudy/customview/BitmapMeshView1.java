package com.oubowu.exerciseprogram.aigestudiostudy.customview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.oubowu.exerciseprogram.R;
import com.oubowu.exerciseprogram.utils.MeasureUtil;

/**
 * 类名： BitmapMeshView
 * 作者: oubowu
 * 时间： 2016/1/12 13:48
 * 功能：
 * svn版本号:$$Rev$$
 * 更新时间:$$Date$$
 * 更新人:$$Author$$
 * 更新描述:
 */
public class BitmapMeshView1 extends View {

    private final int mWidth;
    private final int mHeight;

    // 分割数
    private static final int WIDTH = 9, HEIGHT = 9;
    // 交点数
    private static final int COUNT = (WIDTH + 1) * (HEIGHT + 1);
    // 位图对象
    private Bitmap mBitmap;
    // 基准点坐标数组
    private float[] matrixOriganal = new float[COUNT * 2];
    // 变换后点坐标数组
    private float[] matrixMoved = new float[COUNT * 2];
    // 触摸屏幕时手指的xy坐标
    private float clickX, clickY;
    // 基准点、变换点和线段的绘制Paint
    private Paint origPaint, movePaint, linePaint;

    public BitmapMeshView1(Context context, AttributeSet attrs) {
        super(context, attrs);

        mWidth = MeasureUtil.getScreenWidth((Activity) getContext());
        mHeight = MeasureUtil.getScreenHeight((Activity) getContext())
                - MeasureUtil.getToolbarHeight(context) - MeasureUtil.getStatusBarHeight(context);

        mBitmap = MeasureUtil.decodeSampledBitmapFromResource(getResources(),
                R.mipmap.ic_pal,
                (int) (mWidth / 1.5f),
                (int) (mHeight / 1.5f));

        // 实例画笔并设置颜色
        origPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        origPaint.setColor(0xfff57f17);
        movePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        movePaint.setColor(0xffFF0000);
        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(0xFF00bfa5);

        // Y均分的长度
        float multipleY = mBitmap.getHeight() * 1.0f / HEIGHT;
        // X均分的长度
        float multipleX = mBitmap.getWidth() * 1.0f / WIDTH;
        // 生成各个交点坐标
        int index = 0;
        for (int y = 0; y <= HEIGHT; y++) {
            float fy = multipleY * y;
            for (int x = 0; x <= WIDTH; x++) {
                float fx = multipleX * x;
                setXY(matrixOriganal, fx, fy, index);
                setXY(matrixMoved, fx, fy, index);
                index++;
            }
        }

    }

    /**
     * 计算分割后的交点坐标
     *
     * @param fx    x坐标
     * @param fy    y坐标
     * @param index 标示值
     * @param arr
     */
    private void setXY(float[] arr, float fx, float fy, int index) {
        // 这样子相邻两个数为存储的xy值，偶数下标为x，奇数下标为y
        arr[2 * index] = fx;
        arr[2 * index + 1] = fy;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.DKGRAY);
        canvas.save();
        // 因为drawBitmapMesh默认原点在（0,0），要想居中的话把画布平移
        canvas.translate(mWidth / 2 - mBitmap.getWidth() / 2, mHeight / 2 - mBitmap.getHeight() / 2);
        // drawBitmapMesh参数中有个vertOffset，该参数是verts数组的偏移值，意为从第一个元素开始才对位图就行变化，
        // 这些大家自己去尝试下吧，还有colors和colorOffset，类似。
        // drawBitmapMesh说实话真心很屌，但是计算复杂确是个鸡肋，这么屌的一个方法被埋没其实是由原因可循的，高不成低不就，
        // 如上所示，有些变换我们可以使用Matrix等其他方法简单实现，但是drawBitmapMesh就要通过一些列计算，太复杂。那真要做复杂的图形效果呢，
        // 考虑到效率我们又会首选OpenGL……这真是一个悲伤的故事……无论怎样，请记住这位烈士一样的方法…………总有用处的
        canvas.drawBitmapMesh(mBitmap, WIDTH, HEIGHT, matrixMoved, 0, null, 0, null);
        // 绘制参考元素
        drawGuide(canvas);
        canvas.restore();
    }

    private void drawGuide(Canvas canvas) {
        for (int i = 0; i < COUNT * 2; i += 2) {
            // 绘制原图的交点
            final float x = matrixOriganal[i];
            final float y = matrixOriganal[i + 1];
            canvas.drawCircle(x, y, 4, origPaint);

            final float x1 = matrixMoved[i];
            final float y1 = matrixMoved[i + 1];
            // 绘制原图的交点到移动后的交点间的连线
            canvas.drawLine(x, y, x1, y1, linePaint);
        }

        for (int i = 0; i < COUNT * 2; i += 2) {
            // 绘制原图的交点
            final float x = matrixMoved[i];
            final float y = matrixMoved[i + 1];
            canvas.drawCircle(x, y, 4, movePaint);
        }

        canvas.drawCircle(clickX, clickY, 8, linePaint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        clickX = event.getX();
        clickY = event.getY();
        if (clickX >= mWidth / 2 - mBitmap.getWidth() / 2
                && clickX <= mWidth / 2 - mBitmap.getWidth() / 2 + mBitmap.getWidth()
                && clickY >= mHeight / 2 - mBitmap.getHeight() / 2
                && clickY <= mHeight / 2 - mBitmap.getHeight() / 2 + mBitmap.getHeight()) {
            clickX -= mWidth / 2 - mBitmap.getWidth() / 2;
            clickY -= mHeight / 2 - mBitmap.getHeight() / 2;
            sumdge();
            invalidate();
        }
        return true;
    }

    // 计算变化的坐标
    private void sumdge() {
        for (int i = 0; i < COUNT * 2; i += 2) {
            final float oriX = matrixOriganal[i];
            final float oriY = matrixOriganal[i + 1];
            // 按下的坐标与原来的坐标的偏移量
            final float oridx = clickX - oriX;
            final float oridy = clickY - oriY;

            // 位移xy的平方
            final float kv = oridx * oridx + oridy * oridy;
            //计算扭曲度，距离当前点(cx,cy)越远，扭曲度越小
            // Math.sqrt(kv)为点击点与原点的距离
            // 1000000值越大，扭曲得越明显
            final float pull = (float) (1000000 / kv / Math.sqrt(kv));

            if (pull >= 1) {
                matrixMoved[i] = clickX;
                matrixMoved[i + 1] = clickY;
            } else {
                matrixMoved[i] = oriX + oridx * pull;
                matrixMoved[i + 1] = oriY + oridy * pull;
            }

        }
    }
}
