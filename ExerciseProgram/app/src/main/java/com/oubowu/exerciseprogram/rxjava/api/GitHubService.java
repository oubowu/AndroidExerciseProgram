package com.oubowu.exerciseprogram.rxjava.api;

import com.oubowu.exerciseprogram.rxjava.bean.Repo;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * ClassName:
 * Author:oubowu
 * Fuction:
 * CreateDate:2016/1/18 5:06
 * UpdateUser:
 * UpdateDate:
 */
public interface GitHubService {
    @GET("/users/{user}")
    Call<Repo> listRepos(@Path("user") String user);
}