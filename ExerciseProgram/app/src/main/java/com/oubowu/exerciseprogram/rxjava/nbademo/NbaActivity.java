package com.oubowu.exerciseprogram.rxjava.nbademo;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;

import com.oubowu.exerciseprogram.BaseActivity;
import com.oubowu.exerciseprogram.BuildConfig;
import com.oubowu.exerciseprogram.R;
import com.oubowu.exerciseprogram.refreshrecyclerview.RefreshRecyclerView;
import com.oubowu.exerciseprogram.rxjava.nbademo.adapter.NbaAdapter;
import com.oubowu.exerciseprogram.rxjava.model.Repo;
import com.oubowu.exerciseprogram.rxjava.model.ifeng.TopicList;
import com.oubowu.exerciseprogram.rxjava.nbademo.model.MatchList;
import com.oubowu.exerciseprogram.rxjava.nbademo.model.NBA;
import com.oubowu.exerciseprogram.rxjava.service.GitHubService;
import com.oubowu.exerciseprogram.rxjava.service.IFengService;
import com.oubowu.exerciseprogram.rxjava.nbademo.service.NbaService;
import com.oubowu.exerciseprogram.utils.ToastUtil;
import com.socks.library.KLog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * ClassName:
 * Author:oubowu
 * Fuction:
 * CreateDate:2016/1/18 1:30
 * UpdateUser:
 * UpdateDate:
 */
public class NbaActivity extends BaseActivity {

    @Bind(R.id.rrv)
    RefreshRecyclerView rrv;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @OnClick(R.id.bt)
    void toMvpActivity() {
        startActivity(new Intent(this, com.oubowu.exerciseprogram.rxjava.nbabymvp.NbaActivity.class));
    }

    NbaAdapter mNbaAdapter;

    private Subscription mSubscription;

    @Override
    protected int provideLayoutId() {

        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
        }

        return R.layout.activity_nba;
    }

    @Override
    protected void initView() {

        String title = "NBA赛事消息";
        setTitle(title);

    }

    @Override
    protected void initData() {

        // normalMethod();

        mNbaAdapter = new NbaAdapter(this, null, rrv, true, true);

        rrv.setAdapterAndLayoutManager(mNbaAdapter, new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        rrv.setOnRefreshListener(new RefreshRecyclerView.OnRefreshListenerAdapter() {
            @Override
            public void onRefresh() {
                rxjavaMethod();
            }
        });

        rxjavaMethod();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!mSubscription.isUnsubscribed())
            mSubscription.unsubscribe();
    }

    private void rxjavaMethod() {

        mSubscription = RetrofitManager
                .builder()
                .getNbaObservable()
                .doOnSubscribe(() -> rrv.post(rrv::showRefreshCircleView))
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<NBA>() {
                    @Override
                    public void onCompleted() {
                        rrv.hideRefreshCircleView();
                    }

                    @Override
                    public void onError(Throwable e) {
                        rrv.hideRefreshCircleView();
                        ToastUtil.showShort(NbaActivity.this, e.getMessage());
                    }

                    @Override
                    public void onNext(NBA nba) {
                        for (MatchList info : nba.result.list) {
                            mNbaAdapter.addMoreDatas(info.tr);
                        }
                    }
                });

    }

    private void normalMethod() {

        String BASE_URL = "http://op.juhe.cn/onebox/basketball/";
        String KEY = "fcf4e4431700617553c11f2ca1c604eb";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final NbaService nbaService = retrofit.create(NbaService.class);
        final Call<NBA> nbaCall = nbaService.getNbaInfo(KEY);
        nbaCall.enqueue(new Callback<NBA>() {
            @Override
            public void onResponse(Response<NBA> response, Retrofit retrofit) {
                KLog.e(response.body().reason);
            }

            @Override
            public void onFailure(Throwable t) {
                KLog.e(t.getMessage());
            }
        });
        // 还可以取消请求
        // nbaCall.cancel();
    }

    private void apiTest() {
        String url = "https://api.github.com/users";
        Retrofit retrofit1 = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final GitHubService gitHubService = retrofit1.create(GitHubService.class);
        final Call<Repo> drakeet = gitHubService.listRepos("drakeet");
        drakeet.enqueue(new Callback<Repo>() {
            @Override
            public void onResponse(Response<Repo> response, Retrofit retrofit) {
                KLog.e(response.body().getName());
            }

            @Override
            public void onFailure(Throwable t) {
                KLog.e(t.getMessage());
            }
        });


        url = "http://i.ifeng.com/";
        Retrofit retrofit2 = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final IFengService iFengService = retrofit2.create(IFengService.class);
        Map<String, String> map = new HashMap<>();
        map.put("pageindex", "1");
        map.put("pagesize", "15");
        final Call<List<TopicList>> topicListCall = iFengService.getTopicList(map);
        topicListCall.enqueue(new Callback<List<TopicList>>() {
            @Override
            public void onResponse(Response<List<TopicList>> response, Retrofit retrofit) {
                KLog.e(response.body().get(0).meta.documentId);
            }

            @Override
            public void onFailure(Throwable t) {
                KLog.e(t.getMessage());
            }
        });
    }

}
