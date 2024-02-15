package com.example.rickandmorty.presentation.mainScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.rickandmorty.data.Repository
import com.example.rickandmorty.data.model.CartoonModel
import com.example.rickandmorty.utils.Resource

class MainViewModel(
    private val repository: Repository
) : ViewModel() {

    fun getCharacters(): LiveData<Resource<List<CartoonModel.Result>>> {
        return repository.getCharacters()
    }
}