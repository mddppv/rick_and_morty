package com.example.rickandmorty.presentation.detailScreen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.ActivityDetailBinding
import com.example.rickandmorty.presentation.mainScreen.MainActivity
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
        dropdownClicker()
    }

    private fun getExtraData() = intent.getStringExtra(MainActivity.CHARACTER_ID_NAVIGATE)

    private fun requestData(characterId: String?) {
        if (characterId != null) {
            viewModel.getCharacterById(characterId.toIntOrNull() ?: 1)
        }
    }

    private fun dropdownClicker() {
        with(binding) {
            dropdownFirst.setOnClickListener {
                if (tvName.isVisible) {
                    ivPointer.setBackgroundResource(R.drawable.arrow_dropdown)
                    tvNameTitle.isGone = true
                    tvName.isGone = true
                    tvAirDateTitle.isGone = true
                    tvAirDate.isGone = true
                } else {
                    ivPointer.setBackgroundResource(R.drawable.arrow_dropup)
                    tvNameTitle.isVisible = true
                    tvName.isVisible = true
                    tvAirDateTitle.isVisible = true
                    tvAirDate.isVisible = true
                }
            }
        }
    }

    private fun observeData() {
        viewModel.character.observe(this) { cartoon ->
            if (cartoon != null) {
                with(binding) {
                    tvCharacterName.text = cartoon.name
                    tvCharacterStatus.text = cartoon.status
                    tvCharacterLocation.text = cartoon.location.name
                    tvCharacterSpecies.text = cartoon.species

                    Glide.with(ivCharacterImage).load(cartoon.image)
                        .into(ivCharacterImage)

                    when (cartoon.status) {
                        "unknown" -> ivCharacterStatus.setBackgroundResource(R.drawable.circle_default)
                        "Alive" -> ivCharacterStatus.setBackgroundResource(R.drawable.circle_green)
                        "Dead" -> ivCharacterStatus.setBackgroundResource(R.drawable.circle_red)
                    }
                }
            }
        }

        viewModel.episode.observe(this) { episode ->
            if (episode != null) {
                with(binding) {
                    tvCharacterFirstSeen.text = episode.name
                    tvSeason.text = episode.episode
                    tvEpisode.text = episode.episode
                    tvName.text = episode.name
                    tvAirDate.text = episode.airDate
                }
            }
        }
    }
}