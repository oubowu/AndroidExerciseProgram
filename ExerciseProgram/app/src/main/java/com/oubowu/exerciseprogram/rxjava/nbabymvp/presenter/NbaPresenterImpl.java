package com.oubowu.exerciseprogram.rxjava.nbabymvp.presenter;

import com.oubowu.exerciseprogram.rxjava.nbabymvp.interactor.NbaInteractor;
import com.oubowu.exerciseprogram.rxjava.nbabymvp.interactor.NbaInteractorImpl;
import com.oubowu.exerciseprogram.rxjava.nbabymvp.listener.OnRequestListener;
import com.oubowu.exerciseprogram.rxjava.nbabymvp.model.NBA;
import com.oubowu.exerciseprogram.rxjava.nbabymvp.model.Tr;
import com.oubowu.exerciseprogram.rxjava.nbabymvp.view.NbaView;
import com.socks.library.KLog;

import rx.Subscription;

/**
 * 类名： NbaPresenterImpl
 * 作者: oubowu
 * 时间： 2016/1/19 14:08
 * 功能：
 * svn版本号:$$Rev$$
 * 更新时间:$$Date$$
 * 更新人:$$Author$$
 * 更新描述:
 */
public class NbaPresenterImpl implements NbaPresenter, OnRequestListener {

    private NbaView mNbaView;
    private NbaInteractor mNbaInteractor;
    private Subscription mSubscription;

    private boolean mIsInit = true;

    public NbaPresenterImpl(NbaView view) {

        mNbaView = view;

        mNbaInteractor = new NbaInteractorImpl();

    }

    @Override
    public void onResume() {
        // 网络请求初始化数据
        mSubscription = mNbaInteractor.requestDatas(this);
    }


    @Override
    public void onDestroy() {
        mNbaView = null;
        if (!mSubscription.isUnsubscribed())
            mSubscription.unsubscribe();
    }

    @Override
    public void onItemClicked(int position) {
        final Tr tr = mNbaView.supportDatas().get(position);
        mNbaView.showMsg(tr.player1 + " " + tr.score + " " + tr.player2);
    }

    @Override
    public void refresh() {
        mSubscription = mNbaInteractor.requestDatas(this);
    }

    @Override
    public void beforeRequest() {
        mNbaView.showProgress();
    }

    @Override
    public void requestError(Throwable e) {
        mNbaView.hideProgress();
        KLog.e(e.getMessage());
        mNbaView.showMsg(e.getMessage());
    }

    @Override
    public void requestSuccess(NBA nba) {
        if (mIsInit) {
            mIsInit = false;
            mNbaView.initRecyclerViewDatas(nba);
        } else {
            mNbaView.refreshRecyclerViewDatas(nba);
        }
    }

    @Override
    public void requestComplete() {
        mNbaView.hideProgress();
    }
}
