package com.oubowu.exerciseprogram.aigestudiostudy;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.oubowu.exerciseprogram.utils.MeasureUtil;

/**
 * ClassName:
 * Author:oubowu
 * Fuction:
 * CreateDate:2016/1/10 15:30
 * UpdateUser:
 * UpdateDate:
 */
public class ShadowView extends View {

    private static final int RECT_SIZE = 500;
    private Paint mPaint;

    public ShadowView(Context context) {
        super(context);
        init();
    }

    public ShadowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ShadowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setLayerType(LAYER_TYPE_SOFTWARE, null);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);
        // radius表示阴影的扩散半径，而dx和dy表示阴影平面上的偏移值，shadowColor就不说了阴影颜色，最后提醒一点setShadowLayer同样不支持HW哦
        mPaint.setShadowLayer(10, 10, 10, Color.DKGRAY);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        canvas.drawRect((MeasureUtil.getScreenWidth((Activity) getContext()) - RECT_SIZE) / 2,
                (MeasureUtil.getScreenHeight((Activity) getContext()) - MeasureUtil.getStatusBarHeight(getContext()) - RECT_SIZE) / 2,
                (MeasureUtil.getScreenWidth((Activity) getContext()) - RECT_SIZE) / 2 + RECT_SIZE,
                (MeasureUtil.getScreenHeight((Activity) getContext()) - MeasureUtil.getStatusBarHeight(getContext()) - RECT_SIZE) / 2 + RECT_SIZE,
                mPaint);
    }
}
