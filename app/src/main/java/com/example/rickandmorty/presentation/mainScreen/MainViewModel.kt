package com.example.rickandmorty.presentation.mainScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.data.api.CartoonApiService
import com.example.rickandmorty.data.model.CartoonEpisodeModel
import com.example.rickandmorty.data.model.CartoonModel
import com.example.rickandmorty.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

class MainViewModel (
    private val cartoonApiService: CartoonApiService
) : ViewModel() {

    private val _characters = MutableLiveData<Resource<List<CartoonModel.Result>>>()
    val characters: LiveData<Resource<List<CartoonModel.Result>>> = _characters
    private val _episodes = MutableLiveData<Resource<List<CartoonEpisodeModel.Result>>>()
    val episodes: LiveData<Resource<List<CartoonEpisodeModel.Result>>> = _episodes

    fun getCharacters() {
        viewModelScope.launch {
            _characters.value = Resource.Loading()
            try {
                val response = cartoonApiService.getCharacter()
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _characters.value = Resource.Success(responseBody.results)
                    }
                } else {
                    throw HttpException(response)
                }
            } catch (e: Exception) {
                _characters.value = Resource.Error("Error: ${e.message}")
            }
        }
    }

    fun getEpisodes() {
        viewModelScope.launch {
            _episodes.value = Resource.Loading()
            try {
                val response = cartoonApiService.getEpisode()
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _episodes.value = Resource.Success(responseBody.results)
                    }
                } else {
                    throw HttpException(response)
                }
            } catch (e: Exception) {
                _episodes.value = Resource.Error("Error: ${e.message}")
            }
        }
    }
}