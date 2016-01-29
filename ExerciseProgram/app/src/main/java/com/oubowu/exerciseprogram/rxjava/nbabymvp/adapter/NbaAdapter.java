package com.oubowu.exerciseprogram.rxjava.nbabymvp.adapter;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oubowu.exerciseprogram.R;
import com.oubowu.exerciseprogram.databinding.NbaRecyclerViewBinding;
import com.oubowu.exerciseprogram.refreshrecyclerview.RefreshRecyclerView;
import com.oubowu.exerciseprogram.refreshrecyclerview.adapter.BaseRecyclerViewAdapter;
import com.oubowu.exerciseprogram.rxjava.nbabymvp.model.Tr;
import com.oubowu.exerciseprogram.rxjava.nbabymvp.viewholder.NbaViewHolder;
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
        final NbaRecyclerViewBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_match_mvp, parent, false);
        return new NbaViewHolder(binding.getRoot(), binding);
    }

    @Override
    protected void onBindCustomViewHolder(NbaViewHolder holder, int position) {

        final Tr tr = mDatas.get(position);

//        holder.bindMatchScore(tr);
//
//        holder.bindPlayer1logo(tr.player1logobig);
//
//        holder.bindPlayer2logo(tr.player2logobig);
//
//        holder.bindImageSize(mSize);

        holder.getBinding().setMatch(tr);

        holder.getBinding().setPlayer1logobig(tr.player1logobig);

        holder.getBinding().setPlayer2logobig(tr.player2logobig);

        // 当我们需要某个view的实例时，我们只要给该view一个id，然后Data Binding框架就会给我们自动生成该view的实例，放哪了？当然是ViewDataBinding里面
        holder.getBinding().ivHome.getLayoutParams().width = holder.getBinding().ivHome.getLayoutParams().height = mSize;

        holder.getBinding().ivAway.getLayoutParams().width = holder.getBinding().ivAway.getLayoutParams().height = mSize;

        holder.getBinding().llMatch.setTag(position);

        holder.getBinding().setOnItemClick(this);

        // 当数据改变时，binding会在下一帧去改变数据，如果我们需要立即改变，就去调用executePendingBindings方法
        holder.getBinding().executePendingBindings();

    }

    @Override
    protected int getCustomItemViewType(int position) {
        return 0;
    }

    public void onClick(View v) {
        if (mItemListener != null) {
            mItemListener.onItemClick(v, (int) v.getTag());
        }
    }
}
