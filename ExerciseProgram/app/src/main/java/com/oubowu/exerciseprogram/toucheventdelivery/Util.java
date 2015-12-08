package com.oubowu.exerciseprogram.toucheventdelivery;

import android.view.MotionEvent;

/**
 * ClassName:
 * Author:oubowu
 * Fuction:
 * CreateDate:2015/12/9 1:43
 * UpdateUser:
 * UpdateDate:
 */
public class Util {
    public static String actionToString(int action) {
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                return "ACTION_DOWN";
            case MotionEvent.ACTION_UP:
                return "ACTION_UP";
            case MotionEvent.ACTION_MOVE:
                return "ACTION_MOVE";
        }
        return "";
    }
}
