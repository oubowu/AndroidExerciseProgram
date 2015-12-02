package com.oubowu.exerciseprogram;

import android.view.View;

/**
 * 类名： TestModal
 * 作者: oubowu
 * 时间： 2015/12/2 11:14
 * 功能：
 * svn版本号:$$Rev$$
 * 更新时间:$$Date$$
 * 更新人:$$Author$$
 * 更新描述:
 */
public class RetainDataModel {

    private static RetainDataModel sInstance;
    private View mRetainedButton;

    public static RetainDataModel getInstance() {
        if (sInstance == null) {
            sInstance = new RetainDataModel();
        }
        return sInstance;
    }

    public void recycle() {
        sInstance = null;
    }

    public void setRetainedView(View v) {
        mRetainedButton = v;
    }
}
