package com.oubowu.exerciseprogram.rxjava.nbademo.service;

import com.oubowu.exerciseprogram.rxjava.nbademo.model.NBA;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * 类名： NbaService
 * 作者: oubowu
 * 时间： 2016/1/18 9:45
 * 功能：
 * svn版本号:$$Rev$$
 * 更新时间:$$Date$$
 * 更新人:$$Author$$
 * 更新描述:
 */
public interface NbaService {

    // http://op.juhe.cn/onebox/basketball/nba?key=fcf4e4431700617553c11f2ca1c604eb
    @GET("nba")
    Call<NBA> getNbaInfo(@Query("key") String key);


    /**
     * retrofit 支持 rxjava 整合
     * 这种方法适用于新接口
     */
    @GET("nba")
    Observable<NBA> getRxNbaInfo(@Query("key") String key);

    /*在Retrofit 2.0中我们还可以在@Url里面定义完整的URL：

    public interface APIService {
        @POST("http://api.demo.come/base/user/list")
        Call<User> login();
    }
    这种情况下Base URL会被忽略。*/

}
