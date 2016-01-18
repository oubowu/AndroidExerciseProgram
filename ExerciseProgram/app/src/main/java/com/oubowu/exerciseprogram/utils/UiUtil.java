package com.oubowu.exerciseprogram.utils;

import android.view.View;

/**
 * 类名： UiUtil
 * 作者: oubowu
 * 时间： 2016/1/18 15:29
 * 功能：
 * svn版本号:$$Rev$$
 * 更新时间:$$Date$$
 * 更新人:$$Author$$
 * 更新描述:
 */
public class UiUtil {

    @SuppressWarnings("unchecked")
    public static <T extends View> T getView(View parent, int id) {
        return (T) parent.findViewById(id);
    }

}
