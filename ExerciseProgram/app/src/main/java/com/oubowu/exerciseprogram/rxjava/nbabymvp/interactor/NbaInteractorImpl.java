package com.oubowu.exerciseprogram.rxjava.nbabymvp.interactor;

import com.oubowu.exerciseprogram.rxjava.nbabymvp.RetrofitManager;
import com.oubowu.exerciseprogram.rxjava.nbabymvp.listener.OnRequestListener;
import com.oubowu.exerciseprogram.rxjava.nbabymvp.model.NBA;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * 类名： NbaInteractor
 * 作者: oubowu
 * 时间： 2016/1/19 14:09
 * 功能：
 * svn版本号:$$Rev$$
 * 更新时间:$$Date$$
 * 更新人:$$Author$$
 * 更新描述:
 */
public class NbaInteractorImpl implements NbaInteractor {

    @Override
    public Subscription requestDatas(OnRequestListener listener) {
        return RetrofitManager
                .builder()
                .getNbaObservable()
                .doOnSubscribe(listener::beforeRequest)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<NBA>() {
                    @Override
                    public void onCompleted() {
                        listener.requestComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.requestError(e);
                    }

                    @Override
                    public void onNext(NBA nba) {
                        listener.requestSuccess(nba);
                    }
                });
    }

}
