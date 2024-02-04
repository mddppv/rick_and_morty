package com.example.rickandmorty.data.model

import com.google.gson.annotations.SerializedName

data class CartoonEpisodeModel(
    val info: Info,
    val results: List<Result>
) {
    data class Info(
        val count: Int,
        val next: String,
        val pages: Int,
        val prev: Any
    )

    data class Result(
        val id: Int,
        val name: String,
        @SerializedName("air_date")
        val airDate: String,
        val episode: String,
        val characters: List<String>,
        val url: String,
        val created: String
    )
}