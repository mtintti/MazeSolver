package com.example.mazesolver

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

object RetrofitInstance {
    private const val BASE_URL = "https://v2.jokeapi.dev/"

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
interface ApiService {
    @GET("joke/Programming")
    fun getProgrammingJoke(
        @Query("format") format: String = "json",
        @Query("blacklistFlags") blacklistFlags: String = "nsfw,religious,political,racist,sexist,explicit"
    ): Call<JokeResponse>
}



data class JokeResponse(
    val error: Boolean,
    val category: String,
    val type: String,
    val joke: String?,
    val setup: String?,
    val delivery: String?,
    val flags: Flags,
    val id: Int,
    val safe: Boolean,
    val lang: String
)

data class Flags(
    val nsfw: Boolean,
    val religious: Boolean,
    val political: Boolean,
    val racist: Boolean,
    val sexist: Boolean,
    val explicit: Boolean
)
