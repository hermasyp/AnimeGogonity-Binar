package com.catnip.animecommunity.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.catnip.animecommunity.data.network.api.model.Anime
import com.catnip.animecommunity.databinding.ItemAnimePosterBinding
import com.catnip.animecommunity.databinding.ItemAnimePosterGridBinding

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class AnimeListAdapter(
    private val isGridLayout: Boolean = false,
    private val itemClick: (Anime) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: MutableList<Anime> = mutableListOf()

    fun setItems(items: List<Anime>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (!isGridLayout) {
            PosterViewHolderImpl(
                ItemAnimePosterBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                itemClick
            )
        } else {
            GridPosterViewHolderImpl(
                ItemAnimePosterGridBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                itemClick
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PosterViewHolder) {
            holder.bindView(items[position])
        }
    }

    override fun getItemCount(): Int = items.size


}