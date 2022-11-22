package com.catnip.animecommunity.utils

import com.catnip.animecommunity.data.firebase.model.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resumeWithException

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/

suspend fun <T> Task<T>.await(): T {
    return suspendCancellableCoroutine { cont ->
        addOnCompleteListener {
            if (it.exception != null) {
                cont.resumeWithException(it.exception!!)
            } else {
                cont.resume(it.result, onCancellation = null)
            }
        }

    }
}

fun FirebaseUser.toUserObject(): User {
    return User(
        displayName.orEmpty(),
        email.orEmpty(),
        photoUrl.toString()
    )
}