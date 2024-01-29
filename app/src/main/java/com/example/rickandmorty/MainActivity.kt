package com.example.rickandmorty

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmorty.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }
    private val cartoonAdapter = CartoonAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvCharacters.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = cartoonAdapter
        }

        viewModel.characters.observe(this) { characters ->
            cartoonAdapter.setData(characters)
        }

        viewModel.getCharacters()

        binding.btnCharacterSearch.setOnClickListener {
            val characterId = binding.etCharacterId.text.toString()
            if (characterId.isNotEmpty()) {
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra("characterId", characterId)
                startActivity(intent)
            } else {
                Toast.makeText(this@MainActivity, "Please enter character ID", Toast.LENGTH_SHORT).show()
            }
        }
    }
}