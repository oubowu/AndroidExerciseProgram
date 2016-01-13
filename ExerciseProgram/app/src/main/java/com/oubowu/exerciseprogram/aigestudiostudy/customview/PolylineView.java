package com.oubowu.exerciseprogram.aigestudiostudy.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 类名： PolylineView
 * 作者: oubowu
 * 时间： 2016/1/13 10:07
 * 功能：
 * svn版本号:$$Rev$$
 * 更新时间:$$Date$$
 * 更新人:$$Author$$
 * 更新描述:
 */
public class PolylineView extends View {

    private static final float LEFT = 1 / 16F, TOP = 1 / 16F, RIGHT = 15 / 16F, BOTTOM = 7 / 8F;// 网格区域相对位置
    private static final float TIME_X = 3 / 32F, TIME_Y = 1 / 16F, MONEY_X = 31 / 32F, MONEY_Y = 15 / 16F;// 文字坐标相对位置
    private static final float TEXT_SIGN = 1 / 32F;// 文字相对大小
    private static final float THICK_LINE_WIDTH = 1 / 128F, THIN_LINE_WIDTH = 1 / 512F;// 粗线和细线相对大小

    private TextPaint mTextPaint;// 文字画笔
    private Paint linePaint, pointPaint;// 线条画笔和点画笔
    private Path mPath;// 路径对象
    private Bitmap mBitmap;// 绘制曲线的Btimap对象
    private Canvas mCanvas;// 装载mBitmap的Canvas对象

    private List<PointF> pointFs;// 数据列表
    private float[] rulerX, rulerY;// xy轴向刻度

    private String signX, signY;// 设置X和Y坐标分别表示什么的文字
    private float textY_X, textY_Y, textX_X, textX_Y;// 文字坐标
    private float textSignSzie;// xy坐标标识文本字体大小
    private float thickLineWidth, thinLineWidth;// 粗线和细线宽度
    private float left, top, right, bottom;// 网格区域左上右下两点坐标
    private int viewSize;// 控件尺寸
    private float maxX = Float.MIN_VALUE, maxY = Float.MIN_VALUE;// 横纵轴向最大刻度
    private float spaceX, spaceY;// 刻度间隔

