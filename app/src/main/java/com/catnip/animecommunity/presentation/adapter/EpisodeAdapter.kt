package com.catnip.animecommunity.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.catnip.animecommunity.data.network.api.model.Episodes
import com.catnip.animecommunity.databinding.ItemEpisodeAnimeBinding

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class EpisodesAdapter(var itemClick:( (Episodes) -> Unit)? = null) :
    RecyclerView.Adapter<EpisodesAdapter.EpisodesItemViewHolder>() {


    private var items: MutableList<Episodes> = mutableListOf()

    fun setItems(items: List<Episodes>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun addItems(items: List<Episodes>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun clearItems() {
        this.items.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodesItemViewHolder {
        val binding = ItemEpisodeAnimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EpisodesItemViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: EpisodesItemViewHolder, position: Int) {
        holder.bindView(items[position])
    }

    override fun getItemCount(): Int = items.size


    class EpisodesItemViewHolder(
        private val binding: ItemEpisodeAnimeBinding,
        val itemClick: ((Episodes) -> Unit)?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(item: Episodes) {
            with(item) {
                itemClick?.let {
                    itemView.setOnClickListener {
                        it(this)
                    }
                }
            }
            binding.tvEpisode.text = "Episode ${item.episodeNum}"
        }
    }

}