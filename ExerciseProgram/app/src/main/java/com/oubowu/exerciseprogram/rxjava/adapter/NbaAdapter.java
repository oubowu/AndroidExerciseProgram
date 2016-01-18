package com.oubowu.exerciseprogram.rxjava.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.oubowu.exerciseprogram.R;
import com.oubowu.exerciseprogram.refreshrecyclerview.RefreshRecyclerView;
import com.oubowu.exerciseprogram.refreshrecyclerview.adapter.BaseRecyclerViewAdapter;
import com.oubowu.exerciseprogram.rxjava.model.nba.Tr;
import com.oubowu.exerciseprogram.rxjava.viewholder.NbaViewHolder;
import com.oubowu.exerciseprogram.utils.MeasureUtil;

import java.util.List;

/**
 * 类名： NbaAdapter
 * 作者: oubowu
 * 时间： 2016/1/18 15:32
 * 功能：
 * svn版本号:$$Rev$$
 * 更新时间:$$Date$$
 * 更新人:$$Author$$
 * 更新描述:
 */
public class NbaAdapter extends BaseRecyclerViewAdapter<NbaViewHolder, Tr> {

    private final int mSize;

    public NbaAdapter(Context context,
                      List<Tr> datas,
                      RefreshRecyclerView refreshRecyclerView,
                      boolean isHideLoadAllFooter,
                      boolean disableLoadMore) {
        super(context, datas, refreshRecyclerView, isHideLoadAllFooter, disableLoadMore);
        mSize = (int) (MeasureUtil.getScreenWidth((Activity) context) * 1.0f / 5);
    }

    @Override
    protected NbaViewHolder onCreateCustomViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.item_match, parent, false);
        return new NbaViewHolder(view);
    }

    @Override
    protected void onBindCustomViewHolder(NbaViewHolder holder, int position) {

        final Tr tr = mDatas.get(position);

        holder.home.getLayoutParams().width = mSize;
        holder.home.getLayoutParams().height = mSize;

        holder.away.getLayoutParams().width = mSize;
        holder.away.getLayoutParams().height = mSize;

        Glide.with(mContext)
                .load(tr.player1logobig)
                .crossFade()
                .placeholder(R.mipmap.ic_loading_small_bg)
                .error(R.mipmap.ic_fail)
                .into(holder.home);

        Glide.with(mContext)
                .load(tr.player2logobig)
                .crossFade()
                .placeholder(R.mipmap.ic_loading_small_bg)
                .error(R.mipmap.ic_fail)
                .into(holder.away);

        holder.result.setText(String.format("%s\n%s", tr.score, tr.time));

    }

    @Override
    protected int getCustomItemViewType(int position) {
        return 0;
    }
}
