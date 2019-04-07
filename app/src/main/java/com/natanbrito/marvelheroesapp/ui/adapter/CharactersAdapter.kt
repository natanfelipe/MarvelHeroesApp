package com.natanbrito.marvelheroesapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.natanbrito.marvelheroesapp.R
import com.natanbrito.marvelheroesapp.model.Characters
import com.natanbrito.marvelheroesapp.utils.Utils
import kotlinx.android.synthetic.main.characters_item_layout.view.*
import org.jetbrains.annotations.NotNull

class CharactersAdapter : PagedListAdapter<Characters,CharactersAdapter.CharactersViewHolder>(characterDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.characters_item_layout, parent, false)
        return CharactersViewHolder(view)
    }


    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        val character = getItem(position)
        holder.bind(character)
    }


    class CharactersViewHolder(@NotNull itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(character: Characters?) {
            var characterThumbnail = character?.thumbnail?.path+"."+character?.thumbnail?.extension
            characterThumbnail = characterThumbnail.replace("http","https")
            Glide.with(itemView)
                .load(characterThumbnail)
                .apply(RequestOptions().placeholder(R.drawable.ic_launcher_background))
                .into(itemView.characterImage)

            itemView.characterName.text = character?.name
        }
    }

    companion object {
        val characterDiff = object: DiffUtil.ItemCallback<Characters>(){
            override fun areItemsTheSame(oldItem: Characters, newItem: Characters): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Characters, newItem: Characters): Boolean {
                return oldItem == newItem
            }

        }
    }
}

