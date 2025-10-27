package com.example.movieinfo

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api : OmdbApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://www.omdbapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OmdbApi::class.java)
    }
}