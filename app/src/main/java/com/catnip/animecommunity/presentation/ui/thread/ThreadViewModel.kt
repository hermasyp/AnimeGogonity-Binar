package com.catnip.animecommunity.presentation.ui.thread

import androidx.lifecycle.ViewModel
import com.catnip.animecommunity.data.repository.ThreadRepository

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class ThreadViewModel(private val repository: ThreadRepository) : ViewModel() {
    fun getThreadStreamData() = repository.getThread()
}