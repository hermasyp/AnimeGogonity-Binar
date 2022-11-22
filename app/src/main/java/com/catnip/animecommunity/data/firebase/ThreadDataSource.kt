package com.catnip.animecommunity.data.firebase

import com.catnip.animecommunity.data.firebase.model.ThreadItem
import com.catnip.animecommunity.utils.setValueAsync
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
interface ThreadDataSource {
    suspend fun createThread(threadItem: ThreadItem): Boolean
    fun getThread(): FirebaseRecyclerOptions<ThreadItem>
}

class FirebaseThreadDataSource(private val firebaseDatabase: FirebaseDatabase) : ThreadDataSource {

    override suspend fun createThread(threadItem: ThreadItem): Boolean {
        return getChild().child(threadItem.id).setValueAsync(threadItem)
    }

    override fun getThread(): FirebaseRecyclerOptions<ThreadItem> {
        return FirebaseRecyclerOptions.Builder<ThreadItem>()
            .setQuery(getChild(), ThreadItem::class.java)
            .build()
    }

    private fun getChild() = firebaseDatabase.reference.child(THREADS_CHILD)

    companion object {
        private const val THREADS_CHILD = "threads"
    }

}