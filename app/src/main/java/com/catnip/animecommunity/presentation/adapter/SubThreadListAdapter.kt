package com.catnip.animecommunity.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.catnip.animecommunity.R
import com.catnip.animecommunity.data.firebase.model.SubThreadItem
import com.catnip.animecommunity.data.firebase.model.User
import com.catnip.animecommunity.databinding.ItemSubThreadBinding
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseError

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class SubThreadListAdapter(
    dataStream: FirebaseRecyclerOptions<SubThreadItem>,
    private val currentUser: User?,
    private val onDataExist: () -> Unit,
    private val onLoading: (isLoading: Boolean) -> Unit,
    private val onDataEmpty: () -> Unit,
    private val onDataError: (e: Exception) -> Unit
) : FirebaseRecyclerAdapter<SubThreadItem, SubThreadListAdapter.SubThreadItemViewHolder>(dataStream) {

    init {
        onLoading(true)
    }

    class SubThreadItemViewHolder(private val binding: ItemSubThreadBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SubThreadItem,currentUser: User?) {
            binding.ivProfilePict.load(item.creator?.photoProfileUrl){
                crossfade(true)
                placeholder(R.drawable.ic_person)
                error(R.drawable.ic_person)
                transformations(CircleCropTransformation())
            }
            binding.tvContentThread.text = item.content
            binding.tvNameThreadStarter.text = item.creator?.displayName
            binding.tvTextOwner.isVisible = currentUser?.email == item.creator?.email
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubThreadItemViewHolder {
        return SubThreadItemViewHolder(
            ItemSubThreadBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SubThreadItemViewHolder, position: Int, model: SubThreadItem) {
        holder.bind(model,currentUser)
    }

    override fun onDataChanged() {
        super.onDataChanged()
        onLoading(false)
        if (itemCount < 1) {
            onDataEmpty()
        } else {
            onDataExist()
        }
    }

    override fun onError(error: DatabaseError) {
        super.onError(error)
        onDataError(error.toException())
    }

}