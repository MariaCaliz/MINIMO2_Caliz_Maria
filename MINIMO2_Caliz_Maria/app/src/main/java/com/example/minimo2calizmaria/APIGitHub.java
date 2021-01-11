package com.example.minimo2calizmaria;

import com.example.minimo2calizmaria.models.*;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIGitHub {

    @GET("/users/{username}")
    Call<User> informacionUser (@Path("username") String user);

    @GET("/users/{username}/followers")
    Call<List<User>> listaFollowers(@Path("username") String user);

    @GET("/users/{username}/following")
    Call<List<User>> listaFollowing(@Path("username") String user);

    @GET("/users/{username}/repos")
    Call<List<Repositories>> listaRepos(@Path("username") String user);




}
