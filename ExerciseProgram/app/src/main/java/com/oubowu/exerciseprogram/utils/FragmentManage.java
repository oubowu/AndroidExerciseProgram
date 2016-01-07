package com.oubowu.exerciseprogram.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

/**
 * ClassName：FragmentManage
 * Author：Oubowu
 * Fuction：管理Fragment初始化，切换的工具类，添加了切换的动画
 * CreateDate：2015/7/28 15:47
 * UpdateAuthor：
 * UpdateDate：
 */
public class FragmentManage {

    /**
     * 切换Fragment，添加到栈中
     *
     * @param f         fragment
     * @param fm        FragmentManage
     * @param replaceId 替换的布局id
     * @param tag       fragment对应的标签，用于获取创建的实例
     */
    public static void changeFragment(Fragment f, android.support.v4.app.FragmentManager fm, int replaceId, String tag) {
        changeFragment(f, fm, false, replaceId, tag);
    }

    /**
     * 初始化Fragment，不添加到栈
     *
     * @param f         fragment
     * @param fm        FragmentManage
     * @param replaceId 替换的布局id
     * @param tag       fragment对应的标签，用于获取创建的实例
     */
    public static void initFragment(Fragment f, android.support.v4.app.FragmentManager fm, int replaceId, String tag) {
        changeFragment(f, fm, true, replaceId, tag);
    }

    /**
     * 如果替换或者删除一个Fragment然后让用户可以导航到上一个Fragment，你必须在调用commit()
     * 方法之前调用addToBackStack
     * ()方法添加到回退栈。如果你把这个Fragment添加到了回退栈，在提交之后这个Fragment是会被Stop而不是Destroyed
     * 。如果用户导航到这个Fragment
     * ，这个Fragment会被Restart而不是重新创建。如果你没有把它添加到回退栈，则在删除或者替换的时候它将被Destroyed。
     *
     * @param f               切换的fragment
     * @param fragmentManager fragment管理
     * @param init            是否初始化的fragment
     * @param replaceId       替换的布局id
     * @param tag             标签
     */
    public static void changeFragment(Fragment f, android.support.v4.app.FragmentManager fragmentManager, boolean init, int replaceId, String tag) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        //我们在点击FragmentOne中的按钮时，使用了replace方法，如果你看了前一篇博客，
        // 一定记得replace是remove和add的合体，并且如果不添加事务到回退栈，前一个Fragment实例会被销毁。
        // 这里很明显，我们调用tx.addToBackStack(null);将当前的事务添加到了回退栈，
        // 所以FragmentOne实例不会被销毁，但是视图层次依然会被销毁，即会调用onDestoryView和onCreateView，
        ft.replace(replaceId, f, tag);
        if (!init) {
            ft.addToBackStack(null);
        } else {
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        }
        // 如果我们没有使用replace，而是先隐藏了当前的Fragment，然后添加了FragmentThree的实例，
        // 最后将事务添加到回退栈,这样视图就不会重绘
        //  ft.hide(this);  ft.add(R.id.id_content , fThree, "THREE");
        ft.commit();
    }

}
