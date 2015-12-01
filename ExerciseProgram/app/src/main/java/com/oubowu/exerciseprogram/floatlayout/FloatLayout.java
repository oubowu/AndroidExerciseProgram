package com.oubowu.exerciseprogram.floatlayout;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.oubowu.exerciseprogram.R;
import com.socks.library.KLog;

import java.util.Random;

/**
 * ClassName:
 * Author:oubowu
 * Fuction:
 * CreateDate:2015/11/29 15:59
 * UpdateUser:
 * UpdateDate:
 */
public class FloatLayout extends RelativeLayout {

    private Drawable mLeafs[];

    private Interpolator mInterpolator[];

    private RelativeLayout.LayoutParams mLp;

    private int mWidthMode;
    private int mWidthSize;

    private int mHeightMode;
    private int mHeightSize;

    private int mScreenWidth;
    private int mScreenHeight;
    private ImageView mLeaf;
    private ImageView mTree;


    public FloatLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mLeafs = new Drawable[]{getResources().getDrawable(R.mipmap.leaf_1), getResources().getDrawable(R.mipmap.leaf_2), getResources().getDrawable(R.mipmap.leaf_3), getResources().getDrawable(R.mipmap.leaf_4)};
        mLp = new RelativeLayout.LayoutParams(mLeafs[0].getIntrinsicWidth(), mLeafs[0].getIntrinsicHeight());

        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);
        mScreenWidth = metrics.widthPixels;
        mScreenHeight = metrics.heightPixels;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.mipmap.tree, options);
        final int outWidth = options.outWidth;
        final int outHeight = options.outHeight;
        int inSampleSize = 1;
        if (outWidth > mScreenWidth || outHeight > mScreenHeight) {
            final int widthRatio = outWidth / mScreenWidth;
            final int heightRatio = outHeight / mScreenHeight;
            inSampleSize = Math.min(widthRatio, heightRatio);
        }
        options.inSampleSize = inSampleSize == 0 ? 1 : inSampleSize;
        options.inJustDecodeBounds = false;
        mTree = new ImageView(getContext());
        final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.tree, options);
        mTree.setBackgroundDrawable(new BitmapDrawable(bitmap));
        addView(mTree, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        mInterpolator = new Interpolator[]{new AccelerateDecelerateInterpolator(), new AccelerateInterpolator(), new DecelerateInterpolator(), new LinearInterpolator()};

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(mWidthSize = measureWidth(widthMeasureSpec), mHeightSize = measureHeight(heightMeasureSpec));
    }

    private int measureWidth(int measureSpec) {
        int result = 0;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = mScreenWidth / 2;
            if (mode == MeasureSpec.AT_MOST) {
                result = Math.min(result, size);
            }
        }
        return result;
    }

    private int measureHeight(int measureSpec) {
        int result = 0;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        KLog.e("height: " + size);
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = mScreenHeight / 2;
            if (mode == MeasureSpec.AT_MOST) {
                result = Math.min(result, size);
            }
        }
        return result;
    }

    private int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    // 播放落叶
    public void playLeaf() {

        new Thread() {
            @Override
            public void run() {
                super.run();
                for (int i = 0; i < 20; i++) {
                    ((Activity) getContext()).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            addLeaf();
                        }
                    });
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

    }


    // 添加落叶
    public void addLeaf() {

        KLog.e(mScreenHeight + " " + mHeightSize + " " + ((ViewGroup) getParent()).getMeasuredHeight());
        KLog.e(mScreenWidth + " " + mWidthSize + " " + ((ViewGroup) getParent()).getMeasuredWidth());


        mLeaf = new ImageView(getContext());
        Random random = new Random();
        mLeaf.setImageDrawable(mLeafs[random.nextInt(4)]);
        mLeaf.setLayoutParams(mLp);

        float leafX = random.nextInt(mWidthSize);
        float leafY = 0;

        float h = mHeightSize;
        float w = mWidthSize;
        KLog.e(h / w);
        if (leafX > mWidthSize / 2) {
            leafY = h / w * leafX - h / 2;
        } else {
            leafY = -h / w * leafX + h / 2;
        }
        // 加上toolbar的高度
        ViewCompat.setX(mLeaf, leafX);
        ViewCompat.setY(mLeaf, leafY);
        KLog.e(leafX + "  " + leafY);
        addView(mLeaf);

        ObjectAnimator alpha = ObjectAnimator.ofFloat(mLeaf, "alpha", 0.1f, 1);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(mLeaf, "scaleX", 0.1f, 1);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(mLeaf, "scaleY", 0.1f, 1);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(alpha, scaleX, scaleY);
        set.setDuration(300);

        final PointF pointF1 = new PointF(leafX + random.nextInt((int) (mWidthSize - leafX)), leafY + random.nextInt((int) (mHeightSize - leafY)));
        final PointF pointF2 = new PointF(leafX + random.nextInt((int) (mWidthSize - leafX)), leafY + random.nextInt((int) (mHeightSize - leafY)));
        final PointF pointF0 = new PointF(ViewCompat.getX(mLeaf), ViewCompat.getY(mLeaf));
        final PointF pointF3 = new PointF(random.nextInt(mWidthSize), mHeightSize);

        final BazierTypeEvaluator bazierTypeEvaluator = new BazierTypeEvaluator(pointF1, pointF2);
        ValueAnimator bazierAnimator = ValueAnimator.ofObject(bazierTypeEvaluator, pointF0, pointF3);
        bazierAnimator.setTarget(mLeaf);
        bazierAnimator.addUpdateListener(new BazierUpdateListener(mLeaf));
        bazierAnimator.setDuration(2000);

        AnimatorSet allSet = new AnimatorSet();
        allSet.play(set).before(bazierAnimator);
        allSet.setInterpolator(mInterpolator[random.nextInt(4)]);
        allSet.addListener(new AnimatorEndListener(mLeaf));
        allSet.start();

        KLog.e("child:" + getChildCount());

    }

    private class BazierUpdateListener implements ValueAnimator.AnimatorUpdateListener {

        public BazierUpdateListener(View target) {
            this.target = target;
        }

        View target;


        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            final PointF pointF = (PointF) animation.getAnimatedValue();
            ViewCompat.setX(target, pointF.x);
            ViewCompat.setY(target, pointF.y);
            ViewCompat.setAlpha(target, 1 - animation.getAnimatedFraction());
        }
    }

    private class AnimatorEndListener extends AnimatorListenerAdapter {

        public AnimatorEndListener(View target) {
            this.target = target;
        }

        View target;

        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            removeView(target);
            KLog.e("child:" + getChildCount());
        }
    }

    private class BazierTypeEvaluator implements TypeEvaluator<PointF> {

        /**
         * 三次方贝塞尔曲线
         * B(t)=P0*(1-t)^3+3*P1*t*(1-t)^2+3*P2*t^2*(1-t)+P3*t^3,t∈[0,1]
         * P0,是我们的起点,
         * P3是终点,
         * P1,P2是途径的两个点
         * 而t则是我们的一个因子,取值范围是0-1
         */

        private PointF pointF1;
        private PointF pointF2;

        public BazierTypeEvaluator(PointF pointF1, PointF pointF2) {
            this.pointF1 = pointF1;
            this.pointF2 = pointF2;
        }

        @Override
        public PointF evaluate(float t, PointF startValue, PointF endValue) {
            PointF pointF = new PointF();
            pointF.x = (float) (startValue.x * Math.pow(1 - t, 3) + 3 * pointF1.x * t * Math.pow(1 - t, 2) + 3 * pointF2.x * Math.pow(t, 2) * (1 - t) + endValue.x * Math.pow(t, 3));
            pointF.y = (float) (startValue.y * Math.pow(1 - t, 3) + 3 * pointF1.y * t * Math.pow(1 - t, 2) + 3 * pointF2.y * Math.pow(t, 2) * (1 - t) + endValue.y * Math.pow(t, 3));
            return pointF;
        }
    }

}
