package com.example.rickandmorty.data.api

import com.example.rickandmorty.data.model.CartoonModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CartoonApiService {

    @GET("character")
    fun getCharacter(): Call<CartoonModel>

    @GET("character/{id}")
    fun getCharacterById(@Path("id") id: String): Call<CartoonModel.Result>

}