package com.oubowu.exerciseprogram.rxjava.service;

import com.oubowu.exerciseprogram.rxjava.model.Repo;

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

    // "https://api.github.com/users/drakeet"
    @GET("/users/{user}")
    Call<Repo> listRepos(@Path("user") String user);
}