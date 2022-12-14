package com.catnip.animecommunity.data.firebase

import com.catnip.animecommunity.data.firebase.model.SubThreadItem
import com.catnip.animecommunity.data.firebase.model.ThreadItem
import com.catnip.animecommunity.utils.setValueAppendId
import com.catnip.animecommunity.utils.setValueAsync
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
interface ThreadDataSource {
    suspend fun createThread(threadItem: ThreadItem): Boolean
    suspend fun createSubThread(parentThreadId: String, subThreadItem: SubThreadItem): Boolean
    fun getThread(): FirebaseRecyclerOptions<ThreadItem>
    fun getSubThread(parentThreadId: String): FirebaseRecyclerOptions<SubThreadItem>
}
class FirebaseThreadDataSource(private val firebaseDatabase: FirebaseDatabase) : ThreadDataSource {

    override suspend fun createThread(threadItem: ThreadItem): Boolean {
        return getParentChild().push().setValueAppendId { id -> threadItem.apply { this.id = id } }
    }
    override suspend fun createSubThread(
        parentThreadId: String,
        subThreadItem: SubThreadItem
    ): Boolean {
        return getThreadReplyChild(parentThreadId)
            .push()
            .setValueAppendId { id ->
                subThreadItem.apply { this.id = id }
            }
    }

    override fun getThread(): FirebaseRecyclerOptions<ThreadItem> {
        return FirebaseRecyclerOptions.Builder<ThreadItem>()
            .setQuery(getParentChild(), ThreadItem::class.java)
            .build()
    }

    override fun getSubThread(parentThreadId: String): FirebaseRecyclerOptions<SubThreadItem> {
        return FirebaseRecyclerOptions.Builder<SubThreadItem>()
            .setQuery(getThreadReplyChild(parentThreadId), SubThreadItem::class.java)
            .build()
    }

    private fun getParentChild() = firebaseDatabase.reference.child(THREADS_CHILD)

    private fun getThreadReplyChild(parentThreadId: String) =
        getParentChild().child(parentThreadId).child(THREADS_REPLY_CHILD)

    companion object {
        private const val THREADS_CHILD = "threads"
        private const val THREADS_REPLY_CHILD = "threads_reply"
    }
}