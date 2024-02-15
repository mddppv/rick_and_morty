package com.example.rickandmorty.presentation.mainScreen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmorty.databinding.ActivityMainBinding
import com.example.rickandmorty.presentation.detailScreen.DetailActivity
import com.example.rickandmorty.utils.BaseActivity
import com.example.rickandmorty.utils.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModel()

    private val cartoonAdapter = CartoonAdapter(
        onClick = { cartoonId ->
            navigateToDetail(cartoonId.toString())
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestData()
        initAdapter()

    }

    private fun requestData() {
        viewModel.getCharacters().observe(this) { resource ->
            when (resource) {
                is Resource.Loading -> binding.progressIndicator.isVisible = true
                is Resource.Success -> {
                    binding.progressIndicator.isVisible = false
                    resource.data?.let { cartoonAdapter.setCartoonData(it) }
                }

                is Resource.Error -> {
                    binding.progressIndicator.isVisible = false
                    Toast.makeText(this, resource.message ?: "Unknown error", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun initAdapter() {
        binding.rvCharacters.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = cartoonAdapter
        }
    }

    companion object {
        const val CHARACTER_ID_NAVIGATE = "characterId"
    }
}

private fun Context.navigateToDetail(id: String) {
    if (id.isNotEmpty()) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(MainActivity.CHARACTER_ID_NAVIGATE, id)
        startActivity(intent)
    } else {
        Toast.makeText(this, "Please enter character ID", Toast.LENGTH_SHORT).show()
    }
}