package com.oubowu.exerciseprogram.rxjava.nbabymvp;


import com.oubowu.exerciseprogram.rxjava.nbabymvp.model.NBA;
import com.oubowu.exerciseprogram.rxjava.nbabymvp.service.NbaService;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 类名： RetrofitManager
 * 作者: oubowu
 * 时间： 2016/1/18 17:29
 * 功能：
 * svn版本号:$$Rev$$
 * 更新时间:$$Date$$
 * 更新人:$$Author$$
 * 更新描述:
 */
public class RetrofitManager {

    public static final String BASE_URL = "http://op.juhe.cn/onebox/basketball/";
    public static final String KEY = "fcf4e4431700617553c11f2ca1c604eb";

    private final Observable<NBA> nbaObservable;

    private RetrofitManager() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        final NbaService nbaService = retrofit.create(NbaService.class);
        nbaObservable = nbaService.getRxNbaInfo(KEY);
    }

    public static RetrofitManager builder() {
        return new RetrofitManager();
    }

    public Observable<NBA> getNbaObservable() {
        return nbaObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                        // 对API调用了observeOn(MainThread)之后，线程会跑在主线程上，包括onComplete也是，
                        // unsubscribe也在主线程，然后如果这时候调用call.cancel会导致NetworkOnMainThreadException
                        // 加一句unsubscribeOn(io)
                .unsubscribeOn(Schedulers.io());
    }

}
