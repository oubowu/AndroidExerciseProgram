package com.oubowu.exerciseprogram.rxjava.api;

import com.oubowu.exerciseprogram.rxjava.bean.TopicList;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * ClassName:
 * Author:oubowu
 * Fuction:
 * CreateDate:2016/1/18 3:26
 * UpdateUser:
 * UpdateDate:
 */
public interface IFengApi {

    @GET("/pageindex/{index}"/*/pagesize/{size}*/)
    Call<TopicList> getTopicList(@Path("index") String pageIndex/*, @Path("size") String pageSize*/);

    /**
     注意到每个endpoint 都指定了一个关于HTTP(GET, POST, 等等。)  方法的注解以及用于分发网络调用的方法。而且这些方法的参数也可以有特殊的注解。
     注解	描述
     @Path variable substitution for the API endpoint (i.e. username will be swapped for {username} in the URL endpoint).
     @Query specifies the query key name with the value corresponding to the value of that annotated parameter.
     @Body payload for the POST call
     */
}
