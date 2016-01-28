package com.oubowu.exerciseprogram.rxjava.nbabymvp;

import android.os.StrictMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.oubowu.exerciseprogram.BaseActivity;
import com.oubowu.exerciseprogram.BuildConfig;
import com.oubowu.exerciseprogram.R;
import com.oubowu.exerciseprogram.refreshrecyclerview.RefreshRecyclerView;
import com.oubowu.exerciseprogram.refreshrecyclerview.listener.OnItemClickAdapter;
import com.oubowu.exerciseprogram.rxjava.nbabymvp.adapter.NbaAdapter;
import com.oubowu.exerciseprogram.rxjava.nbabymvp.model.MatchList;
import com.oubowu.exerciseprogram.rxjava.nbabymvp.model.NBA;
import com.oubowu.exerciseprogram.rxjava.nbabymvp.model.Tr;
import com.oubowu.exerciseprogram.rxjava.nbabymvp.presenter.NbaPresenter;
import com.oubowu.exerciseprogram.rxjava.nbabymvp.presenter.NbaPresenterImpl;
import com.oubowu.exerciseprogram.rxjava.nbabymvp.view.NbaView;
import com.oubowu.exerciseprogram.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * ClassName:
 * Author:oubowu
 * Fuction:
 * CreateDate:2016/1/18 1:30
 * UpdateUser:
 * UpdateDate:
 */
public class NbaActivity extends BaseActivity implements NbaView {

    @Bind(R.id.rrv)
    RefreshRecyclerView mRecyclerView;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.bt)
    Button mButton;

    private NbaAdapter mNbaAdapter;

    private NbaPresenter mNbaPresenter;

    @Override
    protected int provideLayoutId() {

        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
        }

        mNbaPresenter = new NbaPresenterImpl(this);

        return R.layout.activity_nba_mvp;
    }

    @Override
    protected void initView() {

        mToolbar.setTitle("NBA赛事消息");

        mButton.setVisibility(View.GONE);

    }

    @Override
    protected void initData() {


        mNbaAdapter = new NbaAdapter(this, null, mRecyclerView, true, true);

        mRecyclerView.setAdapterAndLayoutManager(mNbaAdapter, new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mRecyclerView.setOnRefreshListener(new RefreshRecyclerView.OnRefreshListenerAdapter() {
            @Override
            public void onRefresh() {
                mNbaPresenter.refresh();
            }
        });

        mNbaAdapter.setOnItemClickedListener(new OnItemClickAdapter() {
            @Override
            public void onItemClick(View view, int position) {
                mNbaPresenter.onItemClicked(position);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mNbaPresenter.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mNbaPresenter.onResume();
    }

    @Override
    public void showProgress() {
        mRecyclerView.post(mRecyclerView::showRefreshCircleView);
    }

    @Override
    public void hideProgress() {
        mRecyclerView.hideRefreshCircleView();
    }

    @Override
    public void initRecyclerViewDatas(NBA nba) {
        List<Tr> trs = new ArrayList<>();
        for (MatchList info : nba.result.list) {
            trs.addAll(info.tr);
        }
        mNbaAdapter.setDatas(trs);
    }

    @Override
    public void refreshRecyclerViewDatas(NBA nba) {
        List<Tr> trs = new ArrayList<>();
        for (MatchList info : nba.result.list) {
            trs.addAll(info.tr);
        }
        mNbaAdapter.addMoreDatas(trs);
    }

    @Override
    public void showMsg(String msg) {
        ToastUtil.showShort(this, msg);
    }

    @Override
    public List<Tr> supportDatas() {
        return mNbaAdapter.getDatas();
    }

}
