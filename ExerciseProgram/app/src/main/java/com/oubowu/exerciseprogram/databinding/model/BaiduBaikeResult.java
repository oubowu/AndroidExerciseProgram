package com.oubowu.exerciseprogram.databinding.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.oubowu.exerciseprogram.BR;

/**
 * 类名： BaiduBaikeResult
 * 作者: oubowu
 * 时间： 2015/12/4 12:09
 * 功能：
 * svn版本号:$$Rev$$
 * 更新时间:$$Date$$
 * 更新人:$$Author$$
 * 更新描述:
 */
public class BaiduBaikeResult extends BaseObservable {

    private String chineseName;
    private String englishName;

    public void setAbstractX(String abstractX) {
        this.abstractX = abstractX;
        notifyPropertyChanged(BR.abstractX);
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
        notifyPropertyChanged(BR.chineseName);
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
        notifyPropertyChanged(BR.englishName);
    }

    private String abstractX;

    public BaiduBaikeResult(String chineseName, String englishName, String abstractX) {
        this.chineseName = chineseName;
        this.englishName = englishName;
        this.abstractX = abstractX;
    }

    @Bindable
    public String getChineseName() {
        return chineseName;
    }

    @Bindable
    public String getEnglishName() {
        return englishName;
    }

    @Bindable
    public String getAbstractX() {
        return abstractX;
    }

    @Override
    public String toString() {
        return "BaiduBaikeResult{" +
                "chineseName='" + chineseName + '\'' +
                ", englishName='" + englishName + '\'' +
                ", abstractX='" + abstractX + '\'' +
                '}';
    }
}
