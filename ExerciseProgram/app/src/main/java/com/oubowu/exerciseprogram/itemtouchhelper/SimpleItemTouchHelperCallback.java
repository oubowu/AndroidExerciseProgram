package com.oubowu.exerciseprogram.itemtouchhelper;

import android.graphics.Canvas;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * 类名： SimpleItemTouchHelperCallback
 * 作者: oubowu
 * 时间： 2016/1/25 11:59
 * 功能：
 * svn版本号:$$Rev$$
 * 更新时间:$$Date$$
 * 更新人:$$Author$$
 * 更新描述:
 */
public class SimpleItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private final OnMoveAndSwipeListener mAdapter;

    public interface OnMoveAndSwipeListener {
        boolean onItemMove(int fromPosition, int toPosition);

        void onItemDismiss(int position);
    }

    public interface OnStartDragListener {
        void startDrag(RecyclerView.ViewHolder viewHolder);
    }

    public interface OnStateChangedListener {
        void onItemSelected();

        void onItemClear();
    }

    public SimpleItemTouchHelperCallback(OnMoveAndSwipeListener listener) {
        mAdapter = listener;
    }

    /**
     * 这个方法是用来设置我们拖动的方向以及侧滑的方向的
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {

        //如果是ListView样式的RecyclerView
        if (recyclerView.getLayoutManager().getClass().getSimpleName().equals(LinearLayoutManager.class.getSimpleName())) {
            // 设置拖拽方向为上下
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            // 设置侧滑方向为从左到右和从右到左都可以
            final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
            // 将方法参数设置进去
            return makeMovementFlags(dragFlags, swipeFlags);
        } else {
            // 如果是GridView样式的RecyclerView
            // 设置拖拽方向为上下左右
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            // 不支持侧滑
            final int swipeFlags = 0;
            return makeMovementFlags(dragFlags, swipeFlags);
        }

    }

    /**
     * 当我们拖动item时会回调此方法
     */
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

        // 如果两个item不是一个类型的，我们让他不可以拖拽
        if (viewHolder.getItemViewType() != target.getItemViewType()) {
            return false;
        }

        // 回调adapter中的onItemMove方法
        mAdapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    /**
     * 当我们侧滑item时会回调此方法
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        // 回调adapter中的onItemDismiss方法
        mAdapter.onItemDismiss(viewHolder.getAdapterPosition());
    }

    /**
     * 当状态改变时回调此方法
     */
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        //当前状态不是idel（空闲）状态时，说明当前正在拖拽或者侧滑
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            //看看这个viewHolder是否实现了onStateChangedListener接口
            if (viewHolder instanceof OnStateChangedListener) {
                OnStateChangedListener listener = (OnStateChangedListener) viewHolder;
                //回调ItemViewHolder中的onItemSelected方法来改变item的背景颜色
                listener.onItemSelected();
            }
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    /**
     * 当用户拖拽完或者侧滑完一个item时回调此方法，用来清除施加在item上的一些状态
     */
    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        if (viewHolder instanceof OnStateChangedListener) {
            OnStateChangedListener listener = (OnStateChangedListener) viewHolder;
            //回调ItemViewHolder中的onItemSelected方法来改变item的背景颜色
            listener.onItemClear();
        }
    }

    /**
     * 这个方法可以判断当前是拖拽还是侧滑
     */
    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            //根据侧滑的位移来修改item的透明度
            final float alpha = 1 - Math.abs(dX) / (float) viewHolder.itemView.getWidth();
            viewHolder.itemView.setAlpha(alpha);
            viewHolder.itemView.setTranslationX(dX);
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

}
