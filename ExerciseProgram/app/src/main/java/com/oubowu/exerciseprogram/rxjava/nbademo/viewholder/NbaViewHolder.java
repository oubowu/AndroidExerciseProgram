package com.oubowu.exerciseprogram.rxjava.nbademo.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.oubowu.exerciseprogram.R;
import com.oubowu.exerciseprogram.refreshrecyclerview.viewholder.BaseRecyclerViewHolder;
import com.oubowu.exerciseprogram.utils.UiUtil;

/**
 * 类名： NbaViewHolder
 * 作者: oubowu
 * 时间： 2016/1/18 15:27
 * 功能：
 * svn版本号:$$Rev$$
 * 更新时间:$$Date$$
 * 更新人:$$Author$$
 * 更新描述:
 */
public class NbaViewHolder extends BaseRecyclerViewHolder {

    public ImageView home, away;
    public TextView result;

    public NbaViewHolder(View itemView) {
        super(itemView);
        home = UiUtil.getView(itemView, R.id.iv_home);
        away = UiUtil.getView(itemView, R.id.iv_away);
        result = UiUtil.getView(itemView, R.id.tv_result);
    }

}
