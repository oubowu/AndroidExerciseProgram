package com.oubowu.exerciseprogram.mvpdemo.biz;

import android.animation.IntEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.util.DisplayMetrics;
import android.view.View;

import com.oubowu.exerciseprogram.R;
import com.oubowu.exerciseprogram.mvpdemo.bean.AnimateType;


/**
 * 类名： AnimateBiz
 * 作者: oubowu
 * 时间： 2015/11/27 13:52
 * 功能：Model的业务接口实现
 * svn版本号:$$Rev$$
 * 更新时间:$$Date$$
 * 更新人:$$Author$$
 * 更新描述:
 */
public class AnimateBiz implements IAnimateBiz {
    @Override
    public void perforAnimate(AnimateType animateType, final View view, final DisplayMetrics metrics) {
        float startValue=animateType.getStartValue();
        float endValue=animateType.getEndValue();
        switch (animateType.getType()) {
            case R.id.bt_scale_big:
                ObjectAnimator.ofFloat(view, "scaleX", startValue, endValue).setDuration(1000).start();
                ObjectAnimator.ofFloat(view, "scaleY", startValue, endValue).setDuration(1000).start();
                break;
            case R.id.bt_scale_small:
                ObjectAnimator.ofFloat(view, "scaleX", endValue, startValue).setDuration(1000).start();
                ObjectAnimator.ofFloat(view, "scaleY", endValue, startValue).setDuration(1000).start();
                break;
            case R.id.bt_rotate:
                ObjectAnimator.ofFloat(view, "rotationX", startValue, endValue).setDuration(1000).start();
                ObjectAnimator.ofFloat(view, "rotationY", startValue, endValue).setDuration(1000).start();
                break;
            case R.id.bt_circle:
                final int width = metrics.widthPixels / 2;
                final int height = metrics.heightPixels / 2;
                ValueAnimator valueAnimator = ValueAnimator.ofInt(1, 1000);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                    private IntEvaluator evaluator = new IntEvaluator();

                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int value = (int) animation.getAnimatedValue();
                        float fraction = (float) (value / 1000.0);

                        Integer evaluate = evaluator.evaluate(fraction, 0, width * 2);
                        if (evaluate <= width) {
                            view.setX((width * 2 - view.getMeasuredWidth()) / 2 + evaluate);
                            view.setY((float) (height + Math.sqrt(Math.pow(width / 2, 2) - Math.pow(width / 2 - evaluate, 2))));
                        } else {
                            view.setX((width * 2 - view.getMeasuredWidth()) / 2 + width - (evaluate - width));
                            view.setY((float) (height - Math.sqrt(Math.pow(width / 2, 2) - Math.pow(width / 2 - (evaluate - width), 2))));
                        }

                    }
                });
                valueAnimator.setDuration(2000).start();
                break;
        }
    }
}
