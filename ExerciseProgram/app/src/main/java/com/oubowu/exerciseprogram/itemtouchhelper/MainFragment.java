package com.oubowu.exerciseprogram.itemtouchhelper;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.oubowu.exerciseprogram.R;

/**
 * 类名： MainFragment
 * 作者: oubowu
 * 时间： 2016/1/25 12:48
 * 功能：
 * svn版本号:$$Rev$$
 * 更新时间:$$Date$$
 * 更新人:$$Author$$
 * 更新描述:
 */
public class MainFragment extends ListFragment {
    private onListItemClickListener mListItemClickListener;
    //定义一个回调接口，用来将点击事件传回他的宿主Activity去做，Fragment中不做具体的逻辑操作
    public interface onListItemClickListener{
        void onListItemClick(int position);
    }
    public MainFragment(){

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //他的宿主Activity将实现onListItemClickListener接口
        //使用getActivity（）获得的宿主Activity，将他强转成onListItemClickListener接口
        mListItemClickListener = (onListItemClickListener)getActivity();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //获得我们在strings.xml中定义个数组
        final String[] items = getResources().getStringArray(R.array.main_items);
        //创建适配器
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, items);
        //设置适配器
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if (mListItemClickListener!=null){
            //由于宿主Activity实现了onListItemClickListener接口
            //因此调用的是宿主Activity的onListItemClick方法
            //并且将点击的item的position传给Activity
            mListItemClickListener.onListItemClick(position);
        }
    }
}