package com.example.rickandmorty

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.data.CartoonApiService
import com.example.rickandmorty.data.model.CartoonModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val cartoonApiService: CartoonApiService) :
    ViewModel() {

    private val _characters = MutableLiveData<List<CartoonModel>>()
    val characters: LiveData<List<CartoonModel>> = _characters

    private val _error = MutableLiveData<String>()

    fun getCharacters() {
        viewModelScope.launch {
            try {
                val cartoons = mutableListOf<CartoonModel>()
                for (i in 1..10) {
                    val response = cartoonApiService.getCharacter(i)
                    if (response.isSuccessful) {
                        response.body()?.let { cartoons.add(it) }
                    } else {
                        throw HttpException(response)
                    }
                }
                _characters.value = cartoons
            } catch (e: Exception) {
                _error.value = "Error: ${e.message}"
            }
        }
    }
}