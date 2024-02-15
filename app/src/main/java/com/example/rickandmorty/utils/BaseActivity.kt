package com.example.rickandmorty.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData

abstract class BaseActivity : AppCompatActivity() {

    protected fun <T> LiveData<Resource<T>>.observeResource(
        loadingBlock: () -> Unit = {},
        successBlock: (T) -> Unit,
        errorBlock: (String) -> Unit
    ) {
        observe(this@BaseActivity) { resource ->
            when (resource) {
                is Resource.Loading -> loadingBlock()
                is Resource.Success -> successBlock(resource.data!!)
                is Resource.Error -> errorBlock(resource.message ?: "Unknown error")
            }
        }
    }
}