package com.oubowu.exerciseprogram.refreshrecyclerview;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.oubowu.exerciseprogram.R;
import com.oubowu.exerciseprogram.refreshrecyclerview.adapter.BaseRecyclerViewAdapter;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.socks.library.KLog;

/**
 * 类名： RefreshRecyclerView
 * 作者: oubowu
 * 时间： 2015/11/21 10:24
 * 功能：将swiperefreshlayout和recyclerview封装起来，做成上拉刷新，下滑底部自动加载的组合控件
 * svn版本号:$$Rev$$
 * 更新时间:$$Date$$
 * 更新人:$$Author$$
 * 更新描述:
 */
public class RefreshRecyclerView extends RelativeLayout {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private View mLoadingFooter;
    private OnScrollListener mScrollListeners;
    private OnRefreshListener mRefreshListener;
    private RecyclerView.LayoutManager mLayoutManager;
    private BaseRecyclerViewAdapter mAdapter;
    private int mLastVisiblePosition[] = new int[2];
    private int mLoadingFooterHeight;
    private boolean mIsLoadAll;
    private ProgressWheel mWheel;
    private TextView mLoadingFooterText;

    private int mLoadAllTextSize;
    private int mLoadAllTextColor;
    private String mLoadAllTextString;

    public RefreshRecyclerView(Context context) {
        super(context);
        init(context, null);
    }

    public RefreshRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RefreshRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RefreshRecyclerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    public void setColorSchemeResources(@ColorRes int... colorResIds) {
        final Resources res = getResources();
        int[] colorRes = new int[colorResIds.length];
        for (int i = 0; i < colorResIds.length; i++) {
            colorRes[i] = res.getColor(colorResIds[i]);
        }
        mSwipeRefreshLayout.setColorSchemeColors(colorRes);
    }

