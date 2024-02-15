package com.example.rickandmorty.utils

import com.example.rickandmorty.R

enum class CharacterStatus(val drawableResource: Int) {
    ALIVE(R.drawable.circle_green),
    DEAD(R.drawable.circle_red),
    UNKNOWN(R.drawable.circle_default)
}