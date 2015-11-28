package com.oubowu.exerciseprogram.refreshrecyclerview.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.oubowu.exerciseprogram.R;


/**
 * 类名： BaseRecyclerViewHolder
 * 作者: oubowu
 * 时间： 2015/11/23 10:01
 * 功能：
 * svn版本号:$$Rev$$
 * 更新时间:$$Date$$
 * 更新人:$$Author$$
 * 更新描述:
 */
public class BaseRecyclerViewHolder extends RecyclerView.ViewHolder {

    public final TextView loadAllText;

    public BaseRecyclerViewHolder(View itemView) {
        super(itemView);
        loadAllText = (TextView) itemView.findViewById(R.id.tv_load_all);
    }

}