    private void init(Context context, AttributeSet attrs) {
        mSwipeRefreshLayout = new SwipeRefreshLayout(context);
        mSwipeRefreshLayout.setColorSchemeResources(
                R.color.refresh_progress_1,
                R.color.refresh_progress_2,
                R.color.refresh_progress_3);
        addView(mSwipeRefreshLayout, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        mRecyclerView = new RecyclerView(context);
        mSwipeRefreshLayout.addView(mRecyclerView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        mLoadingFooter = LayoutInflater.from(context).inflate(R.layout.common_loading_footer_layout, this, false);
        addView(mLoadingFooter, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        LayoutParams footerLayoutParams = (LayoutParams) mLoadingFooter.getLayoutParams();
        footerLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        mLoadingFooter.measure(0, 0);
        mLoadingFooterHeight = mLoadingFooter.getMeasuredHeight();
        mLoadingFooter.setVisibility(INVISIBLE);

        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RefreshRecyclerView);

        // getDimension和getDimensionPixelOffset的功能差不多,都是获取某个dimen的值,
        // 如果是dp或sp的单位,将其乘以density,如果是px,则不乘;两个函数的区别是一个返回float,一个返回int.
        // getDimensionPixelSize则不管写的是dp还是sp还是px,都会乘以denstiy.
        int wheelColor = a.getColor(R.styleable.RefreshRecyclerView_loading_wheel_color, getResources().getColor(R.color.colorAccent));
        // getDimension默认值之所以要dp转px是因为它不带单位，所以会直接返回设置的值，我们其实想要的是dp对应的像素值，所以这里直接转为像素值
        int wheelBarWidth = (int) a.getDimension(R.styleable.RefreshRecyclerView_loading_wheel_bar_width, dip2px(getContext(), 2));
        int wheelWidthAndHeight = (int) a.getDimension(R.styleable.RefreshRecyclerView_loading_wheel_width_height, dip2px(getContext(), 20));
        int loadingTextSize = (int) a.getDimension(R.styleable.RefreshRecyclerView_loading_text_size, sp2px(getContext(), 12));
        int loadingTextColor = a.getColor(R.styleable.RefreshRecyclerView_loading_text_color, Color.parseColor("#72000000"));
        String loadingTextString = a.getString(R.styleable.RefreshRecyclerView_loading_text);

        mLoadAllTextSize = (int) a.getDimension(R.styleable.RefreshRecyclerView_load_all_text_size, sp2px(getContext(), 12));
        mLoadAllTextColor = a.getColor(R.styleable.RefreshRecyclerView_load_all_text_color, Color.parseColor("#72000000"));
        mLoadAllTextString = a.getString(R.styleable.RefreshRecyclerView_load_all_text);
        if (TextUtils.isEmpty(mLoadAllTextString)) {
            mLoadAllTextString = getResources().getString(R.string.no_more_products);
        }

        a.recycle();

        mWheel = (ProgressWheel) findViewById(R.id.pw_favorite);
        mWheel.setBarColor(wheelColor);
        mWheel.setBarWidth(wheelBarWidth);
        ViewGroup.LayoutParams mWheelLayoutParams = mWheel.getLayoutParams();
        mWheelLayoutParams.width = mWheelLayoutParams.height = wheelWidthAndHeight;
        mWheel.setLayoutParams(mWheelLayoutParams);

        mLoadingFooterText = (TextView) findViewById(R.id.tv_loading_footer);
        mLoadingFooterText.setTextSize(TypedValue.COMPLEX_UNIT_PX, loadingTextSize);
        mLoadingFooterText.setTextColor(loadingTextColor);
        mLoadingFooterText.setText(TextUtils.isEmpty(loadingTextString) ? getResources().getString(R.string.loading) : loadingTextString);

    }


    public void setWheelBarColor(int color) {
        mWheel.setBarColor(color);
    }

    public void setWheelBarWidth(int width) {
        mWheel.setBarWidth(width);
    }

    public void setWheelWidthAndHeight(int diameter) {
        ViewGroup.LayoutParams mWheelLayoutParams = mWheel.getLayoutParams();
        mWheelLayoutParams.width = mWheelLayoutParams.height = diameter;
        mWheel.setLayoutParams(mWheelLayoutParams);
    }

    public void setLoadingFooterTextSize(int size) {
        mLoadingFooterText.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
    }

    public void setLoadingFooterTextColor(int color) {
        mLoadingFooterText.setTextColor(color);
    }

    public void setLoadingFooterText(String str) {
        mLoadingFooterText.setText(str);
    }

    public void setLoadAllFooterText(String str) {
        this.mLoadAllTextString = str;
    }

    public void setLoadAllFooterTextSize(int loadAllTextSize) {
        this.mLoadAllTextSize = loadAllTextSize;
    }

    public void setLoadAllFooterTextColor(int loadAllTextColor) {
        this.mLoadAllTextColor = loadAllTextColor;
    }

    public int getLoadAllFooterTextSize() {
        return mLoadAllTextSize;
    }

    public int getLoadAllFooterTextColor() {
        return mLoadAllTextColor;
    }

    public String getLoadAllFooterText() {
        return mLoadAllTextString;
    }

    private int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    private int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public void setAdapterAndLayoutManager(BaseRecyclerViewAdapter adapter, RecyclerView.LayoutManager layoutManager) {
        mLayoutManager = layoutManager;
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = adapter;
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (mScrollListeners != null)
                    mScrollListeners.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && mRefreshListener != null && mAdapter != null && !mAdapter.isDisableFooter()) {
                    if (mIsLoadAll) return;
                    if (mLayoutManager instanceof StaggeredGridLayoutManager) {
                        ((StaggeredGridLayoutManager) mLayoutManager).findLastCompletelyVisibleItemPositions(mLastVisiblePosition);
                    }
                    if (mLayoutManager instanceof GridLayoutManager) {
                        mLastVisiblePosition[1] = mLastVisiblePosition[0] = ((GridLayoutManager) mLayoutManager).findLastCompletelyVisibleItemPosition();
                    }
                    if (mLayoutManager instanceof LinearLayoutManager) {
                        mLastVisiblePosition[1] = mLastVisiblePosition[0] = ((LinearLayoutManager) mLayoutManager).findLastCompletelyVisibleItemPosition();
                    }
                    if ((mLastVisiblePosition[1] == mAdapter.getItemCount() - 1) ||
                            mLastVisiblePosition[0] == mAdapter.getItemCount() - 1) {
                        KLog.e("显示正在加载尾部");
                        mLoadingFooter.setVisibility(View.VISIBLE);
                        ObjectAnimator.ofFloat(mSwipeRefreshLayout, "translationY", 0, -mLoadingFooterHeight).setDuration(0).start();
                        mRefreshListener.onLoadMore();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (mScrollListeners != null)
                    mScrollListeners.onScrolled(recyclerView, dx, dy);
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mRefreshListener != null) {
                    mRefreshListener.onRefresh();
                    restoreLoadAll();
                }
            }
        });
    }

    public void setOnRefreshListener(OnRefreshListener listener) {
        mRefreshListener = listener;
    }

    public void refreshComplete() {
        mSwipeRefreshLayout.setRefreshing(false);
        restoreLoadAll();
    }

    public interface OnRefreshListener {
        void onRefresh();

        void onLoadMore();
    }

    public void addOnScrollListener(OnScrollListener listener) {
        this.mScrollListeners = listener;
    }

    public abstract static class OnScrollListener {

        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        }

        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        }
    }

    public void addItemDecoration(RecyclerView.ItemDecoration decor) {
        mRecyclerView.addItemDecoration(decor, -1);
    }

    public void setItemAnimator(RecyclerView.ItemAnimator animator) {
        mRecyclerView.setItemAnimator(animator);
    }

    public void setHasFixedSize(boolean hasFixedSize) {
        mRecyclerView.setHasFixedSize(hasFixedSize);
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public void loadMoreComplete() {
        ObjectAnimator.ofFloat(mSwipeRefreshLayout, "translationY", -mLoadingFooterHeight, 0).setDuration(0).start();
        mLoadingFooter.setVisibility(INVISIBLE);
    }

    public void loadMoreOrRefreshFailed() {
        loadMoreComplete();
    }

    public boolean isLoadAll() {
        return mIsLoadAll;
    }

    public void loadAllComplete() {
        mIsLoadAll = true;
        mRecyclerView.getAdapter().notifyItemInserted(mRecyclerView.getAdapter().getItemCount());
        mRecyclerView.smoothScrollToPosition(mRecyclerView.getAdapter().getItemCount());
        if (ViewCompat.getTranslationY(mSwipeRefreshLayout) != 0) {
            ObjectAnimator.ofFloat(mSwipeRefreshLayout, "translationY", -mLoadingFooterHeight, 0).setDuration(0).start();
            mLoadingFooter.setVisibility(INVISIBLE);
        }
    }

    // 显示圆形刷新头
    public void showRefreshCircleView() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    // 隐藏圆形刷新头
    public void hideRefreshCircleView() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    public void smoothScrollToPosition(int position) {
        mRecyclerView.smoothScrollToPosition(position);
    }

    public void scrollToPosition(int position) {
        mRecyclerView.scrollToPosition(position);
    }

    private void restoreLoadAll() {
        mIsLoadAll = false;
    }

}
