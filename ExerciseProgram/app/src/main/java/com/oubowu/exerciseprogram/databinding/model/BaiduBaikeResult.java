package com.oubowu.exerciseprogram.databinding.model;

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
public class BaiduBaikeResult {

    private final String chineseName;
    private final String englishName;
    private final String abstractX;

    public BaiduBaikeResult(String chineseName, String englishName, String abstractX) {
        this.chineseName = chineseName;
        this.englishName = englishName;
        this.abstractX = abstractX;
    }

    public String getChineseName() {
        return chineseName;
    }

    public String getEnglishName() {
        return englishName;
    }

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
