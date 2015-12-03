package com.oubowu.exerciseprogram.bean;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonIgnore;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.bluelinelabs.logansquare.annotation.OnJsonParseComplete;
import com.bluelinelabs.logansquare.annotation.OnPreJsonSerialize;

import java.util.List;

/**
 * 类名： Image1
 * 作者: oubowu
 * 时间： 2015/12/3 13:55
 * 功能：
 * svn版本号:$$Rev$$
 * 更新时间:$$Date$$
 * 更新人:$$Author$$
 * 更新描述:
 */
/*非private属性都会接受解析和序列化，即使属性没有写@JsonFields注解，但要先配置 fieldDetectionPolicy
 * 这个方案比第一个方案写少点注解
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS)
public class Image1 {

    /*
     *普通声明的属性默认会被解析和序列化
     */
    public String format;

    /*
     *重命名key还是需要注解来指出的
     */
    @JsonField(name = "_id")
    public int imageId;

    public String url;

    public String description;

    /*
     * 包访问权限的处理是没问题的
     */
    List<Image> similarImages;

    /*
     * 用@JsonIgnore来忽略不想被解析和序列化的属性
     */
    @JsonIgnore
    public int nonJsonField;

    /*
     * 该策略下private属性默认忽略
     */
    private int privateInt;

    public int getPrivateInt() {
        return privateInt;
    }

    public void setPrivateInt(int i) {
        privateInt = i;
    }

    @OnJsonParseComplete
    void onParseComplete() {
        // 解析完成后干点什么
    }

    @OnPreJsonSerialize
    void onPreSerialize() {
        //序列化前干点什么
    }
}
