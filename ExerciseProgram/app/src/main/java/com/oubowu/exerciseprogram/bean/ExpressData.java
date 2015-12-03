package com.oubowu.exerciseprogram.bean;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/*
 *所有属性接受解析和序列化。
 * 作者推荐的方案，更少错误，但要打更多的注解。
 */
@JsonObject
public class ExpressData {

    @JsonField
    public String time;
    @JsonField
    public String location;
    @JsonField
    public String context;
    @JsonField
    public String ftime;

    @Override
    public String toString() {
        return "ExpressData{" +
                "  time=" + time + '\n' +
                "  location=" + location + '\n' +
                "  context'" + context + '\n' +
                "  ftime=" + ftime + '\n' +
                '}';
    }
}
