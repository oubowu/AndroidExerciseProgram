package com.oubowu.exerciseprogram.reflection;

/**
 * ClassName:
 * Author:oubowu
 * Fuction:
 * CreateDate:2015/12/12 0:17
 * UpdateUser:
 * UpdateDate:
 */
public class Sub extends Base {

    public Sub(int a, String b, Base c) {

    }

    private void setStr(String str) {
        this.str = str;
    }

    private String str = "私有字符串";


    @Override
    protected void printClassName() {
        System.out.println(getClass().getSimpleName());
    }
}
