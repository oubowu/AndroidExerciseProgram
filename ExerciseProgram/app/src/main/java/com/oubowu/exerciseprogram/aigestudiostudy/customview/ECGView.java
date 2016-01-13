package com.oubowu.exerciseprogram.aigestudiostudy.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.socks.library.KLog;

/**
 * ClassName:
 * Author:oubowu
 * Fuction:
 * CreateDate:2016/1/10 15:51
 * UpdateUser:
 * UpdateDate:
 */
public class ECGView extends View {
    private Paint mPaint;// 画笔
    private Path mPath;// 路径对象

    private float x, y;// 路径初始坐标
    private float initScreenW;// 屏幕初始宽度
    private float initX;// 初始X轴坐标
    private float transX, moveX;// 画布移动的距离

    private boolean isCanvasMove;// 画布是否需要平移

    public ECGView(Context context, AttributeSet set) {
        super(context, set);

        setLayerType(LAYER_TYPE_SOFTWARE, null);

        /*
         * 实例化画笔并设置属性
         */
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setColor(Color.GREEN);
        mPaint.setStrokeWidth(5);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStyle(Paint.Style.STROKE);
        // 绘制的图形添加一个阴影层效果
        mPaint.setShadowLayer(7, 0, 0, Color.GREEN);

        // CornerPathEffect、DiscretePathEffect、DashPathEffect、PathDashPathEffect、ComposePathEffect、SumPathEffect
        mPaint.setPathEffect(new CornerPathEffect(5));

        mPath = new Path();
        transX = 0;
        isCanvasMove = false;
    }

    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        /*
         * 获取屏幕宽高
         */
        int screenW = w;
        int screenH = h;

        /*
         * 设置起点坐标
         */
        x = 0;
        y = (screenH / 2) /*+ (screenH / 4) + (screenH / 10)*/;

        // 屏幕初始宽度
        initScreenW = screenW;

        // 初始X轴坐标
        initX = ((screenW / 2) + (screenW / 4));

        moveX = (screenW / 24);

        mPath.moveTo(x, y);

        KLog.e("onSizeChanged");

    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);

        mPath.lineTo(x, y);

        // 向左平移画布
        canvas.translate(-transX, 0);

        // 计算坐标
        calCoors();

        // 绘制路径
        canvas.drawPath(mPath, mPaint);
        invalidate();
    }

    /**
     * 计算坐标
     */
    private void calCoors() {
        if (isCanvasMove) {
            // 每次移动画布四个像素
            transX += 4;
        }

        if (x < initX) {
            // 曲线走直线
            x += 8;
        } else {
            if (x < initX + moveX) {
                // 曲线往上走
                x += 2;
                y -= 8;
            } else {
                if (x < initX + (moveX * 2)) {
                    // 曲线往下走
                    x += 2;
                    y += 14;
                } else {
                    if (x < initX + (moveX * 3)) {
                        // 曲线往上走
                        x += 2;
                        y -= 12;
                    } else {
                        if (x < initX + (moveX * 4)) {
                            // 曲线往下走
                            x += 2;
                            y += 6;
                        } else {
                            if (x < initScreenW) {
                                // 曲线走直线
                                x += 8;
                            } else {
                                // x大于屏幕宽度时，移动画布，此时初始的转折点坐标加上宽度
                                isCanvasMove = true;
                                initX = initX + initScreenW;
                            }
                        }
                    }
                }
            }

        }

    }
}
