package com.tonyjs.basicretrofit.example;

import java.util.List;
import java.util.Map;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.QueryMap;

/**
 * Created by tonyjs on 15. 1. 13..
 */
public interface ApiInterface {
    String END_POINT = "https://api.github.com";

    @GET("/users/{user}/repos")
    public List<Repo> listRepos(@Path("user") String user);

}
