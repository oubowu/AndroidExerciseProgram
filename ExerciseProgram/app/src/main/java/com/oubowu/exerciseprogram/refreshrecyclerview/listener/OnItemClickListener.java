package com.oubowu.exerciseprogram.refreshrecyclerview.listener;

import android.view.View;

/**
 * 类名： OnItemClickListener
 * 作者: oubowu
 * 时间： 2015/11/24 15:49
 * 功能：点击长按的接口
 * svn版本号:$$Rev$$
 * 更新时间:$$Date$$
 * 更新人:$$Author$$
 * 更新描述:
 */
public interface OnItemClickListener {
    void onItemClick(View view, int position);
    void onItemLongClick(View view, int position);
}
