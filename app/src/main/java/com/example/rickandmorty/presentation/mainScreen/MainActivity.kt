package com.example.rickandmorty.presentation.mainScreen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmorty.databinding.ActivityMainBinding
import com.example.rickandmorty.presentation.detailScreen.DetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

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
        observeData()
        initListener()
        initAdapter()

    }

    private fun initAdapter() {
        binding.rvCharacters.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = cartoonAdapter
        }
    }

    private fun requestData() {
        viewModel.getCharacters()
    }

    private fun observeData() {
        viewModel.characters.observe(this) { characters ->
            cartoonAdapter.setData(characters)
        }
    }

    private fun initListener() {
        binding.btnCharacterSearch.setOnClickListener {
            val characterId = binding.etCharacterId.text.toString()
            navigateToDetail(characterId)
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
        Toast.makeText(this, "Please enter character ID", Toast.LENGTH_SHORT)
            .show()
    }
}