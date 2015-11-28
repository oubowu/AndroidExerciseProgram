package com.oubowu.exerciseprogram.mvpdemo.view;

import android.util.DisplayMetrics;
import android.view.View;

import com.oubowu.exerciseprogram.mvpdemo.bean.AnimateType;


/**
 * 类名： IPerformAnimatorView
 * 作者: oubowu
 * 时间： 2015/11/27 13:59
 * 功能：Presenter与View交互是通过接口,所以我们这里需要定义一个接口
 * svn版本号:$$Rev$$
 * 更新时间:$$Date$$
 * 更新人:$$Author$$
 * 更新描述:
 */
public interface IPerformAnimatorView {
    AnimateType getAnimateType();

    View getAnimateView();

    DisplayMetrics getDisplayMetrics();

    float getAnimatorStartValue();

    float getAnimatorEndValue();

}