    public PolylineView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // 实例化文本画笔并设置参数
        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.LINEAR_TEXT_FLAG);
        mTextPaint.setColor(Color.WHITE);

        // 实例化线条画笔并设置参数
        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setColor(Color.WHITE);

        // 实例化点画笔并设置参数
        pointPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        pointPaint.setStyle(Paint.Style.FILL);
        pointPaint.setColor(Color.WHITE);

        // 实例化Path对象
        mPath = new Path();

        // 实例化Canvas对象
        mCanvas = new Canvas();

        initData();
    }

    /**
     * 初始化数据支撑
     * View初始化时可以考虑给予一个模拟数据
     * 当然我们可以通过setData方法设置自己的数据
     */
    private void initData() {
        signX = "Money";
        signY = "Time";
        Random random = new Random();
        pointFs = new ArrayList<PointF>();
        for (int i = 0; i < 20; i++) {
            PointF pointF = new PointF();

            pointF.x = (float) (random.nextInt(100) * i);
            pointF.y = (float) (random.nextInt(100) * i);

            pointFs.add(pointF);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // Y轴文字坐标
        textY_X = TIME_X * w;
        textY_Y = TIME_Y * w;
        // X轴文字坐标
        textX_X = MONEY_X * w;
        textX_Y = MONEY_Y * w;
        // XY轴刻度的文字大小
        textSignSzie = TEXT_SIGN * w;
        // 粗线和细线宽度
        thickLineWidth = THICK_LINE_WIDTH * w;
        thinLineWidth = THIN_LINE_WIDTH * w;
        // 网格区域左上右下两点坐标
        left = LEFT * w;
        top = TOP * w;
        right = RIGHT * w;
        bottom = BOTTOM * w;

        viewSize = w;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 填充背景
        canvas.drawColor(0xFF9596C4);

        // 绘制标识元素
        drawSign(canvas);

        // 绘制网格
        drawGrid(canvas);

        // 绘制曲线
        drawPolyline(canvas);
    }

    // 绘制XY轴文字
    private void drawSign(Canvas canvas) {
        mTextPaint.setTextSize(textSignSzie);
        // X轴文字
        mTextPaint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText(signX, textX_X, textX_Y, mTextPaint);
        // Y轴文字
        mTextPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(signY, textY_X, textY_Y, mTextPaint);
    }

    /**
     * 绘制网格
     *
     * @param canvas 画布
     */
    private void drawGrid(Canvas canvas) {
        // 锁定画布
        canvas.save();

        // 设置线条画笔宽度
        linePaint.setStrokeWidth(thickLineWidth);

        // 计算xy轴Path
        mPath.moveTo(left, top);
        mPath.lineTo(left, bottom);
        mPath.lineTo(right, bottom);

        // 绘制xy轴
        canvas.drawPath(mPath, linePaint);

        // 绘制线条
        drawLines(canvas);

        // 释放画布
        canvas.restore();
    }

    /**
     * 绘制网格
     *
     * @param canvas 画布
     */
    private void drawLines(Canvas canvas) {
        // 计算刻度文字尺寸
        float textRulerSize = textSignSzie / 2F;

        // 重置文字画笔文字尺寸
        mTextPaint.setTextSize(textRulerSize);

        // 重置线条画笔描边宽度
        linePaint.setStrokeWidth(thinLineWidth);

        // 获取数据长度
        int count = pointFs.size();

        // 计算除数的值为数据长度减一
        int divisor = count - 1;

        // 计算横轴数据最大值
        maxX = 0;
        for (int i = 0; i < count; i++) {
            if (maxX < pointFs.get(i).x) {
                maxX = pointFs.get(i).x;
            }
        }

        // 计算横轴最近的能被count整除的值
        int remainderX = ((int) maxX) % divisor;
        maxX = remainderX == 0 ? ((int) maxX) : divisor - remainderX + ((int) maxX);

        // 计算纵轴数据最大值
        maxY = 0;
        for (int i = 0; i < count; i++) {
            if (maxY < pointFs.get(i).y) {
                maxY = pointFs.get(i).y;
            }
        }

        // 计算纵轴最近的能被count整除的值
        int remainderY = ((int) maxY) % divisor;
        maxY = remainderY == 0 ? ((int) maxY) : divisor - remainderY + ((int) maxY);

        // 生成横轴刻度值
        rulerX = new float[count];
        for (int i = 0; i < count; i++) {
            rulerX[i] = maxX / divisor * i;
        }

        // 生成纵轴刻度值
        rulerY = new float[count];
        for (int i = 0; i < count; i++) {
            rulerY[i] = maxY / divisor * i;
        }

        // 计算横纵坐标刻度间隔
        spaceY = viewSize * (BOTTOM - TOP) / count;
        spaceX = viewSize * (RIGHT - LEFT) / count;

        // 锁定画布并设置画布透明度为75%
        int sc = canvas.saveLayerAlpha(0, 0, canvas.getWidth(), canvas.getHeight(), 75, Canvas.ALL_SAVE_FLAG);

        // 绘制横纵线段
        for (float y = viewSize * BOTTOM - spaceY; y > viewSize * TOP; y -= spaceY) {
            for (float x = viewSize * LEFT; x < viewSize * RIGHT; x += spaceX) {
                /*
                 * 绘制纵向线段
                 */
                if (y == viewSize * TOP + spaceY) {
                    canvas.drawLine(x, y, x, y + spaceY * (count - 1), linePaint);
                }

                /*
                 * 绘制横向线段
                 */
                if (x == viewSize * RIGHT - spaceX) {
                    canvas.drawLine(x, y, x - spaceX * (count - 1), y, linePaint);
                }
            }
        }

        // 还原画布
        canvas.restoreToCount(sc);

        // 绘制横纵轴向刻度值
        int index_x = 0, index_y = 1;
        for (float y = viewSize * BOTTOM - spaceY; y > viewSize * TOP; y -= spaceY) {
            for (float x = viewSize * LEFT; x < viewSize * RIGHT; x += spaceX) {
                /*
                 * 绘制横轴刻度数值
                 */
                if (y == viewSize * BOTTOM - spaceY) {
                    mTextPaint.setTextAlign(Paint.Align.RIGHT);
                    canvas.drawText(String.valueOf(rulerX[index_x]), x, y + textSignSzie + spaceY, mTextPaint);
                }

                /*
                 * 绘制纵轴刻度数值
                 */
                if (x == viewSize * LEFT) {
                    mTextPaint.setTextAlign(Paint.Align.RIGHT);
                    canvas.drawText(String.valueOf(rulerY[index_y]), x - thickLineWidth, y + textRulerSize, mTextPaint);
                }

                index_x++;
            }
            index_y++;
        }
    }

    /**
     * 绘制曲线
     * 这里我使用一个新的Bitmap对象结合新的Canvas对象来绘制曲线
     * 当然你可以直接在原来的canvas（onDraw传来的那个）中直接绘制如果你还没被坐标搞晕的话……
     *
     * @param canvas 画布
     */
    private void drawPolyline(Canvas canvas) {
        // 生成一个Bitmap对象大小和我们的网格大小一致
        mBitmap = Bitmap.createBitmap((int) (viewSize * (RIGHT - LEFT) - spaceX), (int) (viewSize * (BOTTOM - TOP) - spaceY), Bitmap.Config.ARGB_8888);

        // 将Bitmap注入Canvas
        mCanvas.setBitmap(mBitmap);

        // 为画布填充一个半透明的红色
        mCanvas.drawARGB(75, 255, 0, 0);

        // 重置曲线
        mPath.reset();

        /*
         * 生成Path和绘制Point
         */
        for (int i = 0; i < pointFs.size(); i++) {
            // 计算x坐标
            float x = mCanvas.getWidth() / maxX * pointFs.get(i).x;

            // 计算y坐标
            float y = mCanvas.getHeight() / maxY * pointFs.get(i).y;
            y = mCanvas.getHeight() - y;

            // 绘制小点点
            mCanvas.drawCircle(x, y, thickLineWidth, pointPaint);

            /*
             * 如果是第一个点则将其设置为Path的起点
             */
            if (i == 0) {
                mPath.moveTo(x, y);
            }

            // 连接各点
            mPath.lineTo(x, y);
        }

        // 设置PathEffect
        linePaint.setPathEffect(new CornerPathEffect(200));

        // 重置线条宽度
        linePaint.setStrokeWidth(thickLineWidth);

        // 将Path绘制到我们自定的Canvas上
        mCanvas.drawPath(mPath, linePaint);

        // 将mBitmap绘制到原来的canvas
        canvas.drawBitmap(mBitmap, left, top + spaceY, null);
    }

    /**
     * 设置数据
     *
     * @param pointFs 点集合
     */
    public synchronized void setData(List<PointF> pointFs, String signX, String signY) {
        /*
         * 数据为空直接GG
         */
        if (null == pointFs || pointFs.size() == 0)
            throw new IllegalArgumentException("No data to display !");

        /*
         * 控制数据长度不超过10个
         * 对于折线图来说数据太多就没必要用折线图表示了而是使用散点图
         */
        if (pointFs.size() > 10)
            throw new IllegalArgumentException("The data is too long to display !");

        // 设置数据并重绘视图
        this.pointFs = pointFs;
        this.signX = signX;
        this.signY = signY;
        invalidate();
    }

}
