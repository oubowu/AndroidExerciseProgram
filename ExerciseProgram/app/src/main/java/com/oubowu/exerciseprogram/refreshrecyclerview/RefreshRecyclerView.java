package com.oubowu.exerciseprogram.refreshrecyclerview;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
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
public class RefreshRecyclerView extends RelativeLayout implements View.OnClickListener {

    private FloatingActionButton mFab;
    private float offset = 200;
    private AppBarLayout mAppBarLayout;

    /**
     * 悬浮按钮是否添加到布局中的标示位,true为不添加，false为添加
     */
    private boolean mFloatButtonDisable;

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return mSwipeRefreshLayout;
    }

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private View mLoadingFooter;
    private OnScrollListener mScrollListeners;
    private OnRefreshListener mRefreshListener;
    private RecyclerView.LayoutManager mLayoutManager;
    private BaseRecyclerViewAdapter mAdapter;
    private int mLastVisiblePosition[] = new int[2];
    private int mFirstVisiblePosition[] = new int[2];
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
        addView(mSwipeRefreshLayout, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        mRecyclerView = new RecyclerView(context);

        mRecyclerView.setOnTouchListener(new View.OnTouchListener() {

            private int downX, downY;
            private int upX, upY;
            private int mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(ViewConfiguration.get(getContext()));

            @Override
            public boolean onTouch(View v, MotionEvent ev) {
                switch (ev.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        downX = (int) ev.getX();
                        downY = (int) ev.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        upX = (int) ev.getX();
                        upY = (int) ev.getY();
                        if (upX - downX > mTouchSlop && (upX - downX) > Math.abs(upY - downY) * 2) {
                            ((ViewParent) ((Activity) getContext()).findViewById(android.R.id.content)).requestDisallowInterceptTouchEvent(false);
                        } else {
                            ((ViewParent) ((Activity) getContext()).findViewById(android.R.id.content)).requestDisallowInterceptTouchEvent(true);
                        }
                        break;
                }
                return false;
            }
        });

        mSwipeRefreshLayout.addView(mRecyclerView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

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

        mFloatButtonDisable = a.getBoolean(R.styleable.RefreshRecyclerView_float_button_disable, true);

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

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        if (mFloatButtonDisable)
            return;

        if (getParent() instanceof CoordinatorLayout) {
            CoordinatorLayout mCoordinatorLayout = (CoordinatorLayout) getParent();
            for (int i = 0; i < mCoordinatorLayout.getChildCount(); i++) {
                if (mCoordinatorLayout.getChildAt(i) instanceof AppBarLayout) {
                    mAppBarLayout = (AppBarLayout) mCoordinatorLayout.getChildAt(i);
                    break;
                }
            }
            mFab = (FloatingActionButton) ((ViewGroup) getParent()).findViewById(R.id.fab);
        } else {
            mFab = (FloatingActionButton) LayoutInflater.from(getContext()).inflate(R.layout.refresh_float_action_button, this, false);
            addView(mFab, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            LayoutParams fabLp = (LayoutParams) mFab.getLayoutParams();
            fabLp.addRule(ALIGN_PARENT_BOTTOM);
            fabLp.addRule(ALIGN_PARENT_RIGHT);
            int m = getResources().getDimensionPixelSize(R.dimen.fab_margin);
            fabLp.setMargins(m, m, m, m);
        }
        if (mFab != null) {
            mFab.setOnClickListener(this);
            mFab.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    offset = mFab.getMeasuredHeight() + getResources().getDimensionPixelSize(R.dimen.fab_margin) + mFab.getPaddingBottom();
                    ObjectAnimator.ofFloat(mFab, "translationY", 0, offset).setDuration(0).start();
                    mFab.setTag("hide");
                    mFab.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            });
        }

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

        // 瀑布流需要做特殊处理
        if (mLayoutManager instanceof StaggeredGridLayoutManager) {
            // 根据列数生成mLastVisiblePosition数组和mFirstVisiblePosition数组
            final int spanCount = ((StaggeredGridLayoutManager) mLayoutManager).getSpanCount();
            KLog.e("瀑布流的列数：" + spanCount);
            mLastVisiblePosition = new int[spanCount];
            mFirstVisiblePosition = new int[spanCount];
        }

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (mScrollListeners != null)
                    mScrollListeners.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && !mIsLoadAll
                        && mRefreshListener != null
                        && mAdapter != null
                        && !mAdapter.disableLoadMore()) {
                    if (mAdapter == null || mAdapter.getDatas() == null)
                        return;
                    if (mLayoutManager instanceof StaggeredGridLayoutManager) {
                        ((StaggeredGridLayoutManager) mLayoutManager).findLastCompletelyVisibleItemPositions(mLastVisiblePosition);
                    }
                    if (mLayoutManager instanceof GridLayoutManager) {
                        mLastVisiblePosition[0] = ((GridLayoutManager) mLayoutManager).findLastCompletelyVisibleItemPosition();
                    }
                    if (mLayoutManager instanceof LinearLayoutManager) {
                        mLastVisiblePosition[0] = ((LinearLayoutManager) mLayoutManager).findLastCompletelyVisibleItemPosition();
                    }
                    if (mLayoutManager instanceof StaggeredGridLayoutManager) {
                        for (int i = 0; i < mLastVisiblePosition.length; i++) {
                            if (mLastVisiblePosition[i] == mAdapter.getItemCount() - 1) {
                                mLoadingFooter.setVisibility(View.VISIBLE);
                                ObjectAnimator.ofFloat(mSwipeRefreshLayout, "translationY", 0, -mLoadingFooterHeight).setDuration(0).start();
                                if (mRefreshListener != null)
                                    mRefreshListener.onLoadMore();
                                break;
                            }
                        }
                    } else if (mLastVisiblePosition[0] == mAdapter.getItemCount() - 1) {
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

                if (mAdapter == null || mAdapter.getDatas() == null)
                    return;

                if (mFab == null || mFab.getTag() == null)
                    return;

                if (mLayoutManager instanceof StaggeredGridLayoutManager) {
                    ((StaggeredGridLayoutManager) mLayoutManager).findFirstCompletelyVisibleItemPositions(mFirstVisiblePosition);
                }
                if (mLayoutManager instanceof GridLayoutManager) {
                    mFirstVisiblePosition[0] = ((GridLayoutManager) mLayoutManager).findFirstCompletelyVisibleItemPosition();
                }
                if (mLayoutManager instanceof LinearLayoutManager) {
                    mFirstVisiblePosition[0] = ((LinearLayoutManager) mLayoutManager).findFirstCompletelyVisibleItemPosition();
                }

                if (mFirstVisiblePosition[0] == 0) {
                    // 滑动到顶部，隐藏悬浮按钮
                    if (mFab.getTag().equals("show")) {
                        ObjectAnimator.ofFloat(mFab, "translationY", 0, offset).setDuration(300).start();
                        mFab.setTag("hide");
                    }
                } else if (mFab.getTag().equals("hide")) {
                    //  显示悬浮按钮
                    ObjectAnimator.ofFloat(mFab, "translationY", offset, 0).setDuration(300).start();
                    mFab.setTag("show");
                }
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
        hideRefreshCircleView();
        restoreLoadAll();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fab) {
            if (mFab.getTag().equals("hide"))
                return;
            if (mFirstVisiblePosition[0] <= 80) {
                mRecyclerView.smoothScrollToPosition(0);
            } else {
                mRecyclerView.scrollToPosition(0);
            }
            if (mAppBarLayout != null)
                mAppBarLayout.setExpanded(true);
        }
    }

    public interface OnRefreshListener {
        void onRefresh();

        void onLoadMore();
    }

    public static class OnRefreshListenerAdapter implements OnRefreshListener {
        @Override
        public void onRefresh() {

        }

        @Override
        public void onLoadMore() {

        }
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
        hideRefreshCircleView();
    }

    public boolean isLoadAll() {
        return mIsLoadAll;
    }

    public void loadAllComplete() {
        loadAllComplete(false);
    }

    /**
     * 告知全部加载完毕
     *
     * @param scrollToFinalPosition true的话滑动到尾部
     */
    public void loadAllComplete(boolean scrollToFinalPosition) {
        mIsLoadAll = true;
        mRecyclerView.getAdapter().notifyItemInserted(mRecyclerView.getAdapter().getItemCount());
        if (scrollToFinalPosition)
            mRecyclerView.smoothScrollToPosition(mRecyclerView.getAdapter().getItemCount());
        if (ViewCompat.getTranslationY(mSwipeRefreshLayout) != 0) {
            ObjectAnimator.ofFloat(mSwipeRefreshLayout, "translationY", -mLoadingFooterHeight, 0).setDuration(0).start();
            mLoadingFooter.setVisibility(INVISIBLE);
        }
        hideRefreshCircleView();
    }

    // 显示圆形刷新头
    public void showRefreshCircleView() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    // 隐藏圆形刷新头
    public void hideRefreshCircleView() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    // 是否在刷新
    public boolean isRefreshing() {
        return mSwipeRefreshLayout.isRefreshing();
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

    private View mEmptyView;

    public void setEmptyView(View view) {

        if (mEmptyView != null)
            this.removeView(mEmptyView);

        mEmptyView = view;

        if (mEmptyView.getParent() != null)
            ((ViewGroup) mEmptyView.getParent()).removeView(mEmptyView);

        this.addView(mEmptyView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        mEmptyView.setVisibility(GONE);
    }

    public void showEmptyView() {
        if (mEmptyView != null)
            mEmptyView.setVisibility(VISIBLE);
    }

    public void hideEmptyView() {
        if (mEmptyView != null)
            mEmptyView.setVisibility(INVISIBLE);
    }

}