package com.oubowu.exerciseprogram.rxjava.nbabymvp.view;

import com.oubowu.exerciseprogram.rxjava.nbabymvp.model.NBA;
import com.oubowu.exerciseprogram.rxjava.nbabymvp.model.Tr;

import java.util.List;

/**
 * 类名： NbaView
 * 作者: oubowu
 * 时间： 2016/1/19 14:05
 * 功能：
 * svn版本号:$$Rev$$
 * 更新时间:$$Date$$
 * 更新人:$$Author$$
 * 更新描述:
 */
public interface NbaView {

    void showProgress();

    void hideProgress();

    void initRecyclerViewDatas(NBA nba);

    void refreshRecyclerViewDatas(NBA nba);

    void showMsg(String msg);

    List<Tr> supportDatas();

}
