package com.oubowu.exerciseprogram.itemtouchhelper;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.oubowu.exerciseprogram.R;

/**
 * 类名： ItemTouchHelperActivity
 * 作者: oubowu
 * 时间： 2016/1/25 12:50
 * 功能：
 * svn版本号:$$Rev$$
 * 更新时间:$$Date$$
 * 更新人:$$Author$$
 * 更新描述:
 */
public class ItemTouchHelperActivity extends AppCompatActivity implements MainFragment.onListItemClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_touch_helper);
        //当savedInstanceState为null时才new一个MainFragment出来
        //否则每次旋转屏幕都会new出来一个
        if (savedInstanceState == null){
            MainFragment fragment = new MainFragment();
            //用add将MainFragment添加到framelayout上
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content,fragment)
                    .commit();
        }
    }


    @Override
    public void onListItemClick(int position) {
        //当MainFragment的Item被点击后，就会回调此方法
        //在此方法中写真正的逻辑，这样Activity和Fragment
        //之间就是松耦合关系，MainFragment可以复用
        Fragment fragment = null;
        switch (position){
            case 0:
                //当点击第一个item时候，new一个RecyclerListFragment
                fragment = new RecyclerListFragment();
                break;
            case 1:
                //当点击第二个item时候，new一个RecyclerGridFragment
                fragment = new RecyclerGridFragment();
                break;
        }
        //这次用replace，替换framelayout的布局，也就是MainFragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content,fragment)
                .addToBackStack(null)
                .commit();
    }
}
