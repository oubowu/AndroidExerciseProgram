package com.oubowu.exerciseprogram.aigestudiostudy.customview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.oubowu.exerciseprogram.R;
import com.oubowu.exerciseprogram.utils.MeasureUtil;

/**
 * 类名： MatrixImageView
 * 作者: oubowu
 * 时间： 2016/1/11 13:53
 * 功能：
 * svn版本号:$$Rev$$
 * 更新时间:$$Date$$
 * 更新人:$$Author$$
 * 更新描述:
 */
public class MatrixImageView extends ImageView {

    private static final int MODE_NONE = 0x00123;
    // 拖拽模式
    private static final int MODE_DRAG = 0x00124;
    // 缩放or旋转模式
    private static final int MODE_ZOOM = 0x00125;

    private int mCurrentMode;

    // 上次手指移动的距离
    private float mPreMove = 1f;
    // 保存的角度值
    private float mSaveRotate = 0f;
    // 旋转的角度
    private float mRotate = 0f;

    // 上一次各触摸点的坐标集合
    private float[] mPreEventCoor;

    // 起点终点
    private PointF mStart, mMid;

    private Matrix mCurrentMatrix, mSavedMatrix;
    private Context mContext;
    private boolean pointerUp;

    public MatrixImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        init();
    }

    private void init() {
        mCurrentMatrix = new Matrix();
        mSavedMatrix = new Matrix();

        mStart = new PointF();
        mMid = new PointF();

        mCurrentMode = MODE_NONE;

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.mipmap.ic_baoqiang, options);
        Bitmap bitmap = MeasureUtil.decodeSampledBitmapFromResource(getResources(), R.mipmap.ic_baoqiang,
                MeasureUtil.getScreenWidth((Activity) getContext()),
                MeasureUtil.getScreenWidth((Activity) getContext()) * options.outHeight / options.outWidth);

        setBackgroundColor(Color.WHITE);
        setImageBitmap(bitmap);
        // 这一句话一定不能少
        setScaleType(ScaleType.MATRIX);

    }

    //  matrix.preScale(0.5f, 1);
    //  matrix.setScale(1, 0.6f);
    //  matrix.postScale(0.7f, 1);
    //  matrix.preTranslate(15, 0);
    //  那么Matrix的计算过程即为：translate (15, 0) -> scale (1, 0.6f) -> scale (0.7f, 1)，我们说过set会重置数据，所以最开始的
    //  matrix.preScale(0.5f, 1)被后面的setScale重置了

    // 同样地，对于类似的变换：
    // matrix.preScale(0.5f, 1);
    // matrix.preTranslate(10, 0);
    // matrix.postScale(0.7f, 1);
    // matrix.postTranslate(15, 0);
    // 其计算过程为：translate (10, 0) -> scale (0.5f, 1) -> scale (0.7f, 1) -> translate (15, 0)

    // Matrix的应用是相当广泛的，不仅仅是在我们的Shader，我们的canvas也有setMatrix(matrix)方法来设置矩阵变换，
    // 更常见的是在ImageView中对ImageView进行变换，当我们手指在屏幕上划过一定的距离后根据这段距离来平移我们的控件，
    // 根据两根手指之间拉伸的距离和相对于上一次旋转的角度来缩放旋转我们的图片

    // Matrix矩阵最后的3个数是用来设置透视变换的，为什么最后一个值恒为1？因为其表示的是在Z轴向的透视缩放，这三个值都可以被设置，
    // 前两个值跟右手坐标系的XY轴有关，大家可以尝试去发现它们之间的规律，我就不多说了。这里多扯一点，大家一定要学会如何透过现象看本质
    // ，即便看到的本质不一定就是实质，但是离实质已经不远了，不要一来就去追求什么底层源码啊、逻辑什么的，就像上面的矩阵变换一样，
    // 矩阵的9个数作用其实很多人都说不清，与其听别人胡扯还不如自己动手试试你说是吧，不然苦逼的只是你自己。
    //在实际应用中我们极少会使用到Matrix的尾三数做透视变换，更多的是使用Camare摄像机

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // ACTION_MASK在Android中是应用于多点触摸操作，字面上的意思大概是动作掩码的意思吧。
        // 在onTouchEvent(MotionEvent event)中，使用switch (event.getAction())可以处理ACTION_DOWN和ACTION_UP事件；
        // 使用switch (event.getAction() & MotionEvent.ACTION_MASK)就可以处理处理多点触摸的ACTION_POINTER_DOWN和ACTION_POINTER_UP事件。
        // ACTION_DOWN和ACTION_UP就是单点触摸屏幕，按下去和放开的操作；
        // ACTION_POINTER_DOWN和ACTION_POINTER_UP就是多点触摸屏幕，当有一只手指按下去的时候，另一只手指按下和放开的动作捕捉；
        // ACTION_MOVE就是手指在屏幕上移动的操作；
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                // 单指触摸屏幕
                // 保存上次的矩阵
                mSavedMatrix.set(mCurrentMatrix);
                // 设置起点
                mStart.set(event.getX(), event.getY());
                // 接下来的模式是单指拖拽模式
                mCurrentMode = MODE_DRAG;
                // 清空上一次各触摸点的坐标
                mPreEventCoor = null;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                // 双指触摸屏幕
                // 算出两个触摸点的距离
                mPreMove = calSpacing(event);
                if (mPreMove > 10f) {
                    // 距离超过十个像素
                    mSavedMatrix.set(mCurrentMatrix);
                    // 算出触摸点的中点
                    calMidPoint(mMid, event);
                    // 接下来的模式是缩放or旋转模式
                    mCurrentMode = MODE_ZOOM;
                }
                // 记录各触摸点的坐标
                mPreEventCoor = new float[4];
                mPreEventCoor[0] = event.getX(0);
                mPreEventCoor[1] = event.getX(1);
                mPreEventCoor[2] = event.getY(0);
                mPreEventCoor[3] = event.getY(1);
                // 算出双指按下时的旋转角度作为接下来的基准
                mSaveRotate = calRotation(event);
                break;
            case MotionEvent.ACTION_UP:
                // 单点离开屏幕时
                mCurrentMode = MODE_NONE;
                // 清空上一次各触摸点的坐标
                mPreEventCoor = null;
                break;
            case MotionEvent.ACTION_POINTER_UP:
