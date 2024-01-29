package com.example.rickandmorty.presentation.mainScreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rickandmorty.data.model.CartoonModel
import com.example.rickandmorty.databinding.ItemCharacterBinding

class CartoonAdapter(onClick: (Int) -> Unit) : RecyclerView.Adapter<CartoonAdapter.ViewHolder>() {

    private val cartoonList: MutableList<CartoonModel.Result> = mutableListOf()
    private val onItemClick: (Int) -> Unit = onClick

    fun setData(newCartoons: List<CartoonModel.Result>) {
        cartoonList.clear()
        cartoonList.addAll(newCartoons)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cartoon = cartoonList[position]
        holder.bind(cartoon)
    }

    override fun getItemCount() = cartoonList.size

    inner class ViewHolder(private val binding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cartoon: CartoonModel.Result) {
            with(binding) {
                tvCharacterName.text = cartoon.name
                tvCharacterStatus.text = cartoon.status
                tvCharacterLocation.text = cartoon.location.name
                tvCharacterFirstSeen.text = cartoon.origin.name
                Glide.with(root)
                    .load(cartoon.image)
                    .into(ivCharacterImage)

                itemCharacterContent.setOnClickListener {
                    onItemClick(cartoon.id)
                }
            }
        }
    }
}