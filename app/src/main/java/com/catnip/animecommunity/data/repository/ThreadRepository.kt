package com.catnip.animecommunity.data.repository

import com.catnip.animecommunity.base.BaseRepository
import com.catnip.animecommunity.base.wrapper.Resource
import com.catnip.animecommunity.data.firebase.ThreadDataSource
import com.catnip.animecommunity.data.firebase.model.SubThreadItem
import com.catnip.animecommunity.data.firebase.model.ThreadItem
import com.firebase.ui.database.FirebaseRecyclerOptions

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
interface ThreadRepository {
    suspend fun createThread(threadItem: ThreadItem): Resource<Boolean>
    suspend fun createSubThread(
        parentThreadId: String,
        subThreadItem: SubThreadItem
    ): Resource<Boolean>
    fun getThread(): FirebaseRecyclerOptions<ThreadItem>
    fun getSubThread(parentThreadId: String): FirebaseRecyclerOptions<SubThreadItem>
}

class ThreadRepositoryImpl(private val dataSource: ThreadDataSource) : BaseRepository(),
    ThreadRepository {
    override suspend fun createThread(threadItem: ThreadItem): Resource<Boolean> {
        return proceed { dataSource.createThread(threadItem) }
    }

    override suspend fun createSubThread(
        parentThreadId: String,
        subThreadItem: SubThreadItem
    ): Resource<Boolean> {
        return proceed { dataSource.createSubThread(parentThreadId, subThreadItem) }
    }

    override fun getThread(): FirebaseRecyclerOptions<ThreadItem> {
        return dataSource.getThread()
    }

    override fun getSubThread(parentThreadId: String): FirebaseRecyclerOptions<SubThreadItem> {
        return dataSource.getSubThread(parentThreadId)
    }
}