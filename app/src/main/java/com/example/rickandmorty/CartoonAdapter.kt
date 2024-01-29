package com.example.rickandmorty

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rickandmorty.data.model.CartoonModel
import com.example.rickandmorty.databinding.ItemCharacterBinding

class CartoonAdapter(private val cartoons: MutableList<CartoonModel> = mutableListOf()) :
    RecyclerView.Adapter<CartoonAdapter.ViewHolder>() {

    fun setData(newCartoons: List<CartoonModel>) {
        cartoons.clear()
        cartoons.addAll(newCartoons)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cartoon = cartoons[position]
        holder.bind(cartoon)
    }

    override fun getItemCount() = cartoons.size

    inner class ViewHolder(private val binding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cartoon: CartoonModel) {
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