package com.example.rickandmorty.presentation.detailScreen

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
class DetailsViewModel @Inject constructor(
    private val cartoonApiService: CartoonApiService
) : ViewModel() {

    private val _character = MutableLiveData<CartoonModel.Result?>()
    val character: LiveData<CartoonModel.Result?> = _character

    private val _error = MutableLiveData<String>()

    fun getCharacterById(id: Int) {
        viewModelScope.launch {
            try {
                val response = cartoonApiService.getCharacterById(id)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _character.postValue(responseBody)
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