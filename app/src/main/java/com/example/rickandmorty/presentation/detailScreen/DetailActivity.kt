package com.example.rickandmorty.presentation.detailScreen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.rickandmorty.databinding.ActivityDetailBinding
import com.example.rickandmorty.presentation.mainScreen.MainActivity
import com.example.rickandmorty.presentation.mainScreen.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailsViewModel by lazy {
        ViewModelProvider(this)[DetailsViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val characterId = getExtraData()
        requestData(characterId)
        observeData()
    }

    private fun getExtraData() = intent.getStringExtra(MainActivity.CHARACTER_ID_NAVIGATE)

    private fun requestData(characterId: String?) {
        if (characterId != null) {
            viewModel.getCharacterById(characterId.toIntOrNull() ?: 1)
        }
    }

    private fun observeData() {
        viewModel.character.observe(this) { cartoon ->
            if (cartoon != null) {
                with(binding) {
                    tvCharacterName.text = cartoon.name
                    tvCharacterStatus.text = cartoon.status
                    tvCharacterLocation.text = cartoon.location.name
                    tvCharacterFirstSeen.text = cartoon.origin.name
                    Glide.with(root)
                        .load(cartoon.image)
                        .into(ivCharacterImage)
                }
            }
        }
    }
}