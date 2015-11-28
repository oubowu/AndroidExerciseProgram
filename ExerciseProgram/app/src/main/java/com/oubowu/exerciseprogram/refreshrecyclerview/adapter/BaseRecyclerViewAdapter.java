package com.oubowu.exerciseprogram.refreshrecyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.oubowu.exerciseprogram.R;
import com.oubowu.exerciseprogram.refreshrecyclerview.RefreshRecyclerView;
import com.oubowu.exerciseprogram.refreshrecyclerview.viewholder.BaseRecyclerViewHolder;

import java.util.List;

/**
 * 类名： BaseRecyclerViewAdapter
 * 作者: oubowu
 * 时间： 2015/11/23 9:26
 * 功能：
 * svn版本号:$$Rev$$
 * 更新时间:$$Date$$
 * 更新人:$$Author$$
 * 更新描述:
 */
public abstract class BaseRecyclerViewAdapter<T extends BaseRecyclerViewHolder, V> extends RecyclerView.Adapter<T> {

    public List<V> getDatas() {
        return mDatas;
    }

    public void setDatas(List<V> datas) {
        this.mDatas = datas;
        mRefreshRecyclerView.refreshComplete();
    }

    public void addMoreDatas(List<V> datas) {
        int startPos = this.mDatas.size();
        this.mDatas.addAll(datas);
        notifyItemRangeInserted(startPos, datas.size());
        mRefreshRecyclerView.loadMoreComplete();
    }

    protected List<V> mDatas;

    protected RefreshRecyclerView mRefreshRecyclerView;

    public final static int TYPE_LOAD_ALL = 1234;

    protected Context mContext;

    public BaseRecyclerViewAdapter(Context context, List<V> datas, RefreshRecyclerView refreshRecyclerView) {
        this.mDatas = datas;
        this.mRefreshRecyclerView = refreshRecyclerView;
        this.mContext = context;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_LOAD_ALL) {
            return (T) new BaseRecyclerViewHolder(LayoutInflater.from(mContext).inflate(R.layout.common_load_all_footer_layout, parent, false));
        }
        return (T) onCreateCustomViewHolder(parent, viewType);
    }

    protected abstract T onCreateCustomViewHolder(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(T holder, int position) {
        if (getItemViewType(position) == TYPE_LOAD_ALL) {
            if (mRefreshRecyclerView.getRecyclerView().getLayoutManager() instanceof StaggeredGridLayoutManager) {
                final StaggeredGridLayoutManager.LayoutParams lp = (StaggeredGridLayoutManager.LayoutParams) ((ViewGroup) holder.loadAllText.getParent()).getLayoutParams();
                lp.setFullSpan(true);
            } else if (mRefreshRecyclerView.getRecyclerView().getLayoutManager() instanceof GridLayoutManager) {
                ((GridLayoutManager) mRefreshRecyclerView.getRecyclerView().getLayoutManager()).setSpanSizeLookup(mLookup == null ? new CustomSpanSizeLookup() : mLookup);
            }
            holder.loadAllText.setText(mRefreshRecyclerView.getLoadAllFooterText());
            holder.loadAllText.setTextSize(TypedValue.COMPLEX_UNIT_PX, mRefreshRecyclerView.getLoadAllFooterTextSize());
            holder.loadAllText.setTextColor(mRefreshRecyclerView.getLoadAllFooterTextColor());
            return;
        }
        onBindCustomViewHolder(holder, position);
//        DebugLog.e("onBindViewHolder: " + position);
    }

    private CustomSpanSizeLookup mLookup;

    private class CustomSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {
        @Override
        public int getSpanSize(int position) {
            return getItemViewType(position) == TYPE_LOAD_ALL ? ((GridLayoutManager) mRefreshRecyclerView.getRecyclerView().getLayoutManager()).getSpanCount() : 1;
        }
    }

    protected abstract void onBindCustomViewHolder(T holder, int position);

    @Override
    public int getItemViewType(int position) {
        if (mRefreshRecyclerView.isLoadAll() && position == getItemCount() - 1) {
            return TYPE_LOAD_ALL;
        }
        return getCustomItemViewType(position);
    }

    protected abstract int getCustomItemViewType(int position);

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size() + (mRefreshRecyclerView.isLoadAll() ? 1 : 0);
    }

}
