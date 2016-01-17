package com.oubowu.exerciseprogram.rxjava;

import com.oubowu.exerciseprogram.BaseActivity;
import com.oubowu.exerciseprogram.R;
import com.oubowu.exerciseprogram.rxjava.api.GitHubService;
import com.oubowu.exerciseprogram.rxjava.api.IFengApi;
import com.oubowu.exerciseprogram.rxjava.bean.Item;
import com.oubowu.exerciseprogram.rxjava.bean.Repo;
import com.oubowu.exerciseprogram.rxjava.bean.TopicList;
import com.oubowu.exerciseprogram.utils.ToastUtil;
import com.socks.library.KLog;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * ClassName:
 * Author:oubowu
 * Fuction:
 * CreateDate:2016/1/18 1:30
 * UpdateUser:
 * UpdateDate:
 */
public class IfengActivity extends BaseActivity {

    @Override
    protected int provideLayoutId() {
        return R.layout.activity_ifeng;
    }

    @Override
    protected void initView() {

    }

    public static final String BASE_URL = "http://i.ifeng.com/topicList";

    @Override
    protected void initData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final IFengApi iFengApiService = retrofit.create(IFengApi.class);
        // 异步请求这个API，我们如下调用这个service
        final Call<TopicList> call = iFengApiService.getTopicList("1"/*, "15"*/);
        call.enqueue(new Callback<TopicList>() {
            @Override
            public void onResponse(Response<TopicList> response, Retrofit retrofit) {
                final TopicList topicList = response.body();
                for (Item item : topicList.getBody().getItem()) {
                    KLog.e("收到：" + item.getTitle());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                KLog.e(t.getMessage());
                ToastUtil.showShort(IfengActivity.this, t.getMessage());
            }
        });

        String url = "https://api.github.com/users/drakeet";
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

    }


}
