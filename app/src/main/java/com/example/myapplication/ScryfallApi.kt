package com.example.myapplication

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ScryfallApi {
    @GET("cards/search")
    suspend fun getCard(@Query("q") cardName: String): Card
}

private const val BASE_URL = "https://api.scryfall.com/"
val retrofitInstance = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()