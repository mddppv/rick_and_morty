package com.example.rickandmorty.presentation.mainScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.data.api.CartoonApiService
import com.example.rickandmorty.data.model.CartoonModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val cartoonApiService: CartoonApiService
) : ViewModel() {

    private val _characters = MutableLiveData<List<CartoonModel.Result>>()
    val characters: LiveData<List<CartoonModel.Result>> = _characters

    private val _error = MutableLiveData<String>()

    fun getCharacters() {
        viewModelScope.launch {
            try {
                val response = cartoonApiService.getCharacter()
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _characters.postValue(responseBody.results)
                    }
                } else {
                    throw HttpException(response)
                }
            } catch (e: Exception) {
                _error.value = "Error: ${e.message}"
            }
        }
    }
}