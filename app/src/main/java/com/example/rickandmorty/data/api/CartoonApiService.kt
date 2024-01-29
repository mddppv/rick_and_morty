package com.example.rickandmorty.data.api

import com.example.rickandmorty.data.model.CartoonModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CartoonApiService {

    @GET("character")
    suspend fun getCharacter(): Response<CartoonModel>

    @GET("character/{id}")
    suspend fun getCharacterById(@Path("id") id: Int): Response<CartoonModel.Result>

}