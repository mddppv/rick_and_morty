package com.example.rickandmorty.presentation.detailScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.rickandmorty.data.Repository
import com.example.rickandmorty.data.model.CartoonModel

class DetailsViewModel(
    private val repository: Repository
) : ViewModel() {

    fun getCharacterById(id: String): LiveData<CartoonModel.Result> {
        return repository.getCharacterById(id)
    }
}