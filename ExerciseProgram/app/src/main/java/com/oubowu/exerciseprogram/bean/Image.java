package com.oubowu.exerciseprogram.bean;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.bluelinelabs.logansquare.annotation.OnJsonParseComplete;
import com.bluelinelabs.logansquare.annotation.OnPreJsonSerialize;

import java.util.List;

/**
 * 类名： Image
 * 作者: oubowu
 * 时间： 2015/12/3 13:55
 * 功能：
 * svn版本号:$$Rev$$
 * 更新时间:$$Date$$
 * 更新人:$$Author$$
 * 更新描述:
 */
/*
 *所有属性接受解析和序列化。
 * 作者推荐的方案，更少错误，但要打更多的注解。
 */
@JsonObject
public class Image {

    /*
     * 标准的属性注解
     */
    @JsonField
    public String format;

    /*
     * 解析和序列化时，该属性的key用"_id"代替"imageId"
     */
    @JsonField(name = "_id")
    public int imageId;

    @JsonField
    public String url;

    @JsonField
    public String description;

    /*
     * 注意虽然该属性只有包访问权限，但LoganSquare可毫无障碍地处理
     */
    @JsonField(name = "similar_images")
    List<Image> similarImages;

    /*
     * 不注解的属性默认被LoganSquare忽略
     */
    public int nonJsonField;

    /*
     * ！！！强烈注意private权限的属性必须提供getter和setter， 不然你会后悔的
     */
    @JsonField
    private int privateInt;

    public int getPrivateInt() {
        return privateInt;
    }

    public void setPrivateInt(int i) {
        privateInt = i;
    }

    /*
     * 还贴心地提供了解析完成后和序列化前的回调接口，当然是可选的
     */
    @OnJsonParseComplete
    void onParseComplete() {
        // 解析完成后干点什么
    }

    @OnPreJsonSerialize
    void onPreSerialize() {
        //序列化前干点什么
    }

}
