package com.oubowu.exerciseprogram.bean;

import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.List;

/**
 * 类名： CardEntitiy
 * 作者: oubowu
 * 时间： 2015/12/4 11:47
 * 功能：
 * svn版本号:$$Rev$$
 * 更新时间:$$Date$$
 * 更新人:$$Author$$
 * 更新描述:
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class CardEntitiy {
    public String key;
    public String name;
    public List<String> value;
    public List<String> format;
}
