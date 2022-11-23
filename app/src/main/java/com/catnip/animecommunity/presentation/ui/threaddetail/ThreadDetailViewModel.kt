package com.catnip.animecommunity.presentation.ui.threaddetail

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catnip.animecommunity.base.wrapper.Resource
import com.catnip.animecommunity.data.firebase.model.SubThreadItem
import com.catnip.animecommunity.data.firebase.model.ThreadItem
import com.catnip.animecommunity.data.repository.ThreadRepository
import com.catnip.animecommunity.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class ThreadDetailViewModel(
    private val threadRepository: ThreadRepository,
    private val userRepository: UserRepository,
    private val intentData: Bundle
) : ViewModel() {

    val parentThread: ThreadItem? by lazy {
        intentData.getParcelable(ThreadDetailActivity.EXTRAS_PARENT_THREAD)
    }

    val replyThreadResult = MutableLiveData<Resource<Boolean>>()

    fun replyThread(content: String) {
        replyThreadResult.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            replyThreadResult.postValue(
                threadRepository.createSubThread(
                    parentThread?.id.orEmpty(),
                    generateSubThread(content)
                )
            )
        }
    }

    private fun generateSubThread(content: String): SubThreadItem {
        return SubThreadItem(content = content, creator = userRepository.getCurrentUser())
    }

    fun getSubThread() = threadRepository.getSubThread(parentThread?.id.orEmpty())
}