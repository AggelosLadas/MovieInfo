package com.example.movieinfo

import com.google.gson.internal.GsonBuildConfig
import retrofit2.http.GET
import retrofit2.http.Query

interface OmdbApi {
    @GET("/")
    suspend fun searchMovies(
        @Query("s") title : String,
        @Query("apikey") key : String = BuildConfig.apiKeySafe
    ) : MovieSearchResponse

}