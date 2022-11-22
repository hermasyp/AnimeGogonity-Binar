package com.catnip.animecommunity.data.repository

import com.catnip.animecommunity.base.BaseRepository
import com.catnip.animecommunity.base.wrapper.Resource
import com.catnip.animecommunity.data.firebase.UserAuthDataSource
import com.catnip.animecommunity.data.firebase.model.User

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
interface UserRepository {
    suspend fun signInWithCredential(token: String): Resource<Pair<Boolean, User?>>
    fun getCurrentUser(): User?
    fun logoutUser()
}

class UserRepositoryImpl(
    private val userAuthDataSource: UserAuthDataSource,
) : UserRepository, BaseRepository() {

    override suspend fun signInWithCredential(token: String): Resource<Pair<Boolean, User?>> {
        return proceed { userAuthDataSource.signInWithCredential(token) }
    }

    override fun getCurrentUser(): User? {
        return userAuthDataSource.getCurrentUser()
    }

    override fun logoutUser() {
        return userAuthDataSource.logoutUser()
    }

}