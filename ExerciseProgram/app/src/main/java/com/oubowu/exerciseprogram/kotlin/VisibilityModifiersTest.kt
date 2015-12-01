package com.oubowu.exerciseprogram.kotlin

/**
 * 类名： Hehe
 * 作者: oubowu
 * 时间： 2015/12/1 14:04
 * 功能：
 * svn版本号:$$Rev$$
 * 更新时间:$$Date$$
 * 更新人:$$Author$$
 * 更新描述:
 */
class VisibilityModifiersTest:KotlinActivity() {

    /*java中类的修饰符有以下几种：private 、default（package）、protect、public，其范围如下表：
    范围	           private	default	 protected	public
    同一类	             √	      √	    √	     √
    同一包中的类	 	          √	    √	     √
    同一包中的子类、
    不同包中的子类	 	                    √	     √
    所有	 	 	 	                             √*/

    fun keke(): Unit {
        val k = KotlinActivity();
        // 在这个Module都可见
        k.internalVariable
        k.publicVariable
        // 继承了KotlinActivity后可见
        k.protectedVariable
    }

}