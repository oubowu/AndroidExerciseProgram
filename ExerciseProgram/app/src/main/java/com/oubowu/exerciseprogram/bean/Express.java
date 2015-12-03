package com.oubowu.exerciseprogram.bean;

import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.List;

import rx.Observable;

/**
 * 类名： Express
 * 作者: oubowu
 * 时间： 2015/12/3 13:10
 * 功能：
 * svn版本号:$$Rev$$
 * 更新时间:$$Date$$
 * 更新人:$$Author$$
 * 更新描述:
 */
/*非private属性都会接受解析和序列化，即使属性没有写@JsonFields注解，但要先配置 fieldDetectionPolicy
 * 这个方案比第一个方案写少点注解
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class Express {

    /**
     * message : ok
     * nu : 372067641846
     * ischeck : 0
     * com : zhongtong
     * updatetime : 2015-12-03 11:07:15
     * status : 200
     * condition : 00
     * data : [{"time":"2015-12-02 22:46:58","location":"","context":"[郑州中转] 粤BS0023在郑州中转已发车，下一站深圳中心，","ftime":"2015-12-02 22:46:58"},{"time":"2015-12-02 22:10:58","location":"","context":"[郑州中转] 快件在郑州中转装车,正发往深圳中心","ftime":"2015-12-02 22:10:58"},{"time":"2015-12-02 22:08:35","location":"","context":"[郑州] 快件到达郑州,上一站是洛阳目的地是广州","ftime":"2015-12-02 22:08:35"},{"time":"2015-12-02 17:52:17","location":"","context":"[洛阳]  快件在洛阳装车,正发往郑州中转","ftime":"2015-12-02 17:52:17"},{"time":"2015-12-02 14:12:05","location":"","context":"[洛阳] 快件在洛阳,正发往深圳中心","ftime":"2015-12-02 14:12:05"},{"time":"2015-12-02 14:11:45","location":"","context":"[洛阳]  快件在洛阳装车,正发往深圳中心","ftime":"2015-12-02 14:11:45"},{"time":"2015-12-02 11:00:46","location":"","context":"[洛阳] 洛阳 的 瑜伽 已收件 ","ftime":"2015-12-02 11:00:46"}]
     * state : 0
     */

    public String message;
    public String nu;
    public String ischeck;
    public String com;
    public String updatetime;
    public String status;
    public String condition;
    public String state;
    /**
     * time : 2015-12-02 22:46:58
     * location :
     * context : [郑州中转] 粤BS0023在郑州中转已发车，下一站深圳中心，
     * ftime : 2015-12-02 22:46:58
     */

    public List<ExpressData> data;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Observable.from(data).subscribe(d -> sb.append(d.toString()));
        return "Express{\n" +
                "  message=" + message + '\n' +
                "  nu=" + nu + '\n' +
                "  ischeck=" + ischeck + '\n' +
                "  com=" + com + '\n' +
                "  updatetime=" + updatetime + '\n' +
                "  status=" + status + '\n' +
                "  condition=" + condition + '\n' +
                "  state=" + state + '\n' +
                "  data=" + sb.toString() + "\n" +
                '}';
    }
}
