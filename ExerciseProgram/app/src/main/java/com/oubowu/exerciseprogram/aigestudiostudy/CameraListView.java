package com.oubowu.exerciseprogram.aigestudiostudy;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.widget.ListView;

public class CameraListView extends ListView {
    private Camera mCamera;
    private Matrix mMatrix;

    public CameraListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mCamera = new Camera();
        mMatrix = new Matrix();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mCamera.save();
        // 沿X轴旋转30°,外向里方向
        mCamera.rotate(30, 0, 0);
        mCamera.getMatrix(mMatrix);
        mMatrix.preTranslate(-getWidth() / 2, -getHeight() / 2);
        mMatrix.postTranslate(getWidth() / 2, getHeight() / 2);
        canvas.concat(mMatrix);
        super.onDraw(canvas);
        mCamera.restore();
    }
}
