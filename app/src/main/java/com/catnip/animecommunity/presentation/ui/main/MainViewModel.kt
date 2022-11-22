package com.catnip.animecommunity.presentation.ui.main

import androidx.lifecycle.ViewModel
import com.catnip.animecommunity.data.repository.UserRepository

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class MainViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    fun doLogout() {
        userRepository.logoutUser()
    }
}