package com.example.myapplication.network

import com.example.myapplication.models.MoviePopular
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieRequest {
    @GET("movie/top_rated")
    fun getMoviePopular(@Query("api_key")apiKey:String): Observable<MoviePopular>
}