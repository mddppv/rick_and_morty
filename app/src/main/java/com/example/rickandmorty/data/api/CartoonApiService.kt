package com.example.rickandmorty.data.api

import com.example.rickandmorty.data.model.CartoonEpisodeModel
import com.example.rickandmorty.data.model.CartoonModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CartoonApiService {

    @GET("character")
    suspend fun getCharacter(): Response<CartoonModel>

    @GET("episode")
    suspend fun getEpisode(): Response<CartoonEpisodeModel>

    @GET("character/{id}")
    suspend fun getCharacterById(@Path("id") id: Int): Response<CartoonModel.Result>

    @GET("episode/{id}")
    suspend fun getEpisodeById(@Path("id") id: Int): Response<CartoonEpisodeModel.Result>

}