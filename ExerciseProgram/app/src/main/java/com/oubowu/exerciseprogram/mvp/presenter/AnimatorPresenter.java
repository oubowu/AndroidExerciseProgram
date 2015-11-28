package com.oubowu.exerciseprogram.mvp.presenter;


import com.oubowu.exerciseprogram.mvp.biz.AnimateBiz;
import com.oubowu.exerciseprogram.mvp.biz.IAnimateBiz;
import com.oubowu.exerciseprogram.mvp.view.IPerformAnimatorView;

/**
 * 类名： AnimatorPresenter
 * 作者: oubowu
 * 时间： 2015/11/27 14:00
 * 功能：Presenter是用作Model和View之间交互的桥梁
 * svn版本号:$$Rev$$
 * 更新时间:$$Date$$
 * 更新人:$$Author$$
 * 更新描述:
 */
public class AnimatorPresenter {

    private IAnimateBiz animateBiz;
    private IPerformAnimatorView animatorView;

    public AnimatorPresenter(IPerformAnimatorView performAnimatorView) {
        this.animatorView = performAnimatorView;
        animateBiz = new AnimateBiz();
    }

    public void actAnimator() {
        animateBiz.perforAnimate(animatorView.getAnimateType(), animatorView.getAnimateView(), animatorView.getDisplayMetrics());
    }

}
