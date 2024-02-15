package com.example.rickandmorty.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rickandmorty.data.api.CartoonApiService
import com.example.rickandmorty.data.model.CartoonModel
import com.example.rickandmorty.utils.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository(private val api: CartoonApiService) : ViewModel() {

    fun getCharacters(): LiveData<Resource<List<CartoonModel.Result>>> {
        val characters = MutableLiveData<Resource<List<CartoonModel.Result>>>()
        characters.postValue((Resource.Loading()))

        api.getCharacter().enqueue(object : Callback<CartoonModel> {
            override fun onResponse(call: Call<CartoonModel>, response: Response<CartoonModel>) {
                if (response.isSuccessful && response.body() != null && response.code() in 200..300) {
                    response.body()?.let {
                        characters.postValue(
                            Resource.Success(
                                it.results
                            )
                        )
                    }
                }
            }

            override fun onFailure(call: Call<CartoonModel>, t: Throwable) {
                characters.postValue(Resource.Error(t.message ?: "Unknown error!"))
            }
        })

        return characters
    }

    fun getCharacterById(id: String): LiveData<CartoonModel.Result> {
        val characterLv = MutableLiveData<CartoonModel.Result>()

        api.getCharacterById(id).enqueue(object : Callback<CartoonModel.Result> {
            override fun onResponse(
                call: Call<CartoonModel.Result>, response: Response<CartoonModel.Result>
            ) {
                response.body()?.let {
                    characterLv.postValue(it)
                }
            }

            override fun onFailure(call: Call<CartoonModel.Result>, t: Throwable) {
            }
        })

        return characterLv
    }
}