package com.oubowu.exerciseprogram.mvp.bean;

/**
 * 类名： AnimateType
 * 作者: oubowu
 * 时间： 2015/11/27 13:49
 * 功能：Model的bean
 * svn版本号:$$Rev$$
 * 更新时间:$$Date$$
 * 更新人:$$Author$$
 * 更新描述:
 */
public class AnimateType {

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public AnimateType(int type) {
        this.type = type;
    }

    public AnimateType(int type, float startValue, float endValue) {
        this.type = type;
        this.startValue = startValue;
        this.endValue = endValue;
    }

    private int type;

    public float getStartValue() {
        return startValue;
    }

    public void setStartValue(float startValue) {
        this.startValue = startValue;
    }

    public float getEndValue() {
        return endValue;
    }

    public void setEndValue(float endValue) {
        this.endValue = endValue;
    }

    private float startValue;
    private float endValue;

}
