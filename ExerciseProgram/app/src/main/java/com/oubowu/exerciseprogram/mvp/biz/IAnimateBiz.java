package com.oubowu.exerciseprogram.mvp.biz;

import android.util.DisplayMetrics;
import android.view.View;

import com.oubowu.exerciseprogram.mvp.bean.AnimateType;

/**
 * 类名： IAnimateBiz
 * 作者: oubowu
 * 时间： 2015/11/27 13:51
 * 功能：Model的业务接口
 * svn版本号:$$Rev$$
 * 更新时间:$$Date$$
 * 更新人:$$Author$$
 * 更新描述:
 */
public interface IAnimateBiz {
    void perforAnimate(AnimateType animateType, View view, DisplayMetrics metrics);
}
