package com.catnip.animecommunity.presentation.ui.threadform

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catnip.animecommunity.base.wrapper.Resource
import com.catnip.animecommunity.data.firebase.model.ThreadItem
import com.catnip.animecommunity.data.repository.ThreadRepository
import com.catnip.animecommunity.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class ThreadFormViewModel(
    private val threadRepository: ThreadRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    val createThreadResult = MutableLiveData<Resource<Boolean>>()

    fun createThread(title: String, content: String) {
        createThreadResult.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            createThreadResult.postValue(threadRepository.createThread(generateThreadItem(title,content)))
        }
    }

    private fun generateThreadItem(title: String, content: String) : ThreadItem {
        return ThreadItem(
            creator = userRepository.getCurrentUser(),
            title = title,
            content = content
        )
    }

}