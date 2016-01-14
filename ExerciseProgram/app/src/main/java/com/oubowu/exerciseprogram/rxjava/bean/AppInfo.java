package com.oubowu.exerciseprogram.rxjava.bean;

import lombok.experimental.Accessors;

/**
 * 类名： AppInfo
 * 作者: oubowu
 * 时间： 2016/1/14 15:40
 * 功能：
 * svn版本号:$$Rev$$
 * 更新时间:$$Date$$
 * 更新人:$$Author$$
 * 更新描述:
 */

//lombok注解：
//@Data：注解在类上；提供类所有属性的setting和getting方法,此外还提供了equals、canEqual、hashCode、toString方法；
//@Setter：注解在属性上，为属性提供了setting方法；
//@Getter：注解在属性上，为属性提供了getting方法；
//@Log4j：注解在类上，为类提供一个属性名为log的log4j日志对象(需要log4j的jar包)；
//@NoArgsConstructor：注解在类上，为类提供了一个无参的构造方法；
//@AllArgsConstructor：注解在类上，为类提供了一个全参的构造方法；
//@EqualsAndHashCode：注解在类上，为类提供equals()方法和hashCode()方法；
//@ToString：注解在类上，为类提供toString()方法；
//@Cleanup: 关闭流 --
//@Synchronized：对象同步
//@SneakyThrows：抛出异常

// 命名的时候去掉m
@Accessors(prefix = "m")
public class AppInfo implements Comparable<Object> {

    private long mLastUpdateTime;
    private String mName;
    private String mIcon;

    public AppInfo(long lastUpdateTime, String name, String icon) {
        this.mLastUpdateTime = lastUpdateTime;
        this.mName = name;
        this.mIcon = icon;
    }

    @Override
    public int compareTo(Object another) {
        return getName().compareTo(((AppInfo) another).getName());
    }

    public long getLastUpdateTime() {
        return this.mLastUpdateTime;
    }

    public String getName() {
        return this.mName;
    }

    public String getIcon() {
        return this.mIcon;
    }

    public void setLastUpdateTime(long mLastUpdateTime) {
        this.mLastUpdateTime = mLastUpdateTime;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public void setIcon(String mIcon) {
        this.mIcon = mIcon;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof AppInfo)) return false;
        final AppInfo other = (AppInfo) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.mLastUpdateTime != other.mLastUpdateTime) return false;
        final Object this$mName = this.mName;
        final Object other$mName = other.mName;
        if (this$mName == null ? other$mName != null : !this$mName.equals(other$mName))
            return false;
        final Object this$mIcon = this.mIcon;
        final Object other$mIcon = other.mIcon;
        if (this$mIcon == null ? other$mIcon != null : !this$mIcon.equals(other$mIcon))
            return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final long $mLastUpdateTime = this.mLastUpdateTime;
        result = result * PRIME + (int) ($mLastUpdateTime >>> 32 ^ $mLastUpdateTime);
        final Object $mName = this.mName;
        result = result * PRIME + ($mName == null ? 0 : $mName.hashCode());
        final Object $mIcon = this.mIcon;
        result = result * PRIME + ($mIcon == null ? 0 : $mIcon.hashCode());
        return result;
    }

    protected boolean canEqual(Object other) {
        return other instanceof AppInfo;
    }

    public String toString() {
        return "com.oubowu.exerciseprogram.rxjava.bean.AppInfo(mLastUpdateTime=" + this.mLastUpdateTime + ", mName=" + this.mName + ", mIcon=" + this.mIcon + ")";
    }
}
