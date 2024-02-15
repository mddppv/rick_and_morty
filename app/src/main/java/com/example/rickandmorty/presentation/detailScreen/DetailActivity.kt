package com.example.rickandmorty.presentation.detailScreen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.example.rickandmorty.R
import com.example.rickandmorty.data.model.CartoonModel
import com.example.rickandmorty.databinding.ActivityDetailBinding
import com.example.rickandmorty.presentation.mainScreen.MainActivity
import com.example.rickandmorty.utils.CharacterStatus
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Locale

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val characterId = getExtraData()
        requestData(characterId)
        dropdownClicker()

    }

    private fun getExtraData() = intent.getStringExtra(MainActivity.CHARACTER_ID_NAVIGATE)

    private fun requestData(characterId: String?) {
        viewModel.getCharacterById(characterId.orEmpty()).observe(this) {
            it?.let {
                observeData(it)
            }
        }
    }

    private fun observeData(it: CartoonModel.Result) {
        with(binding) {
            tvCharacterName.text = it.name
            tvCharacterStatus.text = it.status
            tvCharacterSpecies.text = it.species
            tvCharacterGender.text = it.gender
            tvCharacterLocation.text = it.location.name
            tvCharacterFirstSeen.text = it.origin.name

            Glide.with(ivCharacterImage).load(it.image).into(ivCharacterImage)

            when (CharacterStatus.valueOf(it.status.uppercase(Locale.getDefault()))) {
                CharacterStatus.ALIVE -> binding.ivCharacterStatus.setBackgroundResource(
                    CharacterStatus.ALIVE.drawableResource
                )

                CharacterStatus.DEAD -> binding.ivCharacterStatus.setBackgroundResource(
                    CharacterStatus.DEAD.drawableResource
                )

                CharacterStatus.UNKNOWN -> binding.ivCharacterStatus.setBackgroundResource(
                    CharacterStatus.UNKNOWN.drawableResource
                )
            }
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
}