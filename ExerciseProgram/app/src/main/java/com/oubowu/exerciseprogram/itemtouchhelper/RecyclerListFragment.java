package com.oubowu.exerciseprogram.itemtouchhelper;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 类名： RecyclerListFragment
 * 作者: oubowu
 * 时间： 2016/1/25 12:49
 * 功能：
 * svn版本号:$$Rev$$
 * 更新时间:$$Date$$
 * 更新人:$$Author$$
 * 更新描述:
 */
public class RecyclerListFragment extends Fragment implements SimpleItemTouchHelperCallback.OnStartDragListener {
    private ItemTouchHelper mItemTouchHelper;

    public RecyclerListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return new RecyclerView(container.getContext());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getActivity(), this);
        //参数view即为我们在onCreateView中return的view
        RecyclerView recyclerView = (RecyclerView) view;
        //固定recyclerview大小
        recyclerView.setHasFixedSize(true);
        //设置adapter
        recyclerView.setAdapter(adapter);
        //设置布局类型为LinearLayoutManager，相当于ListView的样式
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //关联ItemTouchHelper和RecyclerView
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
    }

    // 通过拖动item右侧的ImageView来拖拽整个item
    @Override
    public void startDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }
}
