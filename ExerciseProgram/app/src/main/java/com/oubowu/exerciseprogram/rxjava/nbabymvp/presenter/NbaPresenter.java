package com.oubowu.exerciseprogram.rxjava.nbabymvp.presenter;

/**
 * 类名： NbaPresenter
 * 作者: oubowu
 * 时间： 2016/1/19 14:08
 * 功能：
 * svn版本号:$$Rev$$
 * 更新时间:$$Date$$
 * 更新人:$$Author$$
 * 更新描述:
 */
public interface NbaPresenter {

    void onResume();

    void onDestroy();

    void onItemClicked(int position);

    void refresh();

}
