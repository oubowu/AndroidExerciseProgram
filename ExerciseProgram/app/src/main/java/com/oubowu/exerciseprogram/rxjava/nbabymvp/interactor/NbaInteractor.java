package com.oubowu.exerciseprogram.rxjava.nbabymvp.interactor;

import com.oubowu.exerciseprogram.rxjava.nbabymvp.listener.OnRequestListener;

import rx.Subscription;

/**
 * 类名： NbaInteractor
 * 作者: oubowu
 * 时间： 2016/1/19 14:09
 * 功能：
 * svn版本号:$$Rev$$
 * 更新时间:$$Date$$
 * 更新人:$$Author$$
 * 更新描述:
 */
public interface NbaInteractor {

    Subscription requestDatas(OnRequestListener listener);

}
