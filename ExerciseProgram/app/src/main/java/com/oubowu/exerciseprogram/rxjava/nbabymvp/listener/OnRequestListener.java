package com.oubowu.exerciseprogram.rxjava.nbabymvp.listener;

import com.oubowu.exerciseprogram.rxjava.nbabymvp.model.NBA;

/**
 * 类名： OnRequestListener
 * 作者: oubowu
 * 时间： 2016/1/19 17:26
 * 功能：
 * svn版本号:$$Rev$$
 * 更新时间:$$Date$$
 * 更新人:$$Author$$
 * 更新描述:
 */
public interface OnRequestListener {

    void beforeRequest();

    void requestError(Throwable e);

    void requestSuccess(NBA nba);

    void requestComplete();

}