//                KLog.e(event.getActionIndex());
                // 第二个点离开屏幕时
                if (event.getPointerCount() == 2) {
                    // 双指情况下，单指抬起时getPointerCount为2，双指抬起时getPointerCount为2再为1
                    mCurrentMode = MODE_DRAG;
                    mSavedMatrix.set(mCurrentMatrix);
                    pointerUp = true;
                } else if (event.getPointerCount() == 1) {
                    mCurrentMode = MODE_NONE;
                }
                mPreEventCoor = null;
                break;
            case MotionEvent.ACTION_MOVE:
                // 触摸点移动时
                if (mCurrentMode == MODE_DRAG) {
                    // 单点触控拖拽平移
                    // 先设置上次的矩阵保持状态
                    mCurrentMatrix.set(mSavedMatrix);
                    if (pointerUp) {
                        // 针对双指抬起一个手指的情况，起点更新为move抬起后的第一次坐标
                        pointerUp = false;
                        mStart.set(event.getX(), event.getY());
                    }
                    // 算出移动的位移
                    float dx = event.getX() - mStart.x;
                    float dy = event.getY() - mStart.y;
                    // 矩阵设置位移量
                    mCurrentMatrix.postTranslate(dx, dy);
                } else if (mCurrentMode == MODE_ZOOM && event.getPointerCount() == 2) {
                    // 两点触控缩放or旋转
                    // 算出两个触摸点的距离
                    float currentMove = calSpacing(event);
                    // 先设置上次的矩阵保持状态
                    mCurrentMatrix.set(mSavedMatrix);
                    if (mCurrentMode > 10f) {
                        // 指尖移动距离大于10F进行缩放
                        // 缩放比例为移动的距离与双指按下时的距离的比值
                        float scale = currentMove / mPreMove;
                        // 缩放的原点为双指按下时的算出的中点
                        mCurrentMatrix.postScale(scale, scale, mMid.x, mMid.y);
                    }
                    if (mPreEventCoor != null) {
                        // 算出旋转的角度值
                        mRotate = calRotation(event);
                        // 角度差为现在旋转的角度值与双指按下时的旋转角度的差值
                        float r = mRotate - mSaveRotate;
                        // 旋转的原点为屏幕中心
                        mCurrentMatrix.postRotate(r, getMeasuredWidth() / 2, getMeasuredHeight() / 2);
                    }
                }
                break;
        }
        setImageMatrix(mCurrentMatrix);
        return true;
    }

    //  计算旋转角度
    private float calRotation(MotionEvent event) {
        double deltaX = (event.getX(0) - event.getX(1));
        double deltaY = (event.getY(0) - event.getY(1));
        // 相比较ATan，ATan2究竟有什么不同？本篇介绍一下ATan2的用法及使用条件。
        // 对于tan(θ) = y / x:
        // θ = ATan(y / x)求出的θ取值范围是[-PI/2, PI/2]。
        // θ = ATan2(y, x)求出的θ取值范围是[-PI, PI]。
        // 当 (x, y) 在第一象限, 0 < θ < PI/2.
        // 当 (x, y) 在第二象限 PI/2 < θ≤PI.
        // 当 (x, y) 在第三象限, -PI < θ < -PI/2.
        // 当 (x, y) 在第四象限, -PI/2 < θ < 0.
        // 当点(x, y)在象限的边界也就是坐标轴上时:
        // 当 y 是 0，x 为非负值, θ = 0.
        // 当 y 是 0， x 是 负值, θ = PI.
        // 当 y 是 正值， x 是 0, θ = PI/2.
        // 当 y 是 负值， x 是 0, θ = -PI/2.
        // 由此可知，一般情况下用ATan即可，当对所求出角度的取值范围有特殊要求时，应使用ATan2。
        double radius = Math.atan2(deltaY, deltaX);
        // 转换以弧度为单位测得的角度大致相等的角度,以度衡量
        return (float) Math.toDegrees(radius);
    }

    // 计算两个触摸点的中点坐标 ，用来做缩放的原点
    private void calMidPoint(PointF mMid, MotionEvent event) {
        final float x = event.getX(0) + event.getX(1);
        final float y = event.getY(0) + event.getY(1);
        mMid.set(x / 2.0f, y / 2.0f);
    }

    // 计算两个触摸点的距离
    private float calSpacing(MotionEvent event) {
        final float x = event.getX(0) - event.getX(1);
        final float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }
}
