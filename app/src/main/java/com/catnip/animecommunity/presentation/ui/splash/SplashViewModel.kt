package com.catnip.animecommunity.presentation.ui.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.catnip.animecommunity.data.firebase.model.User
import com.catnip.animecommunity.data.repository.UserRepository

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class SplashViewModel(
    private val userRepository: UserRepository,
) : ViewModel() {

    val currentUserLiveData = MutableLiveData<User?>()

    fun getCurrentUser() {
        currentUserLiveData.postValue(userRepository.getCurrentUser())
    }
}