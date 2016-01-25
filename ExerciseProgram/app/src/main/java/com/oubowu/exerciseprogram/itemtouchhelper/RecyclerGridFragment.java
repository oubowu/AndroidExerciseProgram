package com.oubowu.exerciseprogram.itemtouchhelper;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 类名： RecyclerGridFragment
 * 作者: oubowu
 * 时间： 2016/1/25 12:49
 * 功能：
 * svn版本号:$$Rev$$
 * 更新时间:$$Date$$
 * 更新人:$$Author$$
 * 更新描述:
 */
public class RecyclerGridFragment extends Fragment implements SimpleItemTouchHelperCallback.OnStartDragListener {
    private ItemTouchHelper mItemTouchHelper;

    public RecyclerGridFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return new RecyclerView(container.getContext());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getActivity(),this);
        RecyclerView recyclerView = (RecyclerView) view;
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        //只有这里和RecyclerListFragment不一样，这里我们指定布局为GridView样式，2列
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void startDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }
}
