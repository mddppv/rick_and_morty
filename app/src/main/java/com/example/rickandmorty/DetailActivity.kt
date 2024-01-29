package com.example.rickandmorty

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rickandmorty.data.model.CartoonModel
import com.example.rickandmorty.databinding.ActivityDetailBinding
import com.example.rickandmorty.databinding.ItemCharacterBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val characterId = intent.getStringExtra("characterId")
        setData(characterId)
    }

    private fun setData(characterId: String?) {
        characterId?.let {
            with(binding) {
                tvCharacterName.text = it
                tvCharacterStatus.text = it
                tvCharacterLocation.text = it
                tvCharacterFirstSeen.text = it
            }
        }
    }
}