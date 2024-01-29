package com.example.rickandmorty.data

import com.example.rickandmorty.data.model.CartoonModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CartoonApiService {

    @GET("character/{id}")
    suspend fun getCharacter(@Path("id") id: Int): Response<CartoonModel>

}